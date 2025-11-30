package com.example.levelUp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.levelUp.dto.request.AutenticacaoDTO;
import com.example.levelUp.dto.request.UsuarioDTO;
import com.example.levelUp.dto.response.DadosTokenJWT;
import com.example.levelUp.dto.response.UsuarioResponse;
import com.example.levelUp.model.Usuario;
import com.example.levelUp.repository.UsuarioRepository;

@Service
@Transactional
public class AuthService {

  private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
  
  private final AuthenticationManager authenticationManager;
  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  public AuthService(AuthenticationManager authenticationManager,
      UsuarioRepository usuarioRepository,
      PasswordEncoder passwordEncoder,
      TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.usuarioRepository = usuarioRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenService = tokenService;
  }

  public DadosTokenJWT login(AutenticacaoDTO loginDTO) {
    logger.info("=== INICIANDO LOGIN ===");
    logger.info("Email recebido: {}", loginDTO.email());
    
    try {
      logger.info("1. Criando authentication token...");
      var authenticationToken = new UsernamePasswordAuthenticationToken(
          loginDTO.email(), loginDTO.senha());

      logger.info("2. Chamando authenticationManager.authenticate...");
      Authentication authentication = authenticationManager.authenticate(authenticationToken);
      logger.info("3. Autenticação bem-sucedida!");

      logger.info("4. Obtendo UserDetails do principal...");
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      logger.info("5. UserDetails obtido - username: {}", userDetails.getUsername());

      logger.info("6. Buscando usuário no banco pelo email: {}", userDetails.getUsername());
      Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
          .orElseThrow(() -> {
            logger.error("❌ USUÁRIO NÃO ENCONTRADO NO BANCO para email: {}", userDetails.getUsername());
            return new RuntimeException("Usuário não encontrado após autenticação");
          });

      logger.info("7. Usuário encontrado - ID: {}, Email: {}", usuario.getId(), usuario.getEmail());

      logger.info("8. Gerando token JWT...");
      String token = tokenService.generateToken(usuario);
      logger.info("9. ✅ LOGIN BEM-SUCEDIDO - Token gerado para: {}", usuario.getEmail());

      return new DadosTokenJWT(token);
        
    } catch (BadCredentialsException e) {
      logger.error("❌ CREDENCIAIS INVÁLIDAS para email: {}", loginDTO.email());
      throw new RuntimeException("Email ou senha incorretos");
    } catch (Exception e) {
      logger.error("❌ ERRO INESPERADO durante login para {}: {}", loginDTO.email(), e.getMessage(), e);
      throw new RuntimeException("Erro interno durante o login: " + e.getMessage());
    }
  }

  public UsuarioResponse cadastrar(UsuarioDTO usuarioDTO) {
    logger.info("=== INICIANDO CADASTRO ===");
    logger.info("Email: {}, Nome: {}", usuarioDTO.email(), usuarioDTO.nome());
    
    try {
      if (usuarioRepository.existsByEmail(usuarioDTO.email())) {
        logger.warn("Email já cadastrado: {}", usuarioDTO.email());
        throw new IllegalArgumentException("Email já cadastrado");
      }

      logger.info("Criptografando senha...");
      String senhaCriptografada = passwordEncoder.encode(usuarioDTO.senha());

      Usuario novoUsuario = new Usuario(
          usuarioDTO.email(),
          usuarioDTO.nome(),
          senhaCriptografada,
          usuarioDTO.dataNascimento(),
          usuarioDTO.telefone(),
          null, // metas
          null, // habitos
          null // tarefas
      );

      logger.info("Salvando usuário no banco...");
      Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
      logger.info("✅ USUÁRIO CADASTRADO COM SUCESSO - ID: {}", usuarioSalvo.getId());

      return new UsuarioResponse(usuarioSalvo);
    } catch (Exception e) {
      logger.error("❌ ERRO NO CADASTRO: {}", e.getMessage(), e);
      throw e;
    }
  }

  public boolean verificarEmailExistente(String email) {
    return usuarioRepository.existsByEmail(email);
  }
}

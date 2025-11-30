package com.example.levelUp.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.email(), loginDTO.senha());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenService.generateToken(usuario);
        return new DadosTokenJWT(token);
    }

    public UsuarioResponse cadastrar(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByEmail(usuarioDTO.email())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

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

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        return new UsuarioResponse(usuarioSalvo);
    }

    public boolean verificarEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}

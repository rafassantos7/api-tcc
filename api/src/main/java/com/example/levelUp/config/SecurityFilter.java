package com.example.levelUp.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.levelUp.model.Usuario;
import com.example.levelUp.repository.UsuarioRepository;
import com.example.levelUp.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final UsuarioRepository usuarioRepository;

  public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
    this.tokenService = tokenService;
    this.usuarioRepository = usuarioRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      var token = recuperarToken(request);

      if (token != null) {
        var login = tokenService.getSubject(token);

        if (login != null && !login.isEmpty()) {
          // CORREÇÃO: Usar Optional corretamente
          Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(login);
          
          if (usuarioOpt.isPresent()) {
            UserDetails usuario = usuarioOpt.get();
            
            var authentication = new UsernamePasswordAuthenticationToken(
                usuario,
                null,
                usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            logger.debug("Usuário autenticado: " + login);
          }
        }
      }
    } catch (Exception e) {
      logger.error("Erro no processamento do token JWT", e);
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);
  }

  private String recuperarToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");

    if (authHeader == null) {
      return null;
    }

    if (authHeader.startsWith("Bearer ")) {
      return authHeader.replace("Bearer ", "").trim();
    }

    return null;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    return path.startsWith("/auth/login") ||
        path.startsWith("/auth/cadastro");
  }
}

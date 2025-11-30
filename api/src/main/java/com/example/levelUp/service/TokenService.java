package com.example.levelUp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.levelUp.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

  private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

  @Value("${api.security.token.secret}")
  private String secret;

  public String generateToken(Usuario usuario) {
    try {
      logger.info("🔐 Gerando token para usuário: {}", usuario.getEmail());
      
      // Verificar se o secret está configurado
      if (secret == null || secret.trim().isEmpty()) {
        logger.error("❌ JWT SECRET NÃO CONFIGURADO!");
        throw new RuntimeException("JWT Secret não configurado");
      }
      
      logger.debug("JWT Secret configurado: {}", secret.substring(0, Math.min(10, secret.length())) + "...");
      
      Algorithm algorithm = Algorithm.HMAC256(secret);
      String token = JWT.create()
          .withIssuer("levelup-api")
          .withSubject(usuario.getEmail())
          .withClaim("id", usuario.getId())
          .withClaim("nome", usuario.getNome())
          .withExpiresAt(genExpirationDate())
          .sign(algorithm);
          
      logger.info("✅ Token JWT gerado com sucesso para: {}", usuario.getEmail());
      logger.debug("Token (primeiros 20 chars): {}", token.substring(0, Math.min(20, token.length())) + "...");
      
      return token;
    } catch (Exception exception) {
      logger.error("❌ ERRO AO GERAR TOKEN JWT: {}", exception.getMessage(), exception);
      throw new RuntimeException("Erro ao gerar token JWT: " + exception.getMessage());
    }
  }

  public String getSubject(String token) {
    try {
      logger.debug("🔍 Verificando token JWT...");
      
      if (token == null || token.trim().isEmpty()) {
        logger.warn("Token vazio ou nulo");
        return null;
      }
      
      if (secret == null || secret.trim().isEmpty()) {
        logger.error("❌ JWT SECRET NÃO CONFIGURADO!");
        return null;
      }
      
      Algorithm algorithm = Algorithm.HMAC256(secret);
      DecodedJWT decodedJWT = JWT.require(algorithm)
          .withIssuer("levelup-api")
          .build()
          .verify(token);

      String subject = decodedJWT.getSubject();
      logger.debug("✅ Token válido - Subject: {}", subject);
      return subject;

    } catch (JWTVerificationException exception) {
      logger.warn("⚠️ Token JWT inválido: {}", exception.getMessage());
      return null;
    } catch (Exception exception) {
      logger.error("❌ Erro ao verificar token: {}", exception.getMessage(), exception);
      return null;
    }
  }

  private Instant genExpirationDate() {
    Instant expiration = LocalDateTime.now().plusDays(30).toInstant(ZoneOffset.of("-03:00"));
    logger.debug("Token expira em: {}", expiration);
    return expiration;
  }
}

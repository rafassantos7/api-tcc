package com.example.levelUp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.levelUp.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  public String generateToken(Usuario usuario) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
          .withIssuer("levelup-api")
          .withSubject(usuario.getEmail())
          .withClaim("id", usuario.getId())
          .withClaim("nome", usuario.getNome())
          .withExpiresAt(genExpirationDate())
          .sign(algorithm);
    } catch (Exception exception) {
      throw new RuntimeException("Erro ao gerar token JWT", exception);
    }
  }

  public String getSubject(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      DecodedJWT decodedJWT = JWT.require(algorithm)
          .withIssuer("levelup-api")
          .build()
          .verify(token);

      return decodedJWT.getSubject();

    } catch (JWTVerificationException exception) {
      return null;
    } catch (Exception exception) {
      return null;
    }
  }

  private Instant genExpirationDate() {
    return LocalDateTime.now().plusDays(30).toInstant(ZoneOffset.of("-03:00"));
  }
}
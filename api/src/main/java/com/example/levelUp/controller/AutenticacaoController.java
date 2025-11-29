package com.example.levelUp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.levelUp.dto.DadosAutenticacao;
import com.example.levelUp.dto.DadosTokenJWT;
import com.example.levelUp.model.Usuario;
import com.example.levelUp.service.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

  private final AuthenticationManager manager;
  private final TokenService tokenService;

  // Injeção via construtor (Melhor prática)
  public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
    this.manager = manager;
    this.tokenService = tokenService;
  }

  @PostMapping
  public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
    // 1. Cria o token de autenticação do Spring
    var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

    // 2. O Spring vai no banco, checa o hash da senha e valida
    var authentication = manager.authenticate(authenticationToken);

    // 3. Se passou, gera o JWT
    var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

    return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
  }
}
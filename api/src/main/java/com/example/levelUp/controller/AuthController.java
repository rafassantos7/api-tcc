package com.example.levelUp.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.levelUp.dto.request.AutenticacaoDTO;
import com.example.levelUp.dto.request.UsuarioDTO;
import com.example.levelUp.dto.response.DadosTokenJWT;
import com.example.levelUp.dto.response.UsuarioResponse;
import com.example.levelUp.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<DadosTokenJWT> login(@Valid @RequestBody AutenticacaoDTO loginDTO) {
    DadosTokenJWT tokenResponse = authService.login(loginDTO);
    return ResponseEntity.ok(tokenResponse);
  }

  @PostMapping("/cadastro")
  public ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
    UsuarioResponse usuarioResponse = authService.cadastrar(usuarioDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
  }
}
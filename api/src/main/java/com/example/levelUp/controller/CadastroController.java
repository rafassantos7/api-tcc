package com.example.levelUp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.levelUp.dto.UsuarioDTO;
import com.example.levelUp.dto.UsuarioResponse;
import com.example.levelUp.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {

  private final UsuarioService usuarioService;

  public CadastroController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @PostMapping
  public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody @Valid UsuarioDTO dto) {
    UsuarioResponse usuarioSalvo = usuarioService.salvar(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
  }
}
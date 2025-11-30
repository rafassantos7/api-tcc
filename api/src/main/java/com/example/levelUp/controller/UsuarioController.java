package com.example.levelUp.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.levelUp.dto.request.UsuarioDTO;
import com.example.levelUp.dto.response.UsuarioResponse;
import com.example.levelUp.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> buscarMeuPerfil() {
        UsuarioResponse response = usuarioService.buscarMeuPerfil();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/me")
    public ResponseEntity<UsuarioResponse> atualizarMeuPerfil(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioResponse response = usuarioService.atualizarMeuPerfil(usuarioDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> excluirMeuPerfil() {
        usuarioService.excluirMeuPerfil();
        return ResponseEntity.noContent().build();
    }
}
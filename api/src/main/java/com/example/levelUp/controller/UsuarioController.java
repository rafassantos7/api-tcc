package com.example.levelUp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.levelUp.dto.UsuarioDTO;
import com.example.levelUp.dto.UsuarioResponse;
import com.example.levelUp.model.Meta;
import com.example.levelUp.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // CADASTRO (Aberto a todos)
    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody @Valid UsuarioDTO dto) {
        UsuarioResponse usuarioSalvo = usuarioService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    // ATUALIZAR PERFIL (Exige Token)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioDTO dto) {
        UsuarioResponse usuarioAtualizado = usuarioService.atualizar(id, dto);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // DELETAR CONTA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // VER METAS DO USUÁRIO (Específico)
    @GetMapping("/{id}/metas")
    public ResponseEntity<List<Meta>> listarMetas(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.listarMetasDoUsuario(id));
    }
}
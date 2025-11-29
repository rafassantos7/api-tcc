package com.example.levelUp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.levelUp.model.Meta;
import com.example.levelUp.service.MetaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/metas")
public class MetaController {

    private final MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    @GetMapping
    public ResponseEntity<List<Meta>> listarMinhasMetas() {
        // Traz apenas as metas do usuário logado
        return ResponseEntity.ok(metaService.listarTodos());
    }

    @GetMapping("/concluidas")
    public ResponseEntity<List<Meta>> listarConcluidas() {
        return ResponseEntity.ok(metaService.listarConcluidas());
    }

    @PostMapping
    public ResponseEntity<Meta> criar(@RequestBody @Valid Meta meta) {
        // O Service vincula ao usuário automaticamente
        return ResponseEntity.status(HttpStatus.CREATED).body(metaService.salvar(meta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meta> atualizar(@PathVariable Long id, @RequestBody @Valid Meta meta) {
        meta.setId(id); // Garante ID da URL
        return ResponseEntity.ok(metaService.atualizar(meta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        metaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
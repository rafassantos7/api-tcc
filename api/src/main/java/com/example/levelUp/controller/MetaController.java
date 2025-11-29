package com.example.levelUp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.levelUp.dto.MetaDTO;
import com.example.levelUp.dto.MetaResponse;
import com.example.levelUp.service.MetaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/metas")
public class MetaController {

    private final MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    // 1. LISTAR (GET) - Retorna DTOs
    @GetMapping
    public ResponseEntity<List<MetaResponse>> listar() {
        return ResponseEntity.ok(metaService.listarTodos());
    }

    // 2. LISTAR CONCLUÍDAS (GET)
    @GetMapping("/concluidas")
    public ResponseEntity<List<MetaResponse>> listarConcluidas() {
        return ResponseEntity.ok(metaService.listarConcluidas());
    }

    // 3. CADASTRAR (POST) - Recebe DTO, Devolve Response
    @PostMapping
    public ResponseEntity<MetaResponse> criar(@RequestBody @Valid MetaDTO dto) {
        MetaResponse metaSalva = metaService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(metaSalva);
    }

    // 4. ATUALIZAR (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<MetaResponse> atualizar(@PathVariable Long id, @RequestBody @Valid MetaDTO dto) {
        MetaResponse metaAtualizada = metaService.atualizar(id, dto);
        return ResponseEntity.ok(metaAtualizada);
    }

    // 5. EXCLUIR (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        metaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
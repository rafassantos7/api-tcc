package com.example.levelUp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.levelUp.dto.TarefaDTO;
import com.example.levelUp.dto.TarefaResponse;
import com.example.levelUp.service.TarefaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponse>> listar() {
        return ResponseEntity.ok(tarefaService.listarTarefas());
    }

    @GetMapping("/concluidas")
    public ResponseEntity<List<TarefaResponse>> listarConcluidas() {
        return ResponseEntity.ok(tarefaService.listarConcluidas());
    }

    @PostMapping
    public ResponseEntity<TarefaResponse> criar(@RequestBody @Valid TarefaDTO dto) {
        TarefaResponse novaTarefa = tarefaService.salvarTarefa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTarefa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponse> atualizar(@PathVariable Long id, @RequestBody @Valid TarefaDTO dto) {
        return ResponseEntity.ok(tarefaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        tarefaService.excluirTarefa(id);
        return ResponseEntity.noContent().build();
    }
}
package com.example.levelUp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.levelUp.dto.HabitoDTO;
import com.example.levelUp.dto.HabitoResponse;
import com.example.levelUp.service.HabitoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/habitos")
public class HabitoController {

    private final HabitoService habitoService;

    public HabitoController(HabitoService habitoService) {
        this.habitoService = habitoService;
    }

    @GetMapping
    public ResponseEntity<List<HabitoResponse>> listar() {
        return ResponseEntity.ok(habitoService.listarHabitos());
    }

    @PostMapping
    public ResponseEntity<HabitoResponse> criar(@RequestBody @Valid HabitoDTO dto) {
        HabitoResponse novoHabito = habitoService.salvarHabito(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoHabito);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid HabitoDTO dto) {
        return ResponseEntity.ok(habitoService.atualizarHabito(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        habitoService.deletarHabito(id);
        return ResponseEntity.noContent().build();
    }
}
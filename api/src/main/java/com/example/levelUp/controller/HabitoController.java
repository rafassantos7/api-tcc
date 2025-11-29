package com.example.levelUp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.levelUp.model.Habito;
import com.example.levelUp.service.HabitoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/habitos")
public class HabitoController {

    private final HabitoService habitoService;

    public HabitoController(HabitoService habitoService) {
        this.habitoService = habitoService;
    }

    @PostMapping
    public ResponseEntity<Habito> criar(@RequestBody @Valid Habito habito) {
        Habito novoHabito = habitoService.salvarHabito(habito);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoHabito);
    }

    @GetMapping
    public ResponseEntity<List<Habito>> listar() {
        return ResponseEntity.ok(habitoService.listarHabitos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habito> atualizar(@PathVariable Long id, @RequestBody @Valid Habito habito) {
        habito.setId(id); // Garante que o ID da URL seja usado
        return ResponseEntity.ok(habitoService.atualizarHabito(habito));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        habitoService.deletarHabito(id);
        return ResponseEntity.noContent().build();
    }
}
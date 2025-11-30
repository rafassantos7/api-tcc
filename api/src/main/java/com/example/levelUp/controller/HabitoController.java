package com.example.levelUp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.levelUp.dto.request.HabitoDTO;
import com.example.levelUp.dto.response.HabitoResponse;
import com.example.levelUp.model.enums.FrequenciaHabito;
import com.example.levelUp.model.enums.Status;
import com.example.levelUp.service.HabitoService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user/habitos")
public class HabitoController {

  private final HabitoService habitoService;

  public HabitoController(HabitoService habitoService) {
    this.habitoService = habitoService;
  }

  @PostMapping
  public ResponseEntity<HabitoResponse> salvar(@Valid @RequestBody HabitoDTO dto) {
    HabitoResponse response = habitoService.salvar(dto);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<List<HabitoResponse>> listarDoUsuario() {
    List<HabitoResponse> response = habitoService.listarDoUsuario();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<HabitoResponse> buscarPorId(@PathVariable Long id) {
    HabitoResponse response = habitoService.buscarPorId(id);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<HabitoResponse> atualizar(
      @PathVariable Long id,
      @Valid @RequestBody HabitoDTO dto) {
    HabitoResponse response = habitoService.atualizar(id, dto);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluir(@PathVariable Long id) {
    habitoService.excluir(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/concluir")
  public ResponseEntity<HabitoResponse> concluir(@PathVariable Long id) {
    HabitoResponse response = habitoService.concluir(id);
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/{id}/reiniciar")
  public ResponseEntity<HabitoResponse> reiniciar(@PathVariable Long id) {
    HabitoResponse response = habitoService.reiniciar(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/filtros")
  public ResponseEntity<List<HabitoResponse>> buscarComFiltros(
      @RequestParam(required = false) String titulo,
      @RequestParam(required = false) Status status,
      @RequestParam(required = false) FrequenciaHabito frequencia,
      @RequestParam(defaultValue = "DESC") String ordem) {

    List<HabitoResponse> response = habitoService.buscarComFiltros(titulo, status, frequencia, ordem);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/concluidos")
  public ResponseEntity<List<HabitoResponse>> buscarConcluidos() {
    List<HabitoResponse> response = habitoService.buscarConcluidos();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/pendentes")
  public ResponseEntity<List<HabitoResponse>> buscarPendentes() {
    List<HabitoResponse> response = habitoService.buscarPendentes();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/frequencia/{frequencia}")
  public ResponseEntity<List<HabitoResponse>> buscarPorFrequencia(
      @PathVariable FrequenciaHabito frequencia) {
    List<HabitoResponse> response = habitoService.buscarPorFrequencia(frequencia);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/estatisticas/total")
  public ResponseEntity<Long> contarHabitosUsuario() {
    Long total = habitoService.contarHabitosUsuario();
    return ResponseEntity.ok(total);
  }

  @GetMapping("/estatisticas/concluidos")
  public ResponseEntity<Long> contarHabitosConcluidos() {
    Long total = habitoService.contarHabitosConcluidos();
    return ResponseEntity.ok(total);
  }
}
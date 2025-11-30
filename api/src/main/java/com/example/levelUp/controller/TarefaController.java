package com.example.levelUp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.levelUp.dto.request.TarefaDTO;
import com.example.levelUp.dto.response.TarefaResponse;
import com.example.levelUp.model.enums.Prioridade;
import com.example.levelUp.model.enums.Status;
import com.example.levelUp.service.TarefaService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user/tarefas")
public class TarefaController {

  private final TarefaService tarefaService;

  public TarefaController(TarefaService tarefaService) {
    this.tarefaService = tarefaService;
  }

  @PostMapping
  public ResponseEntity<TarefaResponse> salvar(@Valid @RequestBody TarefaDTO dto) {
    TarefaResponse response = tarefaService.salvar(dto);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<List<TarefaResponse>> listarDoUsuario() {
    List<TarefaResponse> response = tarefaService.listarDoUsuario();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TarefaResponse> buscarPorId(@PathVariable Long id) {
    TarefaResponse response = tarefaService.buscarPorId(id);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TarefaResponse> atualizar(
      @PathVariable Long id,
      @Valid @RequestBody TarefaDTO dto) {
    TarefaResponse response = tarefaService.atualizar(id, dto);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluir(@PathVariable Long id) {
    tarefaService.excluir(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/concluir")
  public ResponseEntity<TarefaResponse> concluir(@PathVariable Long id) {
    TarefaResponse response = tarefaService.concluir(id);
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/{id}/reiniciar")
  public ResponseEntity<TarefaResponse> reiniciar(@PathVariable Long id) {
    TarefaResponse response = tarefaService.reiniciar(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/filtros")
  public ResponseEntity<List<TarefaResponse>> buscarComFiltros(
      @RequestParam(required = false) String titulo,
      @RequestParam(required = false) Status status,
      @RequestParam(required = false) Prioridade prioridade,
      @RequestParam(defaultValue = "DESC") String ordem) {

    List<TarefaResponse> response = tarefaService.buscarComFiltros(titulo, status, prioridade, ordem);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/concluidas")
  public ResponseEntity<List<TarefaResponse>> buscarConcluidas() {
    List<TarefaResponse> response = tarefaService.buscarConcluidas();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/pendentes")
  public ResponseEntity<List<TarefaResponse>> buscarPendentes() {
    List<TarefaResponse> response = tarefaService.buscarPendentes();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/prioridade/{prioridade}")
  public ResponseEntity<List<TarefaResponse>> buscarPorPrioridade(
      @PathVariable Prioridade prioridade) {
    List<TarefaResponse> response = tarefaService.buscarPorPrioridade(prioridade);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/estatisticas/total")
  public ResponseEntity<Long> contarTarefasUsuario() {
    Long total = tarefaService.contarTarefasUsuario();
    return ResponseEntity.ok(total);
  }

  @GetMapping("/estatisticas/concluidas")
  public ResponseEntity<Long> contarTarefasConcluidas() {
    Long total = tarefaService.contarTarefasConcluidas();
    return ResponseEntity.ok(total);
  }
}
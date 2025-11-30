package com.example.levelUp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.levelUp.dto.request.MetaDTO;
import com.example.levelUp.dto.response.MetaResponse;
import com.example.levelUp.model.enums.Status;
import com.example.levelUp.service.MetaService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user/metas")
public class MetaController {

  private final MetaService metaService;

  public MetaController(MetaService metaService) {
    this.metaService = metaService;
  }

  @PostMapping
  public ResponseEntity<MetaResponse> salvar(@Valid @RequestBody MetaDTO dto) {
    MetaResponse response = metaService.salvar(dto);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<List<MetaResponse>> listarDoUsuario() {
    List<MetaResponse> response = metaService.listarDoUsuario();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MetaResponse> buscarPorId(@PathVariable Long id) {
    MetaResponse response = metaService.buscarPorId(id);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MetaResponse> atualizar(
      @PathVariable Long id,
      @Valid @RequestBody MetaDTO dto) {
    MetaResponse response = metaService.atualizar(id, dto);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluir(@PathVariable Long id) {
    metaService.excluir(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/concluir")
  public ResponseEntity<MetaResponse> concluir(@PathVariable Long id) {
    MetaResponse response = metaService.concluir(id);
    return ResponseEntity.ok(response);
  }

  // ENDPOINT REINICIAR - ADICIONADO
  @PatchMapping("/{id}/reiniciar")
  public ResponseEntity<MetaResponse> reiniciar(@PathVariable Long id) {
    MetaResponse response = metaService.reiniciar(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/filtros")
  public ResponseEntity<List<MetaResponse>> buscarComFiltros(
      @RequestParam(required = false) String titulo,
      @RequestParam(required = false) Status status,
      @RequestParam(defaultValue = "DESC") String ordem) {

    List<MetaResponse> response = metaService.buscarComFiltros(titulo, status, ordem);
    return ResponseEntity.ok(response);
  }

  // ENDPOINT CONCLUÍDAS - ADICIONADO
  @GetMapping("/concluidas")
  public ResponseEntity<List<MetaResponse>> buscarConcluidas() {
    List<MetaResponse> response = metaService.buscarConcluidas();
    return ResponseEntity.ok(response);
  }

  // ENDPOINT PENDENTES - ADICIONADO
  @GetMapping("/pendentes")
  public ResponseEntity<List<MetaResponse>> buscarPendentes() {
    List<MetaResponse> response = metaService.buscarPendentes();
    return ResponseEntity.ok(response);
  }

  // ENDPOINT ESTATÍSTICAS TOTAL - ADICIONADO
  @GetMapping("/estatisticas/total")
  public ResponseEntity<Long> contarMetasUsuario() {
    Long total = metaService.contarMetasUsuario();
    return ResponseEntity.ok(total);
  }

  // ENDPOINT ESTATÍSTICAS CONCLUÍDAS - ADICIONADO
  @GetMapping("/estatisticas/concluidas")
  public ResponseEntity<Long> contarMetasConcluidas() {
    Long total = metaService.contarMetasConcluidas();
    return ResponseEntity.ok(total);
  }
}
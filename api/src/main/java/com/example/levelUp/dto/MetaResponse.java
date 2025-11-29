package com.example.levelUp.dto;

import java.time.LocalDate;
import com.example.levelUp.model.Meta;
import com.example.levelUp.model.enums.Status;

public record MetaResponse(
    Long id,
    String titulo,
    String descricao,
    LocalDate dataInicio,
    LocalDate dataConclusao,
    Status status,
    boolean concluida) {
  public MetaResponse(Meta meta) {
    this(
        meta.getId(),
        meta.getTitulo(),
        meta.getDescricao(),
        meta.getDataInicio(),
        meta.getDataConclusao(),
        meta.getStatus(),
        meta.isConcluida());
  }
}
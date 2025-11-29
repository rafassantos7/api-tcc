package com.example.levelUp.dto;

import java.time.LocalDate;
import com.example.levelUp.model.Tarefa;
import com.example.levelUp.model.enums.Prioridade;
import com.example.levelUp.model.enums.Status;

public record TarefaResponse(
    Long id,
    String titulo,
    String descricao,
    LocalDate dataInicio,
    LocalDate dataConclusao,
    Status status,
    Prioridade prioridade) {
  public TarefaResponse(Tarefa tarefa) {
    this(
        tarefa.getId(),
        tarefa.getTitulo(),
        tarefa.getDescricao(),
        tarefa.getDataInicio(),
        tarefa.getDataConclusao(),
        tarefa.getStatus(),
        tarefa.getPrioridade());
  }
}
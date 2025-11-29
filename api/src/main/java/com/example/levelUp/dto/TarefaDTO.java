package com.example.levelUp.dto;

import java.time.LocalDate;
import com.example.levelUp.model.Tarefa;
import com.example.levelUp.model.enums.Prioridade;
import com.example.levelUp.model.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TarefaDTO(
    @NotBlank(message = "Título é obrigatório") String titulo,

    String descricao,

    @NotNull(message = "Data de início é obrigatória") LocalDate dataInicio,

    LocalDate dataConclusao, // Pode ser opcional em alguns casos

    Status status,

    Prioridade prioridade) {
  public Tarefa toEntity() {
    return new Tarefa(
        titulo,
        descricao,
        dataInicio,
        dataConclusao,
        status != null ? status : Status.PENDENTE,
        null,
        prioridade != null ? prioridade : Prioridade.BAIXA // Valor Padrão
    );
  }
}
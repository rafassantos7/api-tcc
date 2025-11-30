package com.example.levelUp.dto.request;

import java.time.LocalDate;

import com.example.levelUp.model.Tarefa;
import com.example.levelUp.model.enums.Prioridade;
import com.example.levelUp.model.enums.Status;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TarefaDTO(
    @NotBlank(message = "Título é obrigatório") 
    String titulo,

    String descricao,

    @NotNull(message = "Data de início é obrigatória") 
    LocalDate dataInicio,

    @Future(message = "A data alvo deve ser no futuro") 
    LocalDate dataConclusao,

    Status status,

    Prioridade prioridade
) {
    public Tarefa toEntity() {
        return new Tarefa(
            prioridade,
            titulo,
            descricao,
            dataInicio,
            dataConclusao,
            status != null ? status : Status.PENDENTE
        );
    }
}
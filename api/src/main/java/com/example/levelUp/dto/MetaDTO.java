package com.example.levelUp.dto;

import java.time.LocalDate;
import com.example.levelUp.model.Meta;
import com.example.levelUp.model.enums.Status;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MetaDTO(
    @NotBlank(message = "Título é obrigatório") String titulo,

    String descricao,

    @NotNull(message = "Data de início é obrigatória") LocalDate dataInicio,

    @Future(message = "A data alvo deve ser no futuro") LocalDate dataConclusao,

    Status status) {
  public Meta toEntity() {
    return new Meta(
        titulo,
        descricao,
        dataInicio,
        dataConclusao,
        status != null ? status : Status.PENDENTE,
        null
    );
  }
}
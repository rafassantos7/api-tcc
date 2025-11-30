package com.example.levelUp.dto.request;

import java.time.LocalDate;

import com.example.levelUp.model.Habito;
import com.example.levelUp.model.enums.FrequenciaHabito;
import com.example.levelUp.model.enums.Status;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HabitoDTO(
    @NotBlank(message = "Título é obrigatório") String titulo,

    String descricao,

    @NotNull(message = "Data de início é obrigatória") LocalDate dataInicio,

    LocalDate dataConclusao,

    Status status,

    @NotNull(message = "A frequência é obrigatória") FrequenciaHabito frequencia) {
  public Habito toEntity() {
    return new Habito(
        titulo,
        descricao,
        dataInicio,
        dataConclusao,
        status != null ? status : Status.PENDENTE,
        frequencia);
  }

}

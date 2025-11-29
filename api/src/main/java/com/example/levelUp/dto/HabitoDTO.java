package com.example.levelUp.dto;

import java.time.LocalDate;
import com.example.levelUp.model.Habito;
import com.example.levelUp.model.enums.FrequenciaHabito;
import com.example.levelUp.model.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HabitoDTO(
    @NotBlank(message = "Título é obrigatório") String titulo,

    String descricao,

    @NotNull(message = "Data de início é obrigatória") LocalDate dataInicio,

    LocalDate dataConclusao, // Opcional para hábitos infinitos

    Status status,

    @NotNull(message = "Frequência é obrigatória") FrequenciaHabito frequencia) {
  public Habito toEntity() {
    return new Habito(
        frequencia,
        titulo,
        descricao,
        dataInicio,
        dataConclusao,
        status != null ? status : Status.PENDENTE,
        null // Usuario
    );
  }
}
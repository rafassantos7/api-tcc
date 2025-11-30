package com.example.levelUp.dto.response;

import java.time.LocalDate;

import com.example.levelUp.model.Habito;
import com.example.levelUp.model.enums.FrequenciaHabito;
import com.example.levelUp.model.enums.Status;

public record HabitoResponse(
    String titulo,
    String descricao,
    LocalDate dataInicio,
    LocalDate dataConclusao,
    Status status,
    FrequenciaHabito frequencia) {
  public HabitoResponse(Habito habito) {
    this(
        habito.getTitulo(),
        habito.getDescricao(),
        habito.getDataInicio(),
        habito.getDataConclusao(),
        habito.getStatus(),
        habito.getFrequencia());
  }

}

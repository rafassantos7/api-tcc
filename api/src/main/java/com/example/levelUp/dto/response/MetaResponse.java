package com.example.levelUp.dto.response;

import com.example.levelUp.model.Meta;
import com.example.levelUp.model.enums.Status;

import java.time.LocalDate;

public record MetaResponse(
        Long id,
        String titulo,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataConclusao,
        Status status) {
    public MetaResponse(Meta meta) {
        this(
                meta.getId(),
                meta.getTitulo(),
                meta.getDescricao(),
                meta.getDataInicio(),
                meta.getDataConclusao(),
                meta.getStatus());
    }
}
package com.example.levelUp.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import com.example.levelUp.model.enums.Status;

public class CriarHabitoDTO {

    @NotBlank(message = "Título é obrigatório.")
    private String titulo;

    private String descricao;

    @NotNull(message = "Data de início é obrigatória.")
    private LocalDate dataInicio;

    @NotNull(message = "Data de conclusão é obrigatória.")
    private LocalDate dataConclusao;

    @NotNull(message = "Status é obrigatório.")
    private Status status;

    @NotNull(message = "Id do usuário é obrigatório.")
    private Long usuarioId;

    public CriarHabitoDTO() {}

    public CriarHabitoDTO(@NotBlank(message = "Título é obrigatório.") String titulo, String descricao,
            @NotNull(message = "Data de início é obrigatória.") LocalDate dataInicio,
            @NotNull(message = "Data de conclusão é obrigatória.") LocalDate dataConclusao,
            @NotNull(message = "Status é obrigatório.") Status status,
            @NotNull(message = "Id do usuário é obrigatório.") Long usuarioId) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataConclusao = dataConclusao;
        this.status = status;
        this.usuarioId = usuarioId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    

}

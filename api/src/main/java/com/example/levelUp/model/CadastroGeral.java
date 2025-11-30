package com.example.levelUp.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import com.example.levelUp.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.GeneratedValue;
import com.example.levelUp.validation.DataConclusaoDepoisDeInicio;
import jakarta.persistence.GenerationType;

@DataConclusaoDepoisDeInicio
@MappedSuperclass
public abstract class CadastroGeral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank(message = "Titulo é obrigatório.")
    protected String titulo;

    protected String descricao;

    @NotNull(message = "Data de criação é obrigatória.")
    protected LocalDate dataInicio;

    protected LocalDate dataConclusao;

    @Enumerated(EnumType.STRING)
    protected Status status;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    protected Usuario usuario;

    public CadastroGeral() {
    }

    public CadastroGeral(@NotBlank(message = "Titulo é obrigatório.") String titulo, String descricao,
            @NotNull(message = "Data de criação é obrigatória.") LocalDate dataInicio, LocalDate dataConclusao,
            Status status) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio == null ? LocalDate.now() : dataInicio;
        this.dataConclusao = dataConclusao;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

}


package com.example.aula.model;

import com.example.aula.validation.DataConclusaoDepoisDeInicio;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
@DataConclusaoDepoisDeInicio
public abstract class CadastroGeral {

    @NotBlank(message = "Titulo é obrigatório.")
    protected String titulo;
    
    protected String descricao;

    @NotNull(message = "Data de criação é obrigatória.")
    @FutureOrPresent(message = "Data de criação não pode ser no passado.")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    protected LocalDate dataInicio;


    @NotNull(message = "Data de criação é obrigatória.")
    @Future(message = "Data de conclusão deve ser no futuro.")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    protected LocalDate dataConclusao;

    @Enumerated(EnumType.STRING)
    protected Status status;

    public CadastroGeral() {
    }

    public CadastroGeral(String titulo, String descricao, LocalDate dataInicio, LocalDate dataConclusao, Status status) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataConclusao = dataConclusao;
        this.status = status;
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

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
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

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

}

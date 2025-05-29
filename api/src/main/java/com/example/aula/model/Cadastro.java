package com.example.aula.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public abstract class Cadastro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank(message = "Titulo é obrigatório.")
    protected String titulo;
    protected String descricao;

    @NotNull(message = "Data de criação é obrigatória.")
    @FutureOrPresent(message = "Data de criação não pode ser no passado.")
    protected LocalDate dataInicio;


    @NotNull(message = "Data de criação é obrigatória.")
    @Future(message = "Data de conclusão deve ser no futuro.")
    protected LocalDate dataConclusao;

    protected String status;


}

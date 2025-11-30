package com.example.levelUp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import com.example.levelUp.model.enums.FrequenciaHabito;
import com.example.levelUp.model.enums.Status;
import com.example.levelUp.validation.DataConclusaoDepoisDeInicio;

@Entity
@DataConclusaoDepoisDeInicio
@Table(name = "tab_habitos")
public class Habito extends CadastroGeral {

    @NotNull(message = "A frequencia é obrigatória.")
    @Enumerated(EnumType.STRING)
    private FrequenciaHabito frequencia;

    public Habito() {
    }

   

    public Habito(@NotBlank(message = "Titulo é obrigatório.") String titulo, String descricao,
            @NotNull(message = "Data de criação é obrigatória.") LocalDate dataInicio,
            LocalDate dataConclusao,
            Status status, @NotNull(message = "A frequencia é obrigatória.") FrequenciaHabito frequencia) {
        super(titulo, descricao, dataInicio, dataConclusao, status);
        this.frequencia = frequencia;
    }



    public FrequenciaHabito getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(FrequenciaHabito frequencia) {
        this.frequencia = frequencia;
    }

}


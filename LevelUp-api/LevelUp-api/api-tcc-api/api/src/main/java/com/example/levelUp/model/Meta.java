package com.example.levelUp.model;

import java.time.LocalDate;

import com.example.levelUp.model.enums.Status;

import jakarta.persistence.*;

@Entity
@Table(name = "tab_metas")
public class Meta extends CadastroGeral {
    
    private Boolean concluida = false;

    public Meta() {
    }

    public Meta(String titulo, String descricao, LocalDate dataInicio, LocalDate dataConclusao, Status status) {
        super(titulo, descricao, dataInicio, dataConclusao, status);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getConcluida() {
        return concluida;
    }

    public void setConcluida(Boolean concluida) {
        this.concluida = concluida;
    }

    public Boolean verificarConclusao() {
        if (getStatus() == Status.CONCLUIDA) {
            setConcluida(true);
        } else {
            setConcluida(false);
        }

        return concluida;
    }

}
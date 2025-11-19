package com.example.levelUp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.example.levelUp.model.enums.Prioridade;
import com.example.levelUp.model.enums.Status;

@Entity
@Table(name = "tab_tarefa")
public class Tarefa extends CadastroGeral {
    
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    private Boolean concluida = false;

    public Tarefa() {

    }

    public Tarefa(String titulo, String descricao, LocalDate dataInicio, LocalDate dataConclusao, Status status,
            Prioridade prioridade) {
        super(titulo, descricao, dataInicio, dataConclusao, status);
        this.prioridade = prioridade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
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

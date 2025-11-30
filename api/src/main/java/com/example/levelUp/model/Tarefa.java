package com.example.levelUp.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.example.levelUp.model.enums.Prioridade;
import com.example.levelUp.model.enums.Status;

@Entity
@Table(name = "tab_tarefas")
public class Tarefa extends CadastroGeral {

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    public Tarefa() {
    }

    public Tarefa(Prioridade prioridade, String titulo, String descricao, LocalDate dataInicio, LocalDate dataConclusao, Status status) {
        super(titulo, descricao, dataInicio, dataConclusao, status);
        this.prioridade = prioridade;
    }

    

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }
}
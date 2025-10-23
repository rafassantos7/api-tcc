package com.example.aula.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tab_tarefa")
public class Tarefa extends CadastroGeral{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tarefa;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    public Tarefa(Long id_tarefa, Prioridade prioridade) {
        this.id_tarefa = id_tarefa;
        this.prioridade = prioridade;
    }

    public Long getId_tarefa() {
        return id_tarefa;
    }

    public void setId_tarefa(Long id_tarefa) {
        this.id_tarefa = id_tarefa;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }
}

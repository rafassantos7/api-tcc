package com.example.aula.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tab_tarefa")
public class Tarefa extends CadastroGeral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_tarefa;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    public Tarefa(String titulo,
                  String descricao,
                  LocalDate dataInicio,
                  LocalDate dataConclusao,
                  Status status,
                  int id_tarefa,
                  Prioridade prioridade) {

        super(titulo, descricao, dataInicio, dataConclusao, status);

        if (prioridade == null) {
            throw new IllegalArgumentException("Prioridade não pode ser vazio");
        }

        this.id_tarefa = id_tarefa;
        this.prioridade = prioridade;
    }

    public Tarefa() {} // Construtor vazio exigido pelo JPA

    public int getId_tarefa() {
        return id_tarefa;
    }

    public void setId_tarefa(int id_tarefa) {
        this.id_tarefa = id_tarefa;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {

        if (prioridade == null) {
            throw new IllegalArgumentException("Prioridade não pode ser vazio");
        }

        this.prioridade = prioridade;
    }
}

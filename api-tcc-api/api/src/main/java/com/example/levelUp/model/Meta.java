package com.example.levelUp.model;

import java.time.LocalDate;

import com.example.levelUp.model.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "tab_metas")
public class Meta extends CadastroGeral {

    public Meta() {
    }

    public Meta(String titulo, String descricao, LocalDate dataInicio, LocalDate dataConclusao, Status status,
            Usuario usuario) {
        super(titulo, descricao, dataInicio, dataConclusao, status, usuario);
    }

    @Transient
    public boolean isConcluida() {
        return this.status == Status.CONCLUIDA;
    }
}
package com.example.levelUp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.example.levelUp.model.enums.Status;

@Entity
@Table(name = "tab_habitos")
public class Habito extends CadastroGeral {
    
    
    public Habito() {
    }

    public Habito(Long id, String titulo, String descricao, LocalDate dataInicio,
         LocalDate dataConclusao, Status status) {
        super(titulo, descricao, dataInicio, dataConclusao, status);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

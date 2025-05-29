package com.example.aula.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tab_habitos")
public class Habitos extends CadastroTarefas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private Boolean feito = false;
    
    
    public Habitos() {
    }

    
    public Habitos(Long id, Boolean feito) {
        this.id = id;
        this.feito = feito;
    }


  
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Boolean getFeito() {
        return feito;
    }


    public void setFeito(Boolean feito) {
        this.feito = feito;
    }


    // Getters, setters e método confirmarRealizacao()
    public void confirmarRealizacao() {
        this.feito = true;
        this.dataConclusao = LocalDate.now();
    }
}

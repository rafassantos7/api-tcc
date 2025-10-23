package com.example.aula.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tab_metas")
public class Metas extends CadastroTarefas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private Boolean atingida = false;

    
    public Metas() {
    }

    

    public Metas(Long id, Boolean atingida) {
        this.id = id;
        this.atingida = atingida;
    }



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public Boolean getAtingida() {
        return atingida;
    }



    public void setAtingida(Boolean atingida) {
        this.atingida = atingida;
    }



}
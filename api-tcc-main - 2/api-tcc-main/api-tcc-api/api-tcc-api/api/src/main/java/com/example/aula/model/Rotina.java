package com.example.aula.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tab_rotina")
public class Rotina extends CadastroGeral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_rotina;


    private String horario_inicio;
    private String horario_fim;

    @ManyToOne()
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Rotina(Long id_rotina, String horario_inicio, String horario_fim, Usuario usuario) {
        this.id_rotina = id_rotina;
        this.horario_inicio = horario_inicio;
        this.horario_fim = horario_fim;
        this.usuario = usuario;
    }

    public Long getId_rotina() {
        return id_rotina;
    }

    public void setId_rotina(Long id_rotina) {
        this.id_rotina = id_rotina;
    }

    public String getHorario_inicio() {
        return horario_inicio;
    }

    public void setHorario_inicio(String horario_inicio) {
        this.horario_inicio = horario_inicio;
    }

    public String getHorario_fim() {
        return horario_fim;
    }

    public void setHorario_fim(String horario_fim) {
        this.horario_fim = horario_fim;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

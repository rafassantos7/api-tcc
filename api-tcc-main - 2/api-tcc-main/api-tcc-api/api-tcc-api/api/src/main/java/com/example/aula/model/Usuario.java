package com.example.aula.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity
@Table(name = "tab_usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório.")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    private String email;

    @NotBlank(message = "Telefone é obrigatorio.")
    private String telefone;

    @NotBlank(message = "Senha é obrigatorio.")
    private String senha;

    @NotNull(message = "Data de nascimento é obrigatória.")
    @Past(message = "Data de nascimento deve ser no passado.")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "usuario")
    private List<Rotina> rotinas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Metas> metas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Habitos> habitos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Rotina> rotina = new ArrayList<>();

    public Usuario() {
    }

    

   
    public Usuario(Long id, @NotBlank(message = "Nome é obrigatório.") String nome,
            @NotBlank(message = "Email é obrigatório") String email,
            @NotBlank(message = "Telefone é obrigatorio.") String telefone,
            @NotBlank(message = "Senha é obrigatorio.") String senha,
            @NotNull(message = "Data de nascimento é obrigatória.") @Past(message = "Data de nascimento deve ser no passado.") LocalDate dataNascimento,
            List<Metas> metas, List<Habitos> habitos, List<Rotina> rotina) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.metas = metas;
        this.habitos = habitos;
        this.rotina = rotina;
    }


    

    public Long getId() {
        return id;
    }




    public void setId(Long id) {
        this.id = id;
    }




    public String getNome() {
        return nome;
    }




    public void setNome(String nome) {
        this.nome = nome;
    }




    public String getEmail() {
        return email;
    }




    public void setEmail(String email) {
        this.email = email;
    }




    public String getTelefone() {
        return telefone;
    }




    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }




    public String getSenha() {
        return senha;
    }




    public void setSenha(String senha) {
        this.senha = senha;
    }




    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }




    public List<Metas> getMetas() {
        return metas;
    }




    public void setMetas(List<Metas> metas) {
        this.metas = metas;
    }




    public List<Habitos> getHabitos() {
        return habitos;
    }




    public void setHabitos(List<Habitos> habitos) {
        this.habitos = habitos;
    }


    public List<Rotina> getRotina() {
        return rotina;
    }

    public void setRotina(List<Rotina> rotina) {
        this.rotina = rotina;
    }
}

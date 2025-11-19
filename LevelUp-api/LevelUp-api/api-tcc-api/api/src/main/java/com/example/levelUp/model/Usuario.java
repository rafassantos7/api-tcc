package com.example.levelUp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tab_usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 6, message = "Digite seu nome completo.")
    @NotBlank(message = "Nome é obrigatório.")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    private String email;

    @NotBlank(message = "Telefone é obrigatorio.")
    private String telefone;

    @Size(min = 8, message = "A senha deve ter pelo menos 6 caracteres.")
    @NotBlank(message = "Senha é obrigatoria.")
    private String senha;

    @NotNull(message = "Data de nascimento é obrigatória.")
    @Past(message = "Data de nascimento deve ser no passado.")
    private LocalDate dataNascimento;

    @OneToMany(mappedBy= "usuario" ,cascade = CascadeType.ALL)
    private List<Meta> metas = new ArrayList<>();

    @OneToMany(mappedBy= "usuario",cascade = CascadeType.ALL)
    private List<Tarefa> tarefas = new ArrayList<>();

    @OneToMany(mappedBy= "usuario",cascade = CascadeType.ALL)
    private List<Habito> habitos = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String email, String nome, String senha, LocalDate dataNascimento, String telefone, List<Meta> metas,
            List<Habito> habitos, List<Tarefa> tarefas) {
        this.email = formatarEmail(email);
        this.nome = nome;
        this.senha = validarSenha(senha);
        this.dataNascimento = dataNascimento;
        this.telefone = validarTelefone(telefone);
        this.metas = (metas != null) ? metas : new ArrayList<>();
        this.habitos = (habitos != null) ? habitos : new ArrayList<>();
        this.tarefas = (tarefas != null) ? tarefas : new ArrayList<>();

    }

    public String formatarEmail(String email) {
        return email.toLowerCase();
    }

    public String validarTelefone(String telefone) {
        return telefone.replaceAll("[^0-9]", "");
    }

    public String validarSenha(String senha) {
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[\\W_]).{8,}$";

        if (senha.matches(regex)) {
            return senha;
        } else {
            throw new IllegalArgumentException(
                    "A senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, um número e um caractere especial.");
        }
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

    public List<Meta> getMetas() {
        return metas;
    }

    public void setMetas(List<Meta> metas) {
        this.metas = metas;
        for (Meta m : metas) {
            m.setUsuario(this);
        }
    }

    public List<Habito> getHabitos() {
        return habitos;
    }

    public void setHabitos(List<Habito> habitos) {
        this.habitos = habitos;
        for (Habito h : habitos) {
            h.setUsuario(this);
        }
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
        for (Tarefa t : tarefas) {
            t.setUsuario(this);
        }
    }

}

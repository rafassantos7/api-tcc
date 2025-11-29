package com.example.levelUp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tab_usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 6, message = "Digite seu nome completo.")
    @NotBlank(message = "Nome é obrigatório.")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido.")
    private String email;

    @NotBlank(message = "Telefone é obrigatório.")
    private String telefone;

    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres.")
    @NotBlank(message = "Senha é obrigatória.")
    private String senha;

    @NotNull(message = "Data de nascimento é obrigatória.")
    @Past(message = "Data de nascimento deve ser no passado.")
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Meta> metas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Tarefa> tarefas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Habito> habitos = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String email, String nome, String senha, LocalDate dataNascimento, String telefone,
            List<Meta> metas, List<Habito> habitos, List<Tarefa> tarefas) {

        this.nome = nome;
        this.email = formatarEmail(email);
        this.dataNascimento = dataNascimento;

        this.telefone = validarTelefone(telefone);
        this.senha = validarSenha(senha);

        this.setMetas((metas != null) ? metas : new ArrayList<>());
        this.setHabitos((habitos != null) ? habitos : new ArrayList<>());
        this.setTarefas((tarefas != null) ? tarefas : new ArrayList<>());
    }

    // --- Métodos Auxiliares ---

    public String formatarEmail(String email) {
        return (email != null) ? email.toLowerCase().trim() : null;
    }

    public String validarTelefone(String telefone) {
        return (telefone != null) ? telefone.replaceAll("[^0-9]", "") : null;
    }

    public String validarSenha(String senha) {
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[\\W_]).{8,}$";
        if (senha != null && senha.matches(regex)) {
            return senha;
        } else {
            throw new IllegalArgumentException(
                    "A senha deve ter: 8 caracteres, 1 Maiúscula, 1 Número e 1 Especial.");
        }
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Meta> getMetas() {
        return metas;
    }

    public void setMetas(List<Meta> metas) {
        this.metas = metas;

        if (metas != null) {
            for (Meta m : metas) {
                m.setUsuario(this);
            }
        }
    }

    public List<Habito> getHabitos() {
        return habitos;
    }

    public void setHabitos(List<Habito> habitos) {
        this.habitos = habitos;
        if (habitos != null) {
            for (Habito h : habitos) {
                h.setUsuario(this);
            }
        }
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
        if (tarefas != null) {
            for (Tarefa t : tarefas) {
                t.setUsuario(this);
            }
        }
    }

    // --- Getters e Setters Padrão ---

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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSenha() {
        return senha;
    }

    // --- UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
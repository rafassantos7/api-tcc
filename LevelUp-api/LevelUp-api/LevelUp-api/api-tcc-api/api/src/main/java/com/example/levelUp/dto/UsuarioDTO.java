package com.example.levelUp.dto;

import jakarta.validation.constraints.*;

public class UsuarioDTO {

    @NotBlank(message = "Nome é obrigatório.")
    @Size(min = 3, message = "O nome deve ter ao menos 3 caracteres.")
    private String nome;

    @NotBlank(message = "Email é obrigatório.")
    @Email(message = "Email inválido.")
    private String email;

    @NotBlank(message = "Telefone é obrigatório.")
    private String telefone;

    @NotBlank(message = "Senha é obrigatória.")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres.")
    private String senha;

    @NotNull(message = "Data de nascimento é obrigatória.")
    private java.time.LocalDate dataNascimento;

    public UsuarioDTO() {}

   

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

    public java.time.LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(java.time.LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
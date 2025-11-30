package com.example.levelUp.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateDTO(
    @Size(min = 3, message = "O nome deve ter ao menos 3 caracteres.") 
    String nome,

    @Email(message = "Email inválido.") 
    String email,

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos numéricos (DDD+Número).") 
    String telefone,

    LocalDate dataNascimento,
    
    // Senha opcional para atualização
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[\\W_]).{8,}$", message = "Senha fraca: Precisa de 8 caracteres, 1 Maiúscula, 1 Número e 1 Símbolo.") 
    String senha
) {}

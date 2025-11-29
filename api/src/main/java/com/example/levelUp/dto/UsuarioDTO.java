package com.example.levelUp.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioDTO(
                @NotBlank(message = "Nome é obrigatório.") @Size(min = 3, message = "O nome deve ter ao menos 3 caracteres.") String nome,

                @NotBlank(message = "Email é obrigatório.") @Email(message = "Email inválido.") String email,

                @NotBlank(message = "Telefone é obrigatório.") @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos numéricos (DDD+Número).") String telefone,

                @NotBlank(message = "Senha é obrigatória.") @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[\\W_]).{8,}$", message = "Senha fraca: Precisa de 8 caracteres, 1 Maiúscula, 1 Número e 1 Símbolo.") String senha,

                @NotNull(message = "Data de nascimento é obrigatória.") LocalDate dataNascimento) {
}
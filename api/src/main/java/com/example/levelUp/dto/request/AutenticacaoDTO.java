package com.example.levelUp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record AutenticacaoDTO(@NotEmpty @Email String email, @NotEmpty String senha) {

}

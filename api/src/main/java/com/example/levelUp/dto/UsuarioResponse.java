package com.example.levelUp.dto;

import java.time.LocalDate;
import com.example.levelUp.model.Usuario;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String telefone,
        LocalDate dataNascimento) {
    public UsuarioResponse(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getDataNascimento());
    }
}
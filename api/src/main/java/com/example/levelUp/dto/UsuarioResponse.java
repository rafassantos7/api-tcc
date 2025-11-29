package com.example.levelUp.dto;

import com.example.levelUp.model.Usuario;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String telefone) {
    public UsuarioResponse(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone());
    }
}
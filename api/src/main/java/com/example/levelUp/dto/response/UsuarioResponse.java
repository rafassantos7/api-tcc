package com.example.levelUp.dto.response;

import java.time.LocalDate;

import com.example.levelUp.model.Usuario;

public record UsuarioResponse(
                String nome,
                String email,
                String telefone,
                LocalDate dataNascimento) {

        public UsuarioResponse(Usuario usuario) {
                this(                                   usuario.getNome(),                              usuario.getEmail(),                                  usuario.getTelefone(),                                      usuario.getDataNascimento());         }
}

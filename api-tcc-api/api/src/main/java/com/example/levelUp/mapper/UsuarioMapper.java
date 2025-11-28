package com.example.levelUp.mapper;

import com.example.levelUp.dto.UsuarioDTO;
import com.example.levelUp.model.Usuario;
import java.util.ArrayList;

public class UsuarioMapper {

    // DTO → Entidade (Criação)
    public static Usuario toEntity(UsuarioDTO dto) {
        // Usa o construtor completo da Entidade para garantir validações e listas
        // vazias
        return new Usuario(
                dto.email(),
                dto.nome(),
                dto.senha(), // Obs: O Service vai criptografar isso depois
                dto.dataNascimento(),
                dto.telefone(),
                new ArrayList<>(), // Metas
                new ArrayList<>(), // Habitos
                new ArrayList<>() // Tarefas
        );
    }

    // Atualização Parcial (Sem mexer na senha!)
    public static void updateEntity(Usuario usuario, UsuarioDTO dto) {
        if (dto.nome() != null) {
            usuario.setNome(dto.nome());
        }

        if (dto.email() != null) {
            usuario.setEmail(dto.email());
        }

        if (dto.telefone() != null) {
            usuario.setTelefone(dto.telefone());
        }

        if (dto.dataNascimento() != null) {
            usuario.setDataNascimento(dto.dataNascimento());
        }

    }
}
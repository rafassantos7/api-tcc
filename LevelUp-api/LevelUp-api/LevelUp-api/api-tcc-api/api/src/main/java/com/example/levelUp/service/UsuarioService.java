package com.example.levelUp.service;


import com.example.levelUp.dto.UsuarioDTO;
import com.example.levelUp.exception.EmailJaCadastradoException;
import com.example.levelUp.model.Habito;
import com.example.levelUp.model.Meta;
import com.example.levelUp.model.Usuario;
import com.example.levelUp.repository.UsuarioRepository;

import main.java.com.example.levelUp.mapper.UsuarioMapper;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import com.example.levelUp.repository.MetaRepository;

@Service
@Validated
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private MetaRepository metasRepository;
    private UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, MetaRepository metasRepository) {
        this.usuarioRepository = usuarioRepository;
        this.metasRepository = metasRepository;
    }

   

    public List<Meta> listarMetasdoUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        return metasRepository.findByUsuarioId(usuarioId);
    }

    public UsuarioResponseDTO salvar(UsuarioDTO dto) {

        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailJaCadastradoException("Email já cadastrado.");
        }

        Usuario usuario = UsuarioMapper.toEntity(dto);
        Usuario salvo = usuarioRepository.save(usuario);

        return UsuarioMapper.toResponse(salvo);
    }

   public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
    Usuario usuarioBanco = usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

    // Atualiza apenas os campos permitidos via DTO
    usuarioBanco.setNome(dto.getNome());
    usuarioBanco.setEmail(dto.getEmail());
    usuarioBanco.setSenha(dto.getSenha());
    usuarioBanco.setTelefone(dto.getTelefone());

    Usuario salvo = usuarioRepository.save(usuarioBanco);

    return UsuarioMapper.toResponseDTO(salvo);
}


    public void excluir(Long id) {
        Usuario usuarioExcluir = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioRepository.deleteById(usuarioExcluir.getId());
    }

    public Usuario adicionarMeta(Long usuarioId, List<Meta> metas) {
        UsuaArio usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        usuario.setMetas(metas);
        return usuarioRepository.save(usuario);
    }

    public Usuario adicionarHabito(Long usuarioId, List<Habito> habitos) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        usuario.setHabitos(habitos);
        return usuarioRepository.save(usuario);
    }

}
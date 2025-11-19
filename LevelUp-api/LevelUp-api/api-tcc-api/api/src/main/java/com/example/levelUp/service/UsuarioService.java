package com.example.levelUp.service;

import com.example.levelUp.exception.EmailJaCadastradoException;
import com.example.levelUp.model.Habito;
import com.example.levelUp.model.Meta;
import com.example.levelUp.model.Usuario;
import com.example.levelUp.repository.UsuarioRepository;

import jakarta.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import com.example.levelUp.repository.MetaRepository;

@Service
@Validated
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private MetaRepository metasRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, MetaRepository metasRepository) {
        this.usuarioRepository = usuarioRepository;
        this.metasRepository = metasRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public List<Meta> listarMetasdoUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        return metasRepository.findByUsuarioId(usuarioId);
    }

    public Usuario salvar(@Valid Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new EmailJaCadastradoException("Usuário já cadastrado.");
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(@Valid Usuario usuario) {
        Usuario usuarioAtualizar = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario não encontrado."));

        usuarioAtualizar.setNome(usuario.getNome());
        usuarioAtualizar.setEmail(usuario.getEmail());
        usuarioAtualizar.setSenha(usuario.getSenha());
        usuarioAtualizar.setTelefone(usuario.getTelefone());

        return usuarioRepository.save(usuarioAtualizar);
    }

    public void excluir(Long id) {
        Usuario usuarioExcluir = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioRepository.deleteById(usuarioExcluir.getId());
    }

    public Usuario adicionarMeta(Long usuarioId, List<Meta> metas) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
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
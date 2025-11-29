package com.example.levelUp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.levelUp.dto.UsuarioDTO;
import com.example.levelUp.dto.UsuarioResponse;
import com.example.levelUp.exception.EmailJaCadastradoException;
import com.example.levelUp.exception.RecursoNaoEncontradoException;
import com.example.levelUp.model.Meta;
import com.example.levelUp.model.Usuario;
import com.example.levelUp.repository.MetaRepository;
import com.example.levelUp.repository.UsuarioRepository;

@Service
@Validated
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final MetaRepository metasRepository;
    private final PasswordEncoder passwordEncoder; // <--- CRUCIAL PARA O LOGIN FUNCIONAR

    public UsuarioService(UsuarioRepository usuarioRepository,
            MetaRepository metasRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.metasRepository = metasRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse salvar(UsuarioDTO dto) {
        if (usuarioRepository.findByEmail(dto.email()) != null) { // findByEmail agora retorna objeto ou null
            throw new EmailJaCadastradoException("Email já cadastrado.");
        }

        Usuario usuario = new Usuario(
                dto.email(),
                dto.nome(),
                dto.senha(),
                dto.dataNascimento(),
                dto.telefone(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());

        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        Usuario salvo = usuarioRepository.save(usuario);

        return new UsuarioResponse(salvo);
    }

    public List<Meta> listarMetasDoUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

        return metasRepository.findByUsuario(usuario);
    }

    public UsuarioResponse atualizar(Long id, UsuarioDTO dto) {
        Usuario usuarioBanco = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

       Optional.ofNullable(dto.nome()).ifPresent(usuarioBanco::setNome);
        Optional.ofNullable(dto.email()).ifPresent(usuarioBanco::setEmail);
        Optional.ofNullable(dto.telefone()).ifPresent(usuarioBanco::setTelefone);
        Optional.ofNullable(dto.dataNascimento()).ifPresent(usuarioBanco::setDataNascimento);

        Usuario salvo = usuarioRepository.save(usuarioBanco);

        return new UsuarioResponse(salvo);
    }

    public void excluir(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
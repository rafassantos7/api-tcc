package com.example.levelUp.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.levelUp.dto.request.UsuarioDTO;
import com.example.levelUp.dto.response.UsuarioResponse;
import com.example.levelUp.exception.EmailJaCadastradoException;
import com.example.levelUp.exception.RecursoNaoEncontradoException;
import com.example.levelUp.model.Usuario;
import com.example.levelUp.repository.UsuarioRepository;

@Service
@Validated
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Usuario getUsuarioLogado() {
        try {
            return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new RecursoNaoEncontradoException("Usuário não autenticado.");
        }
    }

    // NOVO MÉTODO PÚBLICO para outros services
    public Usuario obterUsuarioAutenticado() {
        return getUsuarioLogado();
    }

    public UsuarioResponse salvar(UsuarioDTO dto) {
        if (usuarioRepository.findByEmail(dto.email()) != null) {
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

    public UsuarioResponse buscarMeuPerfil() {
        Usuario usuarioDoToken = getUsuarioLogado();

        Usuario usuarioBanco = usuarioRepository.findById(usuarioDoToken.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

        return new UsuarioResponse(usuarioBanco);
    }

    public UsuarioResponse atualizarMeuPerfil(UsuarioDTO dto) {
        Usuario usuarioDoToken = getUsuarioLogado();

        Usuario usuarioBanco = usuarioRepository.findById(usuarioDoToken.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

        Optional.ofNullable(dto.nome()).ifPresent(usuarioBanco::setNome);
        Optional.ofNullable(dto.email()).ifPresent(usuarioBanco::setEmail);
        Optional.ofNullable(dto.telefone()).ifPresent(usuarioBanco::setTelefone);
        Optional.ofNullable(dto.dataNascimento()).ifPresent(usuarioBanco::setDataNascimento);

        Usuario salvo = usuarioRepository.save(usuarioBanco);
        return new UsuarioResponse(salvo);
    }

    public void excluirMeuPerfil() {
        Usuario usuarioDoToken = getUsuarioLogado();
        usuarioRepository.deleteById(usuarioDoToken.getId());
    }
}
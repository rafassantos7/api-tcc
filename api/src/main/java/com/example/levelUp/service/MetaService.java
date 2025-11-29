package com.example.levelUp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.levelUp.exception.RecursoNaoEncontradoException;
import com.example.levelUp.model.Meta;
import com.example.levelUp.model.Usuario;
import com.example.levelUp.model.enums.Status;
import com.example.levelUp.repository.MetaRepository;

import jakarta.validation.Valid;

@Service
@Validated
public class MetaService {

    private final MetaRepository metasRepository;

    public MetaService(MetaRepository metasRepository) {
        this.metasRepository = metasRepository;
    }

     private Usuario getUsuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<Meta> listarTodos() {
        return metasRepository.findByUsuario(getUsuarioLogado());
    }

    public List<Meta> listarConcluidas() {
        List<Meta> minhasMetas = metasRepository.findByUsuario(getUsuarioLogado());

         List<Meta> concluidas = minhasMetas.stream()
                .filter(m -> m.getStatus() == Status.CONCLUIDA)
                .collect(Collectors.toList());

        if (concluidas.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma meta concluída encontrada.");
        }
        return concluidas;
    }

    public Meta salvar(@Valid Meta meta) {
        meta.setUsuario(getUsuarioLogado());
        return metasRepository.save(meta);
    }

    public Meta atualizar(@Valid Meta meta) {
        Usuario usuarioLogado = getUsuarioLogado();

        Meta metaAtualizar = metasRepository.findById(meta.getId())
                .filter(m -> m.getUsuario().getId().equals(usuarioLogado.getId()))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada."));

         Optional.ofNullable(meta.getTitulo()).ifPresent(metaAtualizar::setTitulo);
        Optional.ofNullable(meta.getDescricao()).ifPresent(metaAtualizar::setDescricao);
        Optional.ofNullable(meta.getStatus()).ifPresent(metaAtualizar::setStatus);
        Optional.ofNullable(meta.getDataInicio()).ifPresent(metaAtualizar::setDataInicio);
        Optional.ofNullable(meta.getDataConclusao()).ifPresent(metaAtualizar::setDataConclusao);

        return metasRepository.save(metaAtualizar);
    }

    public void excluir(Long id) {
        Usuario usuarioLogado = getUsuarioLogado();

        Meta metaExcluir = metasRepository.findById(id)
                .filter(m -> m.getUsuario().getId().equals(usuarioLogado.getId()))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada."));

        metasRepository.delete(metaExcluir);
    }
}
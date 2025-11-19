package com.example.levelUp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.levelUp.exception.RecursoNaoEncontradoException;
import com.example.levelUp.model.Meta;
import com.example.levelUp.repository.MetaRepository;

import jakarta.validation.Valid;

@Service
@Validated
public class MetaService {
    private final MetaRepository metasRepository;

    public MetaService(MetaRepository metasRepository) {
        this.metasRepository = metasRepository;
    }

    public List<Meta> listarTodos() {
        return metasRepository.findAll();
    }

    public List<Meta> listarConcluidas() {

        List<Meta> metasConcluidas = metasRepository.findByConcluida(true);

        if (metasConcluidas.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma meta concluída encontrada.");
        }
        return metasConcluidas;
    }

    public Meta salvar(@Valid Meta metas) {

        return metasRepository.save(metas);
    }

    public Meta atualizar(@Valid Meta metas) {
        Meta metasAtualizar = metasRepository.findById(metas.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada."));

        if (metas.getTitulo() != null) {
            metasAtualizar.setTitulo(metas.getTitulo());
        }

        if (metas.getDescricao() != null) {
            metasAtualizar.setDescricao(metas.getDescricao());
        }

        if (metas.getStatus() != null) {
            metasAtualizar.setStatus(metas.getStatus());
        }

        if (metas.getDataConclusao() != null) {
            metasAtualizar.setDataConclusao(metas.getDataConclusao());
        }

        return metasRepository.save(metasAtualizar);
    }

    public void excluir(Long id) {
        Meta metasExcluir = metasRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada"));

        metasRepository.delete(metasExcluir);
    }

}

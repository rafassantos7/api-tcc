package com.example.levelUp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.levelUp.dto.MetaDTO;
import com.example.levelUp.dto.MetaResponse;
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

    // LISTAR (Retorna DTOs)
    public List<MetaResponse> listarTodos() {
        List<Meta> metas = metasRepository.findByUsuario(getUsuarioLogado());

        // Converte lista de Entidades para lista de Responses
        return metas.stream()
                .map(MetaResponse::new)
                .collect(Collectors.toList());
    }

    // LISTAR CONCLUÍDAS
    public List<MetaResponse> listarConcluidas() {
        List<Meta> minhasMetas = metasRepository.findByUsuario(getUsuarioLogado());

        List<MetaResponse> concluidas = minhasMetas.stream()
                .filter(m -> m.getStatus() == Status.CONCLUIDA)
                .map(MetaResponse::new) // Converte
                .collect(Collectors.toList());

        if (concluidas.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma meta concluída encontrada.");
        }
        return concluidas;
    }

    // SALVAR (Recebe DTO -> Devolve Response)
    public MetaResponse salvar(@Valid MetaDTO dto) {
        Meta meta = dto.toEntity(); // Converte
        meta.setUsuario(getUsuarioLogado()); // Vincula

        Meta metaSalva = metasRepository.save(meta);

        return new MetaResponse(metaSalva); // Reconverte
    }

    // ATUALIZAR
    public MetaResponse atualizar(Long id, @Valid MetaDTO dto) {
        Usuario usuarioLogado = getUsuarioLogado();

        Meta metaAtualizar = metasRepository.findById(id)
                .filter(m -> m.getUsuario().getId().equals(usuarioLogado.getId()))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada."));

        // Atualização segura usando os dados do DTO
        Optional.ofNullable(dto.titulo()).ifPresent(metaAtualizar::setTitulo);
        Optional.ofNullable(dto.descricao()).ifPresent(metaAtualizar::setDescricao);
        Optional.ofNullable(dto.status()).ifPresent(metaAtualizar::setStatus);
        Optional.ofNullable(dto.dataInicio()).ifPresent(metaAtualizar::setDataInicio);
        Optional.ofNullable(dto.dataConclusao()).ifPresent(metaAtualizar::setDataConclusao);

        Meta metaSalva = metasRepository.save(metaAtualizar);

        return new MetaResponse(metaSalva);
    }

    public void excluir(Long id) {
        Usuario usuarioLogado = getUsuarioLogado();
        Meta metaExcluir = metasRepository.findById(id)
                .filter(m -> m.getUsuario().getId().equals(usuarioLogado.getId()))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada."));
        metasRepository.delete(metaExcluir);
    }
}
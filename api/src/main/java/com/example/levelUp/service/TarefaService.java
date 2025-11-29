package com.example.levelUp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.levelUp.dto.TarefaDTO;
import com.example.levelUp.dto.TarefaResponse;
import com.example.levelUp.exception.RecursoNaoEncontradoException;
import com.example.levelUp.model.Tarefa;
import com.example.levelUp.model.Usuario;
import com.example.levelUp.model.enums.Status;
import com.example.levelUp.repository.TarefaRepository;

import jakarta.validation.Valid;

@Service
@Validated
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    private Usuario getUsuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<TarefaResponse> listarTarefas() {
        return tarefaRepository.findByUsuario(getUsuarioLogado())
                .stream()
                .map(TarefaResponse::new)
                .collect(Collectors.toList());
    }

    public List<TarefaResponse> listarConcluidas() {
        List<Tarefa> minhasTarefas = tarefaRepository.findByUsuario(getUsuarioLogado());

        List<TarefaResponse> concluidas = minhasTarefas.stream()
                .filter(t -> t.getStatus() == Status.CONCLUIDA)
                .map(TarefaResponse::new)
                .collect(Collectors.toList());

        if (concluidas.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma tarefa concluída encontrada.");
        }
        return concluidas;
    }

    public TarefaResponse salvarTarefa(@Valid TarefaDTO dto) {
        Tarefa tarefa = dto.toEntity();
        tarefa.setUsuario(getUsuarioLogado());

        Tarefa salvo = tarefaRepository.save(tarefa);
        return new TarefaResponse(salvo);
    }

    public TarefaResponse atualizar(Long id, @Valid TarefaDTO dto) {
        Usuario usuarioLogado = getUsuarioLogado();

        Tarefa tarefaAtualizar = tarefaRepository.findById(id)
                .filter(t -> t.getUsuario().getId().equals(usuarioLogado.getId()))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tarefa não encontrada."));

        Optional.ofNullable(dto.titulo()).ifPresent(tarefaAtualizar::setTitulo);
        Optional.ofNullable(dto.descricao()).ifPresent(tarefaAtualizar::setDescricao);
        Optional.ofNullable(dto.status()).ifPresent(tarefaAtualizar::setStatus);
        Optional.ofNullable(dto.dataConclusao()).ifPresent(tarefaAtualizar::setDataConclusao);
        Optional.ofNullable(dto.prioridade()).ifPresent(tarefaAtualizar::setPrioridade);

        return new TarefaResponse(tarefaRepository.save(tarefaAtualizar));
    }

    public void excluirTarefa(Long id) {
        Usuario usuarioLogado = getUsuarioLogado();
        Tarefa tarefaExcluir = tarefaRepository.findById(id)
                .filter(t -> t.getUsuario().getId().equals(usuarioLogado.getId()))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tarefa não encontrada"));
        tarefaRepository.delete(tarefaExcluir);
    }
}
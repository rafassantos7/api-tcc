package com.example.levelUp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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

    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findByUsuario(getUsuarioLogado());
    }

    public List<Tarefa> listarConcluidas() {
        List<Tarefa> minhasTarefas = tarefaRepository.findByUsuario(getUsuarioLogado());

        List<Tarefa> concluidas = minhasTarefas.stream()
                .filter(t -> t.getStatus() == Status.CONCLUIDA)
                .collect(Collectors.toList());

        if (concluidas.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma tarefa concluída encontrada.");
        }
        return concluidas;
    }

    public Tarefa salvarTarefa(@Valid Tarefa tarefa) {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        tarefa.setUsuario(usuarioLogado);

        return tarefaRepository.save(tarefa);
    }

    public Tarefa atualizar(@Valid Tarefa tarefa) {
        Usuario usuarioLogado = getUsuarioLogado();

        Tarefa tarefaAtualizar = tarefaRepository.findById(tarefa.getId())
                .filter(t -> t.getUsuario().getId().equals(usuarioLogado.getId()))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tarefa não encontrada."));

        Optional.ofNullable(tarefa.getTitulo()).ifPresent(tarefaAtualizar::setTitulo);
        Optional.ofNullable(tarefa.getDescricao()).ifPresent(tarefaAtualizar::setDescricao);
        Optional.ofNullable(tarefa.getStatus()).ifPresent(tarefaAtualizar::setStatus);

        Optional.ofNullable(tarefa.getDataConclusao()).ifPresent(tarefaAtualizar::setDataConclusao);
        Optional.ofNullable(tarefa.getPrioridade()).ifPresent(tarefaAtualizar::setPrioridade);

        return tarefaRepository.save(tarefaAtualizar);
    }

    public void excluirTarefa(Long id) {
        Usuario usuarioLogado = getUsuarioLogado();

        Tarefa tarefaExcluir = tarefaRepository.findById(id)
                .filter(t -> t.getUsuario().getId().equals(usuarioLogado.getId()))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tarefa não encontrada"));

        tarefaRepository.delete(tarefaExcluir);
    }
}
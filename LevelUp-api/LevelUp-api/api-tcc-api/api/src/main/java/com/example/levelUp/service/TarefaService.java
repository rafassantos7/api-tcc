package com.example.levelUp.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.example.levelUp.exception.RecursoNaoEncontradoException;
import com.example.levelUp.model.Tarefa;
import com.example.levelUp.repository.TarefaRepository;

import jakarta.validation.Valid;

@Service
@Validated
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    public List<Tarefa> listarConcluidas() {
        List<Tarefa> tarefaConcluidas = tarefaRepository.findByConcluida(true);

        if (tarefaConcluidas.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma tarefa concluída encontrada.");
        }
        return tarefaConcluidas;
    }

    public Tarefa salvarTarefa(@Valid Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public Tarefa atualizar(@Valid Tarefa tarefa) {
        // Busca a tarefa existente pelo ID
        Tarefa tarefaAtualizar = tarefaRepository.findById(tarefa.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tarefa não encontrada."));

        if (tarefa.getTitulo() != null) {
            tarefaAtualizar.setTitulo(tarefa.getTitulo());
        }

        if (tarefa.getDescricao() != null) {
            tarefaAtualizar.setDescricao(tarefa.getDescricao());
        }

        if (tarefa.getStatus() != null) {
            tarefaAtualizar.setStatus(tarefa.getStatus());
        }

        if (tarefa.getDataConclusao() != null) {
            tarefaAtualizar.setDataConclusao(tarefa.getDataConclusao());
        }

        return tarefaRepository.save(tarefaAtualizar);
    }

    public void excluirTarefa(Long id) {
        Tarefa tarefaExcluir = tarefaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tarefa não encontrada"));

        tarefaRepository.delete(tarefaExcluir);
    }
}
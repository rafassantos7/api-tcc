package com.example.levelUp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.levelUp.dto.request.TarefaDTO;
import com.example.levelUp.dto.response.TarefaResponse;
import com.example.levelUp.model.Tarefa;
import com.example.levelUp.model.Usuario;
import com.example.levelUp.model.enums.Prioridade;
import com.example.levelUp.model.enums.Status;
import com.example.levelUp.repository.TarefaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TarefaService {

  private final TarefaRepository tarefaRepository;
  private final UsuarioService usuarioService;

  public TarefaService(TarefaRepository tarefaRepository, UsuarioService usuarioService) {
    this.tarefaRepository = tarefaRepository;
    this.usuarioService = usuarioService;
  }

  public TarefaResponse salvar(TarefaDTO dto) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();

    Tarefa tarefa = dto.toEntity();
    tarefa.setUsuario(usuario);

    Tarefa salva = tarefaRepository.save(tarefa);
    return new TarefaResponse(salva);
  }

  public List<TarefaResponse> listarDoUsuario() {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return tarefaRepository.findByUsuarioOrderByIdDesc(usuario)
        .stream()
        .map(TarefaResponse::new)
        .collect(Collectors.toList());
  }

  public TarefaResponse buscarPorId(Long id) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    Tarefa tarefa = tarefaRepository.findByIdAndUsuario(id, usuario)
        .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    return new TarefaResponse(tarefa);
  }

  public TarefaResponse atualizar(Long id, TarefaDTO dto) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    Tarefa tarefa = tarefaRepository.findByIdAndUsuario(id, usuario)
        .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

    // Atualiza apenas os campos não nulos
    Optional.ofNullable(dto.titulo()).ifPresent(tarefa::setTitulo);
    Optional.ofNullable(dto.descricao()).ifPresent(tarefa::setDescricao);
    Optional.ofNullable(dto.dataInicio()).ifPresent(tarefa::setDataInicio);
    Optional.ofNullable(dto.dataConclusao()).ifPresent(tarefa::setDataConclusao);
    Optional.ofNullable(dto.status()).ifPresent(tarefa::setStatus);
    Optional.ofNullable(dto.prioridade()).ifPresent(tarefa::setPrioridade);

    Tarefa atualizada = tarefaRepository.save(tarefa);
    return new TarefaResponse(atualizada);
  }

  public void excluir(Long id) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    Tarefa tarefa = tarefaRepository.findByIdAndUsuario(id, usuario)
        .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    tarefaRepository.delete(tarefa);
  }

  public TarefaResponse concluir(Long id) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    Tarefa tarefa = tarefaRepository.findByIdAndUsuario(id, usuario)
        .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

    tarefa.setStatus(Status.CONCLUIDA);
    Tarefa concluida = tarefaRepository.save(tarefa);
    return new TarefaResponse(concluida);
  }

  public TarefaResponse reiniciar(Long id) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    Tarefa tarefa = tarefaRepository.findByIdAndUsuario(id, usuario)
        .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

    tarefa.setStatus(Status.PENDENTE);
    Tarefa reiniciada = tarefaRepository.save(tarefa);
    return new TarefaResponse(reiniciada);
  }

  public List<TarefaResponse> buscarComFiltros(String titulo, Status status, Prioridade prioridade, String ordem) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();

    List<Tarefa> tarefas;

    if (titulo != null && status != null && prioridade != null) {
      // Filtro completo: titulo + status + prioridade
      tarefas = "ASC".equalsIgnoreCase(ordem)
          ? tarefaRepository.findByUsuarioAndTituloContainingAndStatusAndPrioridadeOrderByIdAsc(usuario, titulo, status,
              prioridade)
          : tarefaRepository.findByUsuarioAndTituloContainingAndStatusAndPrioridadeOrderByIdDesc(usuario, titulo,
              status, prioridade);
    } else if (titulo != null && status != null) {
      // Filtro: titulo + status
      tarefas = "ASC".equalsIgnoreCase(ordem)
          ? tarefaRepository.findByUsuarioAndTituloContainingAndStatusOrderByIdAsc(usuario, titulo, status)
          : tarefaRepository.findByUsuarioAndTituloContainingAndStatusOrderByIdDesc(usuario, titulo, status);
    } else if (titulo != null && prioridade != null) {
      // Filtro: titulo + prioridade
      tarefas = "ASC".equalsIgnoreCase(ordem)
          ? tarefaRepository.findByUsuarioAndTituloContainingAndPrioridadeOrderByIdAsc(usuario, titulo, prioridade)
          : tarefaRepository.findByUsuarioAndTituloContainingAndPrioridadeOrderByIdDesc(usuario, titulo, prioridade);
    } else if (status != null && prioridade != null) {
      // Filtro: status + prioridade
      tarefas = "ASC".equalsIgnoreCase(ordem)
          ? tarefaRepository.findByUsuarioAndStatusAndPrioridadeOrderByIdAsc(usuario, status, prioridade)
          : tarefaRepository.findByUsuarioAndStatusAndPrioridadeOrderByIdDesc(usuario, status, prioridade);
    } else if (titulo != null) {
      // Filtro: apenas titulo
      tarefas = "ASC".equalsIgnoreCase(ordem)
          ? tarefaRepository.findByUsuarioAndTituloContainingOrderByIdAsc(usuario, titulo)
          : tarefaRepository.findByUsuarioAndTituloContainingOrderByIdDesc(usuario, titulo);
    } else if (status != null) {
      // Filtro: apenas status
      tarefas = "ASC".equalsIgnoreCase(ordem)
          ? tarefaRepository.findByUsuarioAndStatusOrderByIdAsc(usuario, status)
          : tarefaRepository.findByUsuarioAndStatusOrderByIdDesc(usuario, status);
    } else if (prioridade != null) {
      // Filtro: apenas prioridade
      tarefas = "ASC".equalsIgnoreCase(ordem)
          ? tarefaRepository.findByUsuarioAndPrioridadeOrderByIdAsc(usuario, prioridade)
          : tarefaRepository.findByUsuarioAndPrioridadeOrderByIdDesc(usuario, prioridade);
    } else {
      // Sem filtros, apenas ordenação
      tarefas = "ASC".equalsIgnoreCase(ordem)
          ? tarefaRepository.findByUsuarioOrderByIdAsc(usuario)
          : tarefaRepository.findByUsuarioOrderByIdDesc(usuario);
    }

    return tarefas.stream()
        .map(TarefaResponse::new)
        .collect(Collectors.toList());
  }

  public List<TarefaResponse> buscarConcluidas() {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return tarefaRepository.findByUsuarioAndStatusOrderByIdDesc(usuario, Status.CONCLUIDA)
        .stream()
        .map(TarefaResponse::new)
        .collect(Collectors.toList());
  }

  public List<TarefaResponse> buscarPendentes() {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return tarefaRepository.findByUsuarioAndStatusOrderByIdDesc(usuario, Status.PENDENTE)
        .stream()
        .map(TarefaResponse::new)
        .collect(Collectors.toList());
  }

  public List<TarefaResponse> buscarPorPrioridade(Prioridade prioridade) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return tarefaRepository.findByUsuarioAndPrioridadeOrderByIdDesc(usuario, prioridade)
        .stream()
        .map(TarefaResponse::new)
        .collect(Collectors.toList());
  }
  
  public Long contarTarefasUsuario() {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return tarefaRepository.countByUsuario(usuario);
  }

  public Long contarTarefasConcluidas() {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return tarefaRepository.countByUsuarioAndStatus(usuario, Status.CONCLUIDA);
  }
}
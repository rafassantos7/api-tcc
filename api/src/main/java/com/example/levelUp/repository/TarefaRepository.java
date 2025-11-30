package com.example.levelUp.repository;

import com.example.levelUp.model.Tarefa;
import com.example.levelUp.model.Usuario;
import com.example.levelUp.model.enums.Prioridade;
import com.example.levelUp.model.enums.Status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

  // Métodos básicos
  List<Tarefa> findByUsuario(Usuario usuario);

  List<Tarefa> findByUsuarioOrderByIdDesc(Usuario usuario);

  List<Tarefa> findByUsuarioOrderByIdAsc(Usuario usuario);

  Optional<Tarefa> findByIdAndUsuario(Long id, Usuario usuario);

  // Count methods
  Long countByUsuario(Usuario usuario);

  Long countByUsuarioAndStatus(Usuario usuario, Status status);

  // Filtros - MANTENDO "Usuario" em TODOS os nomes
  List<Tarefa> findByUsuarioAndStatus(Usuario usuario, Status status);

  List<Tarefa> findByUsuarioAndStatusOrderByIdDesc(Usuario usuario, Status status);

  List<Tarefa> findByUsuarioAndStatusOrderByIdAsc(Usuario usuario, Status status);

  List<Tarefa> findByUsuarioAndTituloContaining(Usuario usuario, String titulo);

  List<Tarefa> findByUsuarioAndTituloContainingOrderByIdDesc(Usuario usuario, String titulo);

  List<Tarefa> findByUsuarioAndTituloContainingOrderByIdAsc(Usuario usuario, String titulo);

  List<Tarefa> findByUsuarioAndPrioridade(Usuario usuario, Prioridade prioridade);

  List<Tarefa> findByUsuarioAndPrioridadeOrderByIdDesc(Usuario usuario, Prioridade prioridade);

  List<Tarefa> findByUsuarioAndPrioridadeOrderByIdAsc(Usuario usuario, Prioridade prioridade);

  List<Tarefa> findByUsuarioAndTituloContainingAndStatus(Usuario usuario, String titulo, Status status);

  List<Tarefa> findByUsuarioAndTituloContainingAndStatusOrderByIdDesc(Usuario usuario, String titulo, Status status);

  List<Tarefa> findByUsuarioAndTituloContainingAndStatusOrderByIdAsc(Usuario usuario, String titulo, Status status);

  List<Tarefa> findByUsuarioAndTituloContainingAndPrioridade(Usuario usuario, String titulo, Prioridade prioridade);

  List<Tarefa> findByUsuarioAndTituloContainingAndPrioridadeOrderByIdDesc(Usuario usuario, String titulo,
      Prioridade prioridade);

  List<Tarefa> findByUsuarioAndTituloContainingAndPrioridadeOrderByIdAsc(Usuario usuario, String titulo,
      Prioridade prioridade);

  List<Tarefa> findByUsuarioAndStatusAndPrioridade(Usuario usuario, Status status, Prioridade prioridade);

  List<Tarefa> findByUsuarioAndStatusAndPrioridadeOrderByIdDesc(Usuario usuario, Status status, Prioridade prioridade);

  List<Tarefa> findByUsuarioAndStatusAndPrioridadeOrderByIdAsc(Usuario usuario, Status status, Prioridade prioridade);

  List<Tarefa> findByUsuarioAndTituloContainingAndStatusAndPrioridade(Usuario usuario, String titulo, Status status,
      Prioridade prioridade);

  List<Tarefa> findByUsuarioAndTituloContainingAndStatusAndPrioridadeOrderByIdDesc(Usuario usuario, String titulo,
      Status status, Prioridade prioridade);

  List<Tarefa> findByUsuarioAndTituloContainingAndStatusAndPrioridadeOrderByIdAsc(Usuario usuario, String titulo,
      Status status, Prioridade prioridade);
}
package com.example.levelUp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.levelUp.model.Tarefa;
import com.example.levelUp.model.Usuario; // Import necessário
import com.example.levelUp.model.enums.Status; // Import necessário

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

  List<Tarefa> findByStatus(Status status);

  List<Tarefa> findByUsuario(Usuario usuario);
}
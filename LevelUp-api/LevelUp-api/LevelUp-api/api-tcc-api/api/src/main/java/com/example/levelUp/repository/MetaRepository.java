package com.example.levelUp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.levelUp.model.Meta;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {

    List<Meta> findByConcluida(Boolean concluida);

    List<Meta> findByUsuarioId(Long usuarioId);
}
package com.example.aula.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.aula.model.Metas;

@Repository
public interface MetasRepository extends JpaRepository<Metas, Long> {
    // Métodos adicionais de consulta podem ser definidos aqui, se necessário
    
}

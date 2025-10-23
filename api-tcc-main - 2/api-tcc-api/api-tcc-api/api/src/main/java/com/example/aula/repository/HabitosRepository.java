package com.example.aula.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.aula.model.Habitos;

@Repository
public interface HabitosRepository extends JpaRepository<Habitos, Long> {
    // Métodos adicionais de consulta podem ser definidos aqui, se necessário

}

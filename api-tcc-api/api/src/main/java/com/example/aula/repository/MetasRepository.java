package com.example.aula.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.aula.model.Metas;

@Repository
public interface MetasRepository extends JpaRepository<Metas, Long> {

    List<Metas> findByAtingida(boolean b);
    
}
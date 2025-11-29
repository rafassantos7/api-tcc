package com.example.levelUp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Opcional

import com.example.levelUp.model.Meta;
import com.example.levelUp.model.Usuario; // Import do Usuario
import com.example.levelUp.model.enums.Status; // Import do Enum

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {

    List<Meta> findByStatus(Status status);

    List<Meta> findByUsuario(Usuario usuario);
}
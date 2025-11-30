package com.example.levelUp.repository;

import com.example.levelUp.model.Meta;
import com.example.levelUp.model.Usuario;
import com.example.levelUp.model.enums.Status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {

    // Métodos básicos
    List<Meta> findByUsuario(Usuario usuario);

    List<Meta> findByUsuarioOrderByIdDesc(Usuario usuario);

    List<Meta> findByUsuarioOrderByIdAsc(Usuario usuario);

    Optional<Meta> findByIdAndUsuario(Long id, Usuario usuario);

    // Count methods (NOVOS)
    Long countByUsuario(Usuario usuario);

    Long countByUsuarioAndStatus(Usuario usuario, Status status);

    // Métodos para Status
    List<Meta> findByUsuarioAndStatus(Usuario usuario, Status status);

    List<Meta> findByUsuarioAndStatusOrderByIdDesc(Usuario usuario, Status status);

    List<Meta> findByUsuarioAndStatusOrderByIdAsc(Usuario usuario, Status status);

    // Métodos para Título
    List<Meta> findByUsuarioAndTituloContainingIgnoreCase(Usuario usuario, String titulo);

    List<Meta> findByUsuarioAndTituloContainingIgnoreCaseOrderByIdDesc(Usuario usuario, String titulo);

    List<Meta> findByUsuarioAndTituloContainingIgnoreCaseOrderByIdAsc(Usuario usuario, String titulo);

    // Filtros combinados - Título + Status
    List<Meta> findByUsuarioAndTituloContainingIgnoreCaseAndStatus(Usuario usuario, String titulo, Status status);

    List<Meta> findByUsuarioAndTituloContainingIgnoreCaseAndStatusOrderByIdDesc(Usuario usuario, String titulo,
            Status status);

    List<Meta> findByUsuarioAndTituloContainingIgnoreCaseAndStatusOrderByIdAsc(Usuario usuario, String titulo,
            Status status);
}
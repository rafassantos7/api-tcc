package com.example.levelUp.repository;

import com.example.levelUp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Para Spring Security
    UserDetails findByEmail(String email);

    // Para suas operações de negócio
    boolean existsByEmail(String email);
}
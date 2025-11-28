package com.example.levelUp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.levelUp.model.Habito;
import com.example.levelUp.model.Usuario;
public interface HabitoRepository extends JpaRepository<Habito, Long> {

  List<Habito> findByUsuario(Usuario usuario);
}
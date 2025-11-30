package com.example.levelUp.repository;

import com.example.levelUp.model.Habito;
import com.example.levelUp.model.Usuario;
import com.example.levelUp.model.enums.FrequenciaHabito;
import com.example.levelUp.model.enums.Status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitoRepository extends JpaRepository<Habito, Long> {

  // Métodos básicos
  List<Habito> findByUsuario(Usuario usuario);

  List<Habito> findByUsuarioOrderByIdDesc(Usuario usuario);

  List<Habito> findByUsuarioOrderByIdAsc(Usuario usuario);

  Optional<Habito> findByIdAndUsuario(Long id, Usuario usuario);

  // Count methods
  Long countByUsuario(Usuario usuario);

  Long countByUsuarioAndStatus(Usuario usuario, Status status);

  // Filtros - Status
  List<Habito> findByUsuarioAndStatus(Usuario usuario, Status status);

  List<Habito> findByUsuarioAndStatusOrderByIdDesc(Usuario usuario, Status status);

  List<Habito> findByUsuarioAndStatusOrderByIdAsc(Usuario usuario, Status status);

  // Filtros - Frequencia
  List<Habito> findByUsuarioAndFrequencia(Usuario usuario, FrequenciaHabito frequencia);

  List<Habito> findByUsuarioAndFrequenciaOrderByIdDesc(Usuario usuario, FrequenciaHabito frequencia);

  List<Habito> findByUsuarioAndFrequenciaOrderByIdAsc(Usuario usuario, FrequenciaHabito frequencia);

  // Filtros - Título
  List<Habito> findByUsuarioAndTituloContaining(Usuario usuario, String titulo);

  List<Habito> findByUsuarioAndTituloContainingOrderByIdDesc(Usuario usuario, String titulo);

  List<Habito> findByUsuarioAndTituloContainingOrderByIdAsc(Usuario usuario, String titulo);

  // Filtros combinados - Título + Status
  List<Habito> findByUsuarioAndTituloContainingAndStatus(Usuario usuario, String titulo, Status status);

  List<Habito> findByUsuarioAndTituloContainingAndStatusOrderByIdDesc(Usuario usuario, String titulo, Status status);

  List<Habito> findByUsuarioAndTituloContainingAndStatusOrderByIdAsc(Usuario usuario, String titulo, Status status);

  // Filtros combinados - Título + Frequencia
  List<Habito> findByUsuarioAndTituloContainingAndFrequencia(Usuario usuario, String titulo,
      FrequenciaHabito frequencia);

  List<Habito> findByUsuarioAndTituloContainingAndFrequenciaOrderByIdDesc(Usuario usuario, String titulo,
      FrequenciaHabito frequencia);

  List<Habito> findByUsuarioAndTituloContainingAndFrequenciaOrderByIdAsc(Usuario usuario, String titulo,
      FrequenciaHabito frequencia);

  // Filtros combinados - Status + Frequencia
  List<Habito> findByUsuarioAndStatusAndFrequencia(Usuario usuario, Status status, FrequenciaHabito frequencia);

  List<Habito> findByUsuarioAndStatusAndFrequenciaOrderByIdDesc(Usuario usuario, Status status,
      FrequenciaHabito frequencia);

  List<Habito> findByUsuarioAndStatusAndFrequenciaOrderByIdAsc(Usuario usuario, Status status,
      FrequenciaHabito frequencia);

  // Filtro completo - Título + Status + Frequencia
  List<Habito> findByUsuarioAndTituloContainingAndStatusAndFrequencia(Usuario usuario, String titulo, Status status,
      FrequenciaHabito frequencia);

  List<Habito> findByUsuarioAndTituloContainingAndStatusAndFrequenciaOrderByIdDesc(Usuario usuario, String titulo,
      Status status, FrequenciaHabito frequencia);

  List<Habito> findByUsuarioAndTituloContainingAndStatusAndFrequenciaOrderByIdAsc(Usuario usuario, String titulo,
      Status status, FrequenciaHabito frequencia);
}
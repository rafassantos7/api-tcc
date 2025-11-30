package com.example.levelUp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.levelUp.dto.request.HabitoDTO;
import com.example.levelUp.dto.response.HabitoResponse;
import com.example.levelUp.model.Habito;
import com.example.levelUp.model.Usuario;
import com.example.levelUp.model.enums.FrequenciaHabito;
import com.example.levelUp.model.enums.Status;
import com.example.levelUp.repository.HabitoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class HabitoService {

  private final HabitoRepository habitoRepository;
  private final UsuarioService usuarioService;

  public HabitoService(HabitoRepository habitoRepository, UsuarioService usuarioService) {
    this.habitoRepository = habitoRepository;
    this.usuarioService = usuarioService;
  }

  public HabitoResponse salvar(HabitoDTO dto) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();

    Habito habito = dto.toEntity();
    habito.setUsuario(usuario);

    Habito salvo = habitoRepository.save(habito);
    return new HabitoResponse(salvo);
  }

  public List<HabitoResponse> listarDoUsuario() {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return habitoRepository.findByUsuarioOrderByIdDesc(usuario)
        .stream()
        .map(HabitoResponse::new)
        .collect(Collectors.toList());
  }

  public HabitoResponse buscarPorId(Long id) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    Habito habito = habitoRepository.findByIdAndUsuario(id, usuario)
        .orElseThrow(() -> new RuntimeException("Hábito não encontrado"));
    return new HabitoResponse(habito);
  }

  public HabitoResponse atualizar(Long id, HabitoDTO dto) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    Habito habito = habitoRepository.findByIdAndUsuario(id, usuario)
        .orElseThrow(() -> new RuntimeException("Hábito não encontrado"));

    // Atualiza apenas os campos não nulos
    Optional.ofNullable(dto.titulo()).ifPresent(habito::setTitulo);
    Optional.ofNullable(dto.descricao()).ifPresent(habito::setDescricao);
    Optional.ofNullable(dto.dataInicio()).ifPresent(habito::setDataInicio);
    Optional.ofNullable(dto.dataConclusao()).ifPresent(habito::setDataConclusao);
    Optional.ofNullable(dto.status()).ifPresent(habito::setStatus);
    Optional.ofNullable(dto.frequencia()).ifPresent(habito::setFrequencia);

    Habito atualizado = habitoRepository.save(habito);
    return new HabitoResponse(atualizado);
  }

  public void excluir(Long id) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    Habito habito = habitoRepository.findByIdAndUsuario(id, usuario)
        .orElseThrow(() -> new RuntimeException("Hábito não encontrado"));
    habitoRepository.delete(habito);
  }

  public HabitoResponse concluir(Long id) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    Habito habito = habitoRepository.findByIdAndUsuario(id, usuario)
        .orElseThrow(() -> new RuntimeException("Hábito não encontrado"));

    habito.setStatus(Status.CONCLUIDA);
    Habito concluido = habitoRepository.save(habito);
    return new HabitoResponse(concluido);
  }

  public HabitoResponse reiniciar(Long id) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    Habito habito = habitoRepository.findByIdAndUsuario(id, usuario)
        .orElseThrow(() -> new RuntimeException("Hábito não encontrado"));

    habito.setStatus(Status.PENDENTE);
    Habito reiniciado = habitoRepository.save(habito);
    return new HabitoResponse(reiniciado);
  }

  public List<HabitoResponse> buscarComFiltros(String titulo, Status status, FrequenciaHabito frequencia,
      String ordem) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();

    List<Habito> habitos;

    if (titulo != null && status != null && frequencia != null) {
      // Filtro completo: titulo + status + frequencia
      habitos = "ASC".equalsIgnoreCase(ordem)
          ? habitoRepository.findByUsuarioAndTituloContainingAndStatusAndFrequenciaOrderByIdAsc(usuario, titulo, status,
              frequencia)
          : habitoRepository.findByUsuarioAndTituloContainingAndStatusAndFrequenciaOrderByIdDesc(usuario, titulo,
              status, frequencia);
    } else if (titulo != null && status != null) {
      // Filtro: titulo + status
      habitos = "ASC".equalsIgnoreCase(ordem)
          ? habitoRepository.findByUsuarioAndTituloContainingAndStatusOrderByIdAsc(usuario, titulo, status)
          : habitoRepository.findByUsuarioAndTituloContainingAndStatusOrderByIdDesc(usuario, titulo, status);
    } else if (titulo != null && frequencia != null) {
      // Filtro: titulo + frequencia
      habitos = "ASC".equalsIgnoreCase(ordem)
          ? habitoRepository.findByUsuarioAndTituloContainingAndFrequenciaOrderByIdAsc(usuario, titulo, frequencia)
          : habitoRepository.findByUsuarioAndTituloContainingAndFrequenciaOrderByIdDesc(usuario, titulo, frequencia);
    } else if (status != null && frequencia != null) {
      // Filtro: status + frequencia
      habitos = "ASC".equalsIgnoreCase(ordem)
          ? habitoRepository.findByUsuarioAndStatusAndFrequenciaOrderByIdAsc(usuario, status, frequencia)
          : habitoRepository.findByUsuarioAndStatusAndFrequenciaOrderByIdDesc(usuario, status, frequencia);
    } else if (titulo != null) {
      // Filtro: apenas titulo
      habitos = "ASC".equalsIgnoreCase(ordem)
          ? habitoRepository.findByUsuarioAndTituloContainingOrderByIdAsc(usuario, titulo)
          : habitoRepository.findByUsuarioAndTituloContainingOrderByIdDesc(usuario, titulo);
    } else if (status != null) {
      // Filtro: apenas status
      habitos = "ASC".equalsIgnoreCase(ordem)
          ? habitoRepository.findByUsuarioAndStatusOrderByIdAsc(usuario, status)
          : habitoRepository.findByUsuarioAndStatusOrderByIdDesc(usuario, status);
    } else if (frequencia != null) {
      // Filtro: apenas frequencia
      habitos = "ASC".equalsIgnoreCase(ordem)
          ? habitoRepository.findByUsuarioAndFrequenciaOrderByIdAsc(usuario, frequencia)
          : habitoRepository.findByUsuarioAndFrequenciaOrderByIdDesc(usuario, frequencia);
    } else {
      // Sem filtros, apenas ordenação
      habitos = "ASC".equalsIgnoreCase(ordem)
          ? habitoRepository.findByUsuarioOrderByIdAsc(usuario)
          : habitoRepository.findByUsuarioOrderByIdDesc(usuario);
    }

    return habitos.stream()
        .map(HabitoResponse::new)
        .collect(Collectors.toList());
  }

  public List<HabitoResponse> buscarConcluidos() {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return habitoRepository.findByUsuarioAndStatusOrderByIdDesc(usuario, Status.CONCLUIDA)
        .stream()
        .map(HabitoResponse::new)
        .collect(Collectors.toList());
  }

  public List<HabitoResponse> buscarPendentes() {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return habitoRepository.findByUsuarioAndStatusOrderByIdDesc(usuario, Status.PENDENTE)
        .stream()
        .map(HabitoResponse::new)
        .collect(Collectors.toList());
  }

  public List<HabitoResponse> buscarPorFrequencia(FrequenciaHabito frequencia) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return habitoRepository.findByUsuarioAndFrequenciaOrderByIdDesc(usuario, frequencia)
        .stream()
        .map(HabitoResponse::new)
        .collect(Collectors.toList());
  }

  public Long contarHabitosUsuario() {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return habitoRepository.countByUsuario(usuario);
  }

  public Long contarHabitosConcluidos() {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return habitoRepository.countByUsuarioAndStatus(usuario, Status.CONCLUIDA);
  }
}
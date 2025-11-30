package com.example.levelUp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.levelUp.dto.request.MetaDTO;
import com.example.levelUp.dto.response.MetaResponse;
import com.example.levelUp.model.Meta;
import com.example.levelUp.model.Usuario;
import com.example.levelUp.model.enums.Status;
import com.example.levelUp.repository.MetaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MetaService {

  private final MetaRepository metaRepository;
  private final UsuarioService usuarioService;

  public MetaService(MetaRepository metaRepository, UsuarioService usuarioService) {
    this.metaRepository = metaRepository;
    this.usuarioService = usuarioService;
  }

  public MetaResponse salvar(MetaDTO dto) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();

    Meta meta = new Meta(
        dto.titulo(),
        dto.descricao(),
        dto.dataInicio(),
        dto.dataConclusao(),
        dto.status() != null ? dto.status() : Status.PENDENTE);

    meta.setUsuario(usuario);
    Meta salva = metaRepository.save(meta);
    return new MetaResponse(salva);
  }

  public List<MetaResponse> listarDoUsuario() {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return metaRepository.findByUsuarioOrderByIdDesc(usuario)
        .stream()
        .map(MetaResponse::new)
        .collect(Collectors.toList());
  }

  public MetaResponse buscarPorId(Long id) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    Meta meta = metaRepository.findByIdAndUsuario(id, usuario)
        .orElseThrow(() -> new RuntimeException("Meta não encontrada"));
    return new MetaResponse(meta);
  }

  public MetaResponse atualizar(Long id, MetaDTO dto) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    Meta meta = metaRepository.findByIdAndUsuario(id, usuario)
        .orElseThrow(() -> new RuntimeException("Meta não encontrada"));

    meta.setTitulo(dto.titulo());
    meta.setDescricao(dto.descricao());
    meta.setDataInicio(dto.dataInicio());
    meta.setDataConclusao(dto.dataConclusao());
    if (dto.status() != null) {
      meta.setStatus(dto.status());
    }

    Meta atualizada = metaRepository.save(meta);
    return new MetaResponse(atualizada);
  }

  public void excluir(Long id) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    Meta meta = metaRepository.findByIdAndUsuario(id, usuario)
        .orElseThrow(() -> new RuntimeException("Meta não encontrada"));
    metaRepository.delete(meta);
  }

  public MetaResponse concluir(Long id) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    Meta meta = metaRepository.findByIdAndUsuario(id, usuario)
        .orElseThrow(() -> new RuntimeException("Meta não encontrada"));

    meta.setStatus(Status.CONCLUIDA);
    Meta concluida = metaRepository.save(meta);
    return new MetaResponse(concluida);
  }

  // NOVO MÉTODO: Reiniciar meta
  public MetaResponse reiniciar(Long id) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    Meta meta = metaRepository.findByIdAndUsuario(id, usuario)
        .orElseThrow(() -> new RuntimeException("Meta não encontrada"));

    meta.setStatus(Status.PENDENTE);
    Meta reiniciada = metaRepository.save(meta);
    return new MetaResponse(reiniciada);
  }

  public List<MetaResponse> buscarComFiltros(String titulo, Status status, String ordem) {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();

    List<Meta> metas;
    if (titulo != null && status != null) {
      metas = "ASC".equalsIgnoreCase(ordem)
          ? metaRepository.findByUsuarioAndTituloContainingIgnoreCaseAndStatusOrderByIdAsc(usuario, titulo, status)
          : metaRepository.findByUsuarioAndTituloContainingIgnoreCaseAndStatusOrderByIdDesc(usuario, titulo, status);
    } else if (titulo != null) {
      metas = "ASC".equalsIgnoreCase(ordem)
          ? metaRepository.findByUsuarioAndTituloContainingIgnoreCaseOrderByIdAsc(usuario, titulo)
          : metaRepository.findByUsuarioAndTituloContainingIgnoreCaseOrderByIdDesc(usuario, titulo);
    } else if (status != null) {
      metas = "ASC".equalsIgnoreCase(ordem)
          ? metaRepository.findByUsuarioAndStatusOrderByIdAsc(usuario, status)
          : metaRepository.findByUsuarioAndStatusOrderByIdDesc(usuario, status);
    } else {
      metas = "ASC".equalsIgnoreCase(ordem)
          ? metaRepository.findByUsuarioOrderByIdAsc(usuario)
          : metaRepository.findByUsuarioOrderByIdDesc(usuario);
    }

    return metas.stream()
        .map(MetaResponse::new)
        .collect(Collectors.toList());
  }

  // NOVO MÉTODO: Buscar metas concluídas
  public List<MetaResponse> buscarConcluidas() {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return metaRepository.findByUsuarioAndStatusOrderByIdDesc(usuario, Status.CONCLUIDA)
        .stream()
        .map(MetaResponse::new)
        .collect(Collectors.toList());
  }

  // NOVO MÉTODO: Buscar metas pendentes
  public List<MetaResponse> buscarPendentes() {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return metaRepository.findByUsuarioAndStatusOrderByIdDesc(usuario, Status.PENDENTE)
        .stream()
        .map(MetaResponse::new)
        .collect(Collectors.toList());
  }

  // NOVO MÉTODO: Contar total de metas do usuário
  public Long contarMetasUsuario() {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return metaRepository.countByUsuario(usuario);
  }

  // NOVO MÉTODO: Contar metas concluídas
  public Long contarMetasConcluidas() {
    Usuario usuario = usuarioService.obterUsuarioAutenticado();
    return metaRepository.countByUsuarioAndStatus(usuario, Status.CONCLUIDA);
  }
}
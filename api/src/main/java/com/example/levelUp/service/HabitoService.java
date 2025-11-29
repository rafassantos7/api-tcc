package com.example.levelUp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.levelUp.dto.HabitoDTO;
import com.example.levelUp.dto.HabitoResponse;
import com.example.levelUp.exception.RecursoNaoEncontradoException;
import com.example.levelUp.model.Habito;
import com.example.levelUp.model.Usuario;
import com.example.levelUp.repository.HabitoRepository;

import jakarta.validation.Valid;

@Service
@Validated
public class HabitoService {

    private final HabitoRepository habitoRepository;

    public HabitoService(HabitoRepository habitoRepository) {
        this.habitoRepository = habitoRepository;
    }

    private Usuario getUsuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<HabitoResponse> listarHabitos() {
        return habitoRepository.findByUsuario(getUsuarioLogado())
                .stream()
                .map(HabitoResponse::new)
                .collect(Collectors.toList());
    }

    public HabitoResponse salvarHabito(@Valid HabitoDTO dto) {
        Habito habito = dto.toEntity();
        habito.setUsuario(getUsuarioLogado());

        Habito salvo = habitoRepository.save(habito);
        return new HabitoResponse(salvo);
    }

    public HabitoResponse atualizarHabito(Long id, @Valid HabitoDTO dto) {
        Usuario usuarioLogado = getUsuarioLogado();

        Habito habitoAtualizar = habitoRepository.findById(id)
                .filter(h -> h.getUsuario().getId().equals(usuarioLogado.getId()))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Hábito não encontrado."));

        Optional.ofNullable(dto.titulo()).ifPresent(habitoAtualizar::setTitulo);
        Optional.ofNullable(dto.descricao()).ifPresent(habitoAtualizar::setDescricao);
        Optional.ofNullable(dto.dataInicio()).ifPresent(habitoAtualizar::setDataInicio);
        Optional.ofNullable(dto.dataConclusao()).ifPresent(habitoAtualizar::setDataConclusao);
        Optional.ofNullable(dto.frequencia()).ifPresent(habitoAtualizar::setFrequencia);
        Optional.ofNullable(dto.status()).ifPresent(habitoAtualizar::setStatus);

        return new HabitoResponse(habitoRepository.save(habitoAtualizar));
    }

    public void deletarHabito(Long id) {
        Usuario usuarioLogado = getUsuarioLogado();
        Habito habitoDeletar = habitoRepository.findById(id)
                .filter(h -> h.getUsuario().getId().equals(usuarioLogado.getId()))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Hábito não encontrado."));
        habitoRepository.delete(habitoDeletar);
    }
}
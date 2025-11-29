package com.example.levelUp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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

    public List<Habito> listarHabitos() {
        return habitoRepository.findByUsuario(getUsuarioLogado());
    }

    public Habito salvarHabito(@Valid Habito habito) {
        habito.setUsuario(getUsuarioLogado());
        return habitoRepository.save(habito);
    }

    public Habito atualizarHabito(@Valid Habito habito) {
        Usuario usuarioLogado = getUsuarioLogado();

         Habito habitoAtualizar = habitoRepository.findById(habito.getId())
                .filter(h -> h.getUsuario().getId().equals(usuarioLogado.getId())) // Segurança extra
                .orElseThrow(() -> new RecursoNaoEncontradoException("Hábito não encontrado ou não pertence a você."));

      
        Optional.ofNullable(habito.getTitulo()).ifPresent(habitoAtualizar::setTitulo);
        Optional.ofNullable(habito.getDescricao()).ifPresent(habitoAtualizar::setDescricao);
        Optional.ofNullable(habito.getDataInicio()).ifPresent(habitoAtualizar::setDataInicio);
        Optional.ofNullable(habito.getDataConclusao()).ifPresent(habitoAtualizar::setDataConclusao);
        Optional.ofNullable(habito.getFrequencia()).ifPresent(habitoAtualizar::setFrequencia);
        Optional.ofNullable(habito.getStatus()).ifPresent(habitoAtualizar::setStatus);

        return habitoRepository.save(habitoAtualizar);
    }

    public void deletarHabito(Long id) {
        Usuario usuarioLogado = getUsuarioLogado();

        Habito habitoDeletar = habitoRepository.findById(id)
                .filter(h -> h.getUsuario().getId().equals(usuarioLogado.getId())) // Segurança extra
                .orElseThrow(() -> new RecursoNaoEncontradoException("Hábito não encontrado."));

        habitoRepository.delete(habitoDeletar);
    }
}
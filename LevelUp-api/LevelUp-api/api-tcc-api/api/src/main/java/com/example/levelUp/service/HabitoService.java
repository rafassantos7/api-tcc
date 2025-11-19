package com.example.levelUp.service;

import com.example.levelUp.model.Habito;
import com.example.levelUp.repository.HabitoRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import java.util.List;

@Service
@Validated
public class HabitoService {
    private final HabitoRepository habitoRepository;

    public HabitoService(HabitoRepository habitoRepository) {
        this.habitoRepository = habitoRepository;
    }

    public List<Habito> listarHabitos(){
        return habitoRepository.findAll();
    }

    public Habito salvarHabito(@Valid Habito habito){
        if (habito.getTitulo() != null && habitoRepository.findById(habito.getId()).isPresent()) {
            throw new IllegalArgumentException("Hábitos com títulos duplicados não são permitidos.");
        } else {
            return habitoRepository.save(habito);
        }
    }

    public Habito atualizarHabito(@Valid Habito habito){
        Habito habitoAtualizar = habitoRepository.findById(habito.getId())
                .orElseThrow(() -> new IllegalArgumentException("Hábito não encontrado para atualização."));

        habitoAtualizar.setTitulo(habito.getTitulo());
        habitoAtualizar.setDescricao(habito.getDescricao());
        habitoAtualizar.setDataInicio(habito.getDataInicio());
        habitoAtualizar.setDataConclusao(habito.getDataConclusao());
        habitoAtualizar.setStatus(habito.getStatus());

        return habitoRepository.save(habitoAtualizar);
    }

    public void deletarHabito(Long id){
        Habito habitoDeletar = habitoRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Hábito não encontrado para exclusão."));
        
            habitoRepository.delete(habitoDeletar);

    }

}

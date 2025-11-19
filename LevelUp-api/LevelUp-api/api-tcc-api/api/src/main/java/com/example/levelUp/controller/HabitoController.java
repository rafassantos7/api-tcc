package com.example.levelUp.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.levelUp.model.Habito;
import com.example.levelUp.service.HabitoService;

import jakarta.validation.Valid;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/habitos")
public class HabitoController {
    private HabitoService habitoService;



    @GetMapping
    public List<Habito> listarTodos(){
        return habitoService.listarHabitos();

}   

    @PostMapping("/criarHabito")
    public Habito criarHabito(@Valid @RequestBody Habito habito){
        return habitoService.salvarHabito(habito);
    }

    @PutMapping("/atualizarHabito")
    public Habito atualizarHabito(@Valid @RequestBody Habito habito){
        return habitoService.atualizarHabito(habito);
    }

    @DeleteMapping("deletarHabito/{id}")
    public void deletarHabito(@PathVariable Long id){
        habitoService.deletarHabito(id);
    }
}

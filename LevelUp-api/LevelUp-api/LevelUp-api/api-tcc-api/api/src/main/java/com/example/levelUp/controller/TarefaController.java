package com.example.levelUp.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.http.ResponseEntity;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.example.levelUp.model.Tarefa;
import com.example.levelUp.service.TarefaService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("tarefa")
public class TarefaController {
    private TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping("/listarTarefas")
    public List<Tarefa> listarTodos(){
        return tarefaService.listarTarefas();
    }

    @GetMapping("/listarConcluidas")
    public List<Tarefa> listarConcluidas(){
        return tarefaService.listarConcluidas();
    }

    @PostMapping("/criarTarefa")
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa){
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefa);
    }

    @PutMapping("/atualizarTarefa")
    public ResponseEntity<Tarefa> atualizarTarefa(@RequestBody Tarefa tarefa){
        return ResponseEntity.status(HttpStatus.OK).body(tarefa);
    }

    @DeleteMapping("deletarTarefa/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id){
        tarefaService.excluirTarefa(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
}

}
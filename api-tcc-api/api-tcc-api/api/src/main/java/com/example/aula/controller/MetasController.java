package com.example.aula.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aula.model.Metas;
import com.example.aula.service.MetasService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/metas")
public class MetasController {
    private MetasService metasService;
    public MetasController(MetasService metasService) {
        this.metasService = metasService;
    }

    @GetMapping
    public List<com.example.aula.model.Metas> listarTodos() {
        return metasService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Metas> criarMeta(@RequestBody Metas metas) {
        try {
            Metas novaMeta = metasService.salvar(metas);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaMeta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}

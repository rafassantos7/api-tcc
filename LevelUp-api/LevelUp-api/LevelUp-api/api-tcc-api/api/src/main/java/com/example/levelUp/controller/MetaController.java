package com.example.levelUp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.levelUp.model.Meta;
import com.example.levelUp.service.MetaService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/metas")
public class MetaController {
    private MetaService metasService;

    public MetaController(MetaService metasService) {
        this.metasService = metasService;
    }

    @GetMapping
    public List<Meta> listarTodos() {
        return metasService.listarTodos();
    }

    @GetMapping("/concluidas")
    public List<Meta> listarConcluidas() {
        return metasService.listarConcluidas();
    }

    @PostMapping
    public ResponseEntity<Meta> criarMeta(@RequestBody Meta metas) {
        try {
            Meta novaMeta = metasService.salvar(metas);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaMeta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Meta> atualizarMeta(@PathVariable Long id, @RequestBody @Valid Meta meta) {
        // Garante que o ID do corpo é o mesmo da URL
        meta.setId(id);

        // Chama o serviço direto. Se der erro, o ResourceExceptionHandler pega.
        Meta metaAtualizada = metasService.atualizar(meta);

        return ResponseEntity.ok(metaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> excluirMeta(@PathVariable Long id) {
        metasService.excluir(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("mensagem", "Meta excluída com sucesso"));
    }
}

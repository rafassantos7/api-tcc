package com.example.levelUp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.levelUp.dto.UsuarioDTO;
import com.example.levelUp.dto.UsuarioResponse;
import com.example.levelUp.dto.MetaResponse;
import com.example.levelUp.dto.TarefaResponse;
import com.example.levelUp.dto.HabitoResponse;
import com.example.levelUp.service.HabitoService;
import com.example.levelUp.service.MetaService;
import com.example.levelUp.service.TarefaService;
import com.example.levelUp.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final MetaService metaService;
    private final HabitoService habitoService;
    private final TarefaService tarefaService;

    public UsuarioController(UsuarioService usuarioService, MetaService metaService,
            HabitoService habitoService, TarefaService tarefaService) {
        this.usuarioService = usuarioService;
        this.metaService = metaService;
        this.habitoService = habitoService;
        this.tarefaService = tarefaService;
    }

    // MEU PERFIL
    @GetMapping
    public ResponseEntity<UsuarioResponse> meuPerfil() {
        return ResponseEntity.ok(usuarioService.buscarMeuPerfil());
    }

    // ATUALIZAR PERFIL
    @PutMapping
    public ResponseEntity<UsuarioResponse> atualizar(@RequestBody @Valid UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizarMeuPerfil(dto));
    }

    // DELETAR CONTA
    @DeleteMapping
    public ResponseEntity<Void> excluir() {
        usuarioService.excluirMeuPerfil();
        return ResponseEntity.noContent().build();
    }

    // --- RECURSOS ANINHADOS (Agora retornando Responses) ---

    @GetMapping("/metas")
    public ResponseEntity<List<MetaResponse>> minhasMetas() {
        return ResponseEntity.ok(metaService.listarTodos());
    }

    @GetMapping("/habitos")
    public ResponseEntity<List<HabitoResponse>> meusHabitos() {
        return ResponseEntity.ok(habitoService.listarHabitos());
    }

    @GetMapping("/tarefas")
    public ResponseEntity<List<TarefaResponse>> minhasTarefas() {
        return ResponseEntity.ok(tarefaService.listarTarefas());
    }
}
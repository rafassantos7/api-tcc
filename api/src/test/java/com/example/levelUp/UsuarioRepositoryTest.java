package com.example.levelUp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.levelUp.model.Usuario;
import com.example.levelUp.repository.UsuarioRepository;

import java.time.LocalDate;
import java.util.Optional; // Importe o Optional

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuarioTeste;

    // O @BeforeEach cria um usuário limpo para cada teste
    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
        usuarioTeste = new Usuario();
        usuarioTeste.setNome("Usuario Login");
        usuarioTeste.setEmail("login@teste.com");
        usuarioTeste.setSenha("senha123"); // Senha em texto puro (veja nota abaixo)
        usuarioTeste.setTelefone("123123");
        usuarioTeste.setDataNascimento(LocalDate.of(1990, 1, 1));

        usuarioRepository.save(usuarioTeste);
    }

    @Test
    void encontrarUsuarioPorEmailEValidarSenha() {
        Optional<Usuario> usuarioOpcional = Optional.ofNullable(usuarioRepository.findByEmail("login@teste.com"));
        assertTrue(usuarioOpcional.isPresent());
        Usuario usuarioEncontrado = usuarioOpcional.get();
        assertEquals("login@teste.com", usuarioEncontrado.getEmail());
        assertEquals("senha123", usuarioEncontrado.getSenha());
    }

    @Test
    void naoEncontrarUsuarioPorEmailInexistente() {
        Optional<Usuario> usuarioOpcional = Optional.ofNullable(usuarioRepository.findByEmail("errado@teste.com"));
        assertFalse(usuarioOpcional.isPresent());
    }
}
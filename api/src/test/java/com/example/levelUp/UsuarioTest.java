package com.example.levelUp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.levelUp.model.Meta;
import com.example.levelUp.model.Usuario;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // ... (seu setup de usuário) ...
        usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setNome("Usuario Teste");
        usuario.setSenha("A1234567.!a");
        usuario.setDataNascimento(LocalDate.of(2000, 1, 1));
        usuario.setTelefone("999999756");
    }

    @Test
    void deveAdicionarMetasNaLista() {
        // 1. Crie o objeto Meta
        Meta meta = new Meta();

        // 2. Sete os campos (incluindo a data)
        meta.setDescricao("Economizar 100 reais");
        meta.setDataConclusao(LocalDate.of(2025, 12, 31)); // <-- Data adicionada

        // 3. Adicione na lista getMetas()
        usuario.getMetas().add(meta);

        // 4. Verifique
        assertEquals(1, usuario.getMetas().size());

        Meta metaAdicionada = usuario.getMetas().get(0);

        // 5. Teste os dois campos
        assertEquals("Economizar 100 reais", metaAdicionada.getDescricao());
        assertEquals(LocalDate.of(2025, 12, 31), metaAdicionada.getDataConclusao()); // <-- Verificação da data
    }

}
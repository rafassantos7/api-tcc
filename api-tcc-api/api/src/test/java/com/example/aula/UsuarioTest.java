package com.example.aula;

import com.example.aula.model.Metas;
import com.example.aula.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // ... (seu setup de usuário) ...
        usuario = new Usuario();
        usuario.setNome("Usuario Teste");
        usuario.setEmail("teste@email.com");
        usuario.setSenha("12345");
        usuario.setTelefone("999999");
        usuario.setDataNascimento(LocalDate.of(2000, 1, 1));
    }

   
@Test
    void deveAdicionarMetasNaLista() {
        // 1. Crie o objeto Meta
        Metas meta = new Metas();

        // 2. Sete os campos (incluindo a data)
        meta.setDescricao("Economizar 100 reais");
        meta.setDataConclusao(LocalDate.of(2025, 12, 31)); // <-- Data adicionada

        // 3. Adicione na lista getMetas()
        usuario.getMetas().add(meta);

        // 4. Verifique
        assertEquals(1, usuario.getMetas().size());
        
        Metas metaAdicionada = usuario.getMetas().get(0);
        
        // 5. Teste os dois campos
        assertEquals("Economizar 100 reais", metaAdicionada.getDescricao());
        assertEquals(LocalDate.of(2025, 12, 31), metaAdicionada.getDataConclusao()); // <-- Verificação da data
    }


}
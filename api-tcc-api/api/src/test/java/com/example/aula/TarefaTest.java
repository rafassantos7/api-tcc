package com.example.aula;

import com.example.aula.model.Prioridade;
import com.example.aula.model.Status;
import com.example.aula.model.Tarefa;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TarefaTest {

    @Test
    void criarTarefa() {
        Tarefa tarefa = new Tarefa(
                "Estudar Java",
                "Estudar For e While",
                LocalDate.of(2025, 3, 10),
                LocalDate.of(2025, 3, 12),
                Status.EM_ANDAMENTO,
                1,
                Prioridade.ALTA
        );

        assertEquals(Prioridade.ALTA, tarefa.getPrioridade());
    }

    @Test
    void naoDeveAceitarPrioridadeNula() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Tarefa(
                        "Estudar Java",
                        "Descrição",
                        LocalDate.now(),
                        LocalDate.now().plusDays(1),
                        Status.PENDENTE,
                        1,
                        null
                )
        );

        assertEquals("Prioridade não pode ser vazio", exception.getMessage());
    }
}

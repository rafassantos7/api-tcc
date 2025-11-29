package com.example.levelUp;

import org.junit.jupiter.api.Test;

import com.example.levelUp.model.Tarefa;
import com.example.levelUp.model.enums.Prioridade;
import com.example.levelUp.model.enums.Status;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TarefaTest {

    @Test
    void criarTarefa() {
        Tarefa tarefa = new Tarefa(
                "Estudar Java",
                "Estudar For e While",
                LocalDate.of(2025, 12, 10),
                LocalDate.now().plusDays(3),
                Status.EM_ANDAMENTO,
                null, Prioridade.ALTA);

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
                        null, null));

        assertEquals("Prioridade não pode ser vazio", exception.getMessage());
    }
}

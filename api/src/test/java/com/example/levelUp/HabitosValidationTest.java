package com.example.levelUp;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.example.levelUp.model.Habito;
import com.example.levelUp.model.enums.Status;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class HabitosValidationTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Habito criarHabitoValido() {
        Habito habito = new Habito();
        habito.setTitulo("Hábito Válido");
        habito.setDescricao("Hábito Válido");
        habito.setDataInicio(LocalDate.of(2025, 1, 1));
        habito.setDataConclusao(LocalDate.now().plusDays(1));
        habito.setStatus(Status.PENDENTE);

        return habito;
    }

    @Test
    public void testHabitoValido_NaoDeveTerViolacoes() {
        Habito habito = criarHabitoValido();
        Set<ConstraintViolation<Habito>> violations = validator.validate(habito);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testTituloNotBlank() {
        Habito habito = criarHabitoValido();
        habito.setTitulo("");

        Set<ConstraintViolation<Habito>> violations = validator.validate(habito);
        assertThat(violations).isNotEmpty();
        Set<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
        assertThat(messages).contains("Titulo é obrigatório.");
    }

    @Test
    public void testDataInicioNoPassado() {
        Habito habito = criarHabitoValido();
        habito.setDataInicio(LocalDate.now().minusDays(1));

        Set<ConstraintViolation<Habito>> violations = validator.validate(habito);
        assertThat(violations).isNotEmpty();
        Set<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
        assertThat(messages).contains("Data de criação não pode ser no passado.");
    }

    @Test
    public void testValidacaoCustomizada_DataConclusaoAntesDeInicio() {
        Habito habito = criarHabitoValido();
        habito.setDataInicio(LocalDate.now().plusDays(5));

        habito.setDataConclusao(LocalDate.now().plusDays(3));

        Set<ConstraintViolation<Habito>> violations = validator.validate(habito);
        assertThat(violations).isNotEmpty();
        Set<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
        assertThat(messages).contains("Data de conclusão deve ser depois da data de início.");
    }
}
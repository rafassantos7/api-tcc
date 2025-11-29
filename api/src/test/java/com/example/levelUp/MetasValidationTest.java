package com.example.levelUp;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.example.levelUp.model.Meta;
import com.example.levelUp.model.enums.Status;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MetasValidationTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Meta criarMetaValida() {
        Meta meta = new Meta();
        meta.setTitulo("Título Válido");
        meta.setDataInicio(LocalDate.now());
        meta.setDataConclusao(LocalDate.now().plusDays(1));
        meta.setStatus(Status.PENDENTE);
        return meta;
    }

    @Test
    public void testConstrutorPadrao_DeveDefinirAtingidaComoFalse() {
        Meta meta = new Meta();
        assertThat(meta.getStatus()).isEqualTo(Status.PENDENTE);
    }

    @Test
    public void testMetaValida_NaoDeveTerViolacoes() {
        Meta meta = criarMetaValida();
        Set<ConstraintViolation<Meta>> violations = validator.validate(meta);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testTituloNotBlank() {
        Meta meta = criarMetaValida();
        meta.setTitulo("");

        Set<ConstraintViolation<Meta>> violations = validator.validate(meta);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Titulo é obrigatório.");
    }

    @Test
    public void testDataInicioNoPassado() {
        Meta meta = criarMetaValida();
        meta.setDataInicio(LocalDate.now().minusDays(1));

        Set<ConstraintViolation<Meta>> violations = validator.validate(meta);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Data de criação não pode ser no passado.");
    }

    @Test
    public void testDataConclusaoNaoEhFuture() {
        Meta meta = criarMetaValida();
        meta.setDataConclusao(LocalDate.now());

        Set<ConstraintViolation<Meta>> violations = validator.validate(meta);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().equals("Data de conclusão deve ser no futuro."));
    }

    @Test
    public void testValidacaoCustomizada_DataConclusaoAntesDeInicio() {
        Meta meta = criarMetaValida();
        meta.setDataInicio(LocalDate.now().plusDays(5));
        meta.setDataConclusao(LocalDate.now().plusDays(3));

        Set<ConstraintViolation<Meta>> violations = validator.validate(meta);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Data de conclusão deve ser depois da data de início.");
    }
}
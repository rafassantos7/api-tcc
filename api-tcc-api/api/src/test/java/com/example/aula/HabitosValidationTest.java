package com.example.aula;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.example.aula.model.Habitos;
import com.example.aula.model.Status;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class HabitosValidationTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Habitos criarHabitoValido() {
        Habitos habito = new Habitos();
        habito.setTitulo("Hábito Válido");
        habito.setDataInicio(LocalDate.now()); 
        habito.setDataConclusao(LocalDate.now().plusDays(1));
        habito.setStatus(Status.PENDENTE); 
        
        return habito;
    }

    @Test
    public void testHabitoValido_NaoDeveTerViolacoes() {
        Habitos habito = criarHabitoValido();
        Set<ConstraintViolation<Habitos>> violations = validator.validate(habito);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testTituloNotBlank() {
        Habitos habito = criarHabitoValido();
        habito.setTitulo("");

        Set<ConstraintViolation<Habitos>> violations = validator.validate(habito);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Titulo é obrigatório.");
    }

    @Test
    public void testDataInicioNoPassado() {
        Habitos habito = criarHabitoValido();
        habito.setDataInicio(LocalDate.now().minusDays(1)); 

        Set<ConstraintViolation<Habitos>> violations = validator.validate(habito);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Data de criação não pode ser no passado.");
    }

    @Test
    public void testValidacaoCustomizada_DataConclusaoAntesDeInicio() {
        Habitos habito = criarHabitoValido();
        habito.setDataInicio(LocalDate.now().plusDays(5));
        
        habito.setDataConclusao(LocalDate.now().plusDays(3)); 

        Set<ConstraintViolation<Habitos>> violations = validator.validate(habito);
        assertThat(violations).hasSize(1);
        
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Data de conclusão deve ser depois da data de início.");
    }
}
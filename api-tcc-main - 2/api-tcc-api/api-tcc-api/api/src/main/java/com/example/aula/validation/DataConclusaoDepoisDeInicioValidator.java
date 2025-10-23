package com.example.aula.validation;

import com.example.aula.model.CadastroTarefas;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DataConclusaoDepoisDeInicioValidator implements ConstraintValidator<DataConclusaoDepoisDeInicio, CadastroTarefas> {
    @Override
    public boolean isValid(CadastroTarefas value, ConstraintValidatorContext context) {
        if (value == null) return true;
        if (value.getDataInicio() == null || value.getDataConclusao() == null) return true;
        return value.getDataConclusao().isAfter(value.getDataInicio());
    }
}
package com.example.aula.validation;

import com.example.aula.model.CadastroGeral;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DataConclusaoDepoisDeInicioValidator implements ConstraintValidator<DataConclusaoDepoisDeInicio, CadastroGeral> {
    @Override
    public boolean isValid(CadastroGeral value, ConstraintValidatorContext context) {
        if (value == null) return true;
        if (value.getDataInicio() == null || value.getDataConclusao() == null) return true;
        return value.getDataConclusao().isAfter(value.getDataInicio());
    }
}
package com.example.levelUp.validation;

import com.example.levelUp.model.CadastroGeral;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// MUDANÇA 1: Troque 'Habito' por 'CadastroGeral'
public class DataConclusaoDepoisDeInicioValidator
        implements ConstraintValidator<DataConclusaoDepoisDeInicio, CadastroGeral> {

    @Override
    public boolean isValid(CadastroGeral value, ConstraintValidatorContext context) {
        // 1. Se o objeto é nulo, deixa passar (outra validação cuida disso)
        if (value == null)
            return true;

        // 2. Se as datas são nulas, deixa passar (o @NotNull cuida disso)
        // Isso é CRUCIAL para permitir Hábitos sem data de fim (se dataConclusao for
        // null)
        if (value.getDataInicio() == null || value.getDataConclusao() == null) {
            return true;
        }

        // 3. A Lógica Flexível:
        // Usamos "!isBefore" (Não é antes).
        // Isso significa: "É depois OU é igual".
        // Permite tarefas de um dia só (Início: 20/11, Fim: 20/11).
        return !value.getDataConclusao().isBefore(value.getDataInicio());
    }
}
package com.example.aula.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DataConclusaoDepoisDeInicioValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DataConclusaoDepoisDeInicio {
    String message() default "Data de conclusão deve ser depois da data de início.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
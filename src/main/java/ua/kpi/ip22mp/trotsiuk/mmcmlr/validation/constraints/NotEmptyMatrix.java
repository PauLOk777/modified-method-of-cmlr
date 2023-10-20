package ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.validators.NotEmptyMatrixValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = NotEmptyMatrixValidator.class)
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface NotEmptyMatrix {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

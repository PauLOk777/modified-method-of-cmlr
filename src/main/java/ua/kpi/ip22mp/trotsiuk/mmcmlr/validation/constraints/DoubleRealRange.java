package ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.validators.DoubleRealRangeValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = DoubleRealRangeValidator.class)
@Target(TYPE_USE)
@Retention(RUNTIME)
public @interface DoubleRealRange {
    String message() default "{unreal.range.exception}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

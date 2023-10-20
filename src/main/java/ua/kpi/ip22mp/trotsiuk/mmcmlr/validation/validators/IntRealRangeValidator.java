package ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.util.IntRange;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.IntRealRange;

import static java.util.Objects.isNull;

public class IntRealRangeValidator implements ConstraintValidator<IntRealRange, IntRange> {

    @Override
    public boolean isValid(IntRange range, ConstraintValidatorContext constraintValidatorContext) {
        if (isNull(range)) {
            return true;
        }

        return range.start() < range.end();
    }
}

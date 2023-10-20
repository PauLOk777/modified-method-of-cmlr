package ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.util.DoubleRange;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.DoubleRealRange;

import static java.util.Objects.isNull;

public class DoubleRealRangeValidator implements ConstraintValidator<DoubleRealRange, DoubleRange> {

    @Override
    public boolean isValid(DoubleRange range, ConstraintValidatorContext constraintValidatorContext) {
        if (isNull(range)) {
            return true;
        }

        return range.start() < range.end();
    }
}

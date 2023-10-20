package ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.DoubleRandomNumbersRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.DoubleRealRange;

public class DoubleRealRangeValidator implements ConstraintValidator<DoubleRealRange, DoubleRandomNumbersRequestBody> {

    @Override
    public boolean isValid(DoubleRandomNumbersRequestBody doubleRandomNumbersRequestBody,
                           ConstraintValidatorContext constraintValidatorContext) {
        return doubleRandomNumbersRequestBody.begin() < doubleRandomNumbersRequestBody.end();
    }
}

package ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.IntRandomNumbersRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.IntRealRange;

public class IntRealRangeValidator implements ConstraintValidator<IntRealRange, IntRandomNumbersRequestBody> {

    @Override
    public boolean isValid(IntRandomNumbersRequestBody intRandomNumbersRequestBody,
                           ConstraintValidatorContext constraintValidatorContext) {
        return intRandomNumbersRequestBody.begin() < intRandomNumbersRequestBody.end();
    }
}

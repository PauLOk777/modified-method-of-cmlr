package ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.NormallyDistributedRandomNumbersMmcmlrRequestBody;

@Component
public class NormallyDistributedRandomNumbersMmcmlrRequestBodyValidator implements Validator {

    private static final String NUMBER_OF_COEFFICIENTS_SHOULD_BE_EQUAL_TO_NUMBER_OF_INDEPENDENT_VARIABLES_PLUS_ONE =
            "number.of.coefficients.should.be.equal.to.number.of.independent.variables.plus.one";

    @Override
    public boolean supports(Class<?> clazz) {
        return NormallyDistributedRandomNumbersMmcmlrRequestBody.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NormallyDistributedRandomNumbersMmcmlrRequestBody requestBody =
                (NormallyDistributedRandomNumbersMmcmlrRequestBody) target;

        if (requestBody.correctCoefficients().length != requestBody.independentVariables()[0].length + 1) {
            errors.reject(NUMBER_OF_COEFFICIENTS_SHOULD_BE_EQUAL_TO_NUMBER_OF_INDEPENDENT_VARIABLES_PLUS_ONE);
        }
    }
}

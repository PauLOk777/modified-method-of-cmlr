package ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody;

@Component
public class ModifiedMethodOfCmlrRequestBodyValidator implements Validator {

    private static final String NUMBER_OF_COEFFICIENTS_SHOULD_BE_EQUAL_TO_NUMBER_OF_INDEPENDENT_VARIABLES_PLUS_ONE =
            "number.of.coefficients.should.be.equal.to.number.of.independent.variables.plus.one";
    private static final String ERRORS_ROWS_NUMBER_SHOULD_BE_EQUAL_TO_INDEPENDENT_VARIABLES_ROWS_NUMBER =
            "errors.rows.number.should.be.equal.to.independent.variables.rows.number";
    private static final String ERRORS_ROW_SIZE_SHOULD_BE_EQUAL_TO_TOTAL_NUMBER_OF_EXPERIMENTS_GROUP =
            "errors.row.size.should.be.equal.to.total.number.of.experiments.group";

    @Override
    public boolean supports(Class<?> clazz) {
        return ModifiedMethodOfCmlrRequestBody.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ModifiedMethodOfCmlrRequestBody requestBody = (ModifiedMethodOfCmlrRequestBody) target;

        if (requestBody.correctCoefficients().length != requestBody.independentVariables()[0].length + 1) {
            errors.reject(NUMBER_OF_COEFFICIENTS_SHOULD_BE_EQUAL_TO_NUMBER_OF_INDEPENDENT_VARIABLES_PLUS_ONE);
        }

        if (requestBody.independentVariables().length != requestBody.errors().length) {
            errors.reject(ERRORS_ROWS_NUMBER_SHOULD_BE_EQUAL_TO_INDEPENDENT_VARIABLES_ROWS_NUMBER);
        }

        if (requestBody.errors()[0].length !=
                requestBody.repetitionsNumberOfActiveExperiments() + requestBody.numberOfValidationSequences()) {
            errors.reject(ERRORS_ROW_SIZE_SHOULD_BE_EQUAL_TO_TOTAL_NUMBER_OF_EXPERIMENTS_GROUP);
        }
    }
}

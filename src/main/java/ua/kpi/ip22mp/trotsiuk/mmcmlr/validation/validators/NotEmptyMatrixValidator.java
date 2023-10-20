package ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.NotEmptyMatrix;

public class NotEmptyMatrixValidator implements ConstraintValidator<NotEmptyMatrix, double[][]> {

    @Override
    public boolean isValid(double[][] matrix,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }

        for (double[] doubles : matrix) {
            if (doubles.length == 0) {
                return false;
            }
        }

        return true;
    }
}

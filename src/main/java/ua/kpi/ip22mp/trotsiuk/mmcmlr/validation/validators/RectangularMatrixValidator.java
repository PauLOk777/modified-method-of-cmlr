package ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.RectangularMatrix;

public class RectangularMatrixValidator implements ConstraintValidator<RectangularMatrix, double[][]> {

    @Override
    public boolean isValid(double[][] matrix, ConstraintValidatorContext constraintValidatorContext) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return true;
        }

        int expectedRowSize = matrix[0].length;
        for (double[] row : matrix) {
            if (row.length != expectedRowSize) {
                return false;
            }
        }

        return true;
    }
}

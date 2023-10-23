package ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.MatrixSize;

public class MatrixSizeValidator implements ConstraintValidator<MatrixSize, double[][]> {

    private int minRows;
    private int maxRows;
    private int minColumns;
    private int maxColumns;

    @Override
    public void initialize(MatrixSize constraintAnnotation) {
        minRows = constraintAnnotation.minRows();
        maxRows = constraintAnnotation.maxRows();
        minColumns = constraintAnnotation.minColumns();
        maxColumns = constraintAnnotation.maxColumns();
    }

    @Override
    public boolean isValid(double[][] matrix, ConstraintValidatorContext constraintValidatorContext) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null) {
            return true;
        }

        formatMessage(constraintValidatorContext);
        return matrix.length >= minRows && matrix.length <= maxRows
                && matrix[0].length >= minColumns && matrix[0].length <= maxColumns;
    }

    private void formatMessage(ConstraintValidatorContext context) {
        String msg = context.getDefaultConstraintMessageTemplate();
        String formattedMsg = String.format(msg, minRows, maxRows, minColumns, maxColumns);
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(formattedMsg).addConstraintViolation();
    }
}

package ua.kpi.ip22mp.trotsiuk.mmcmlr.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.util.DoubleRange;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.DoubleRealRange;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.MatrixSize;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.NotEmptyMatrix;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.RectangularMatrix;

public record RandomNumbersMMCMLRRequestBody (
    @Min(value = 1, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.repetitionsNumberOfActiveExperiments.Min}")
    @Max(value = 100, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.repetitionsNumberOfActiveExperiments.Max}")
    int repetitionsNumberOfActiveExperiments,
    @Min(value = 1, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.numberOfValidationSequences.Min}")
    @Max(value = 100, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.numberOfValidationSequences.Max}")
    int numberOfValidationSequences,
    @Min(value = 1, message = "{}")
    int numberOfRuns,
    @Size(min = 1, max = 101, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.correctCoefficients.Size}")
    double[] correctCoefficients,
    @NotEmptyMatrix(message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.independentVariables.NotEmptyMatrix}")
    @RectangularMatrix(message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.independentVariables.RectangularMatrix}")
    @MatrixSize(maxRows = 100, maxColumns = 100, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.independentVariables.MatrixSize}")
    double[][] independentVariables,
    @NotNull(message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.DoubleRandomNumbersRequestBody.range.NotNull}")
    @DoubleRealRange DoubleRange range
) {}

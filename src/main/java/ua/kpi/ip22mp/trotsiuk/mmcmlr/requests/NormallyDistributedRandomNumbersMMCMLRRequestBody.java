package ua.kpi.ip22mp.trotsiuk.mmcmlr.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.MatrixSize;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.NotEmptyMatrix;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.RectangularMatrix;

public record NormallyDistributedRandomNumbersMMCMLRRequestBody (
    @Min(value = 1, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.repetitionsNumberOfActiveExperiments.Min}")
    @Max(value = 10, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.repetitionsNumberOfActiveExperiments.Max}")
    int repetitionsNumberOfActiveExperiments,
    @Min(value = 1, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.numberOfValidationSequences.Min}")
    @Max(value = 10, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.numberOfValidationSequences.Max}")
    int numberOfValidationSequences,
    @Min(value = 1, message = "{number.of.runs.Min}")
    @Min(value = 10000, message = "{number.of.runs.Max}")
    int numberOfRuns,
    @Size(min = 1, max = 31, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.correctCoefficients.Size}")
    double[] correctCoefficients,
    @NotEmptyMatrix(message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.independentVariables.NotEmptyMatrix}")
    @RectangularMatrix(message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.independentVariables.RectangularMatrix}")
    @MatrixSize(maxRows = 300, maxColumns = 30, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.independentVariables.MatrixSize}")
    double[][] independentVariables,
    double mean,
    @Min(value = 0, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.MatrixOfNormallyDistributedRandomNumbersRequestBody.stdDev.Min}")
    double stdDev
) {}

package ua.kpi.ip22mp.trotsiuk.mmcmlr.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.NotEmptyMatrix;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.RectangularMatrix;

public record ModifiedMethodOfCmlrRequestBody(
        @Min(value = 2, message =
                "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.totalNumberOfExperimentsGroup.Min}")
        int totalNumberOfExperimentsGroup,
        @Min(value = 1, message =
                "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.initialNumberOfExperimentsGroup.Min}")
        int initialNumberOfExperimentsGroup,
        @NotEmptyMatrix(message =
                "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.independentVariables.NotEmptyMatrix}")
        @RectangularMatrix(message =
                "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.independentVariables.RectangularMatrix}")
        double[][] independentVariables,
        @Size(min = 1, message =
                "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.correctCoefficients.Size}")
        double[] correctCoefficients,
        @NotEmptyMatrix(message =
                "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.errors.NotEmptyMatrix}")
        @RectangularMatrix(message =
                "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody.errors.RectangularMatrix}")
        double[][] errors) {}

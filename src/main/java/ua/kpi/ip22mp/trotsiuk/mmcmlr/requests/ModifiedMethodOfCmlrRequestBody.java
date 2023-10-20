package ua.kpi.ip22mp.trotsiuk.mmcmlr.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ModifiedMethodOfCmlrRequestBody(
        @Min(2) int totalNumberOfExperimentsGroup, @Min(1) int initialNumberOfExperimentsGroup,
        @NotNull double[][] independentVariables, @NotNull @Size(min = 1) double[] correctCoefficients,
        @NotNull double[][] errors) {}

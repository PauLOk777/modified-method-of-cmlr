package ua.kpi.ip22mp.trotsiuk.mmcmlr.models;

import java.util.List;

public record ModifiedMethodOfCmlrRequestBody(
        int experimentsNumber, int initialExperimentsNumber, int validationExperimentsNumber,
        List<List<Double>> independentVariables, List<Double> correctCoefficients, List<List<Double>> errorsMatrix) {}

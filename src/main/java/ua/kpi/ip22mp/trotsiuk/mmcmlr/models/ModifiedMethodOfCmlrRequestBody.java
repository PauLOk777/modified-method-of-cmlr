package ua.kpi.ip22mp.trotsiuk.mmcmlr.models;

public record ModifiedMethodOfCmlrRequestBody(
        int experimentsNumber, int initialExperimentsNumber, int validationExperimentsNumber,
        double[][] independentVariables, double[] correctCoefficients, double[][] errorsMatrix) {}

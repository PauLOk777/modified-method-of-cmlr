package ua.kpi.ip22mp.trotsiuk.mmcmlr.models;

public record ModifiedMethodOfCmlrRequestBody(
        int totalNumberOfExperimentsGroup, int initialNumberOfExperimentsGroup, double[][] independentVariables,
        double[] correctCoefficients, double[][] errors) {}

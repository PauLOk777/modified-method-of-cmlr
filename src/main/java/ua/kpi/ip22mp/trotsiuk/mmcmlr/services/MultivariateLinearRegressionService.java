package ua.kpi.ip22mp.trotsiuk.mmcmlr.services;

public interface MultivariateLinearRegressionService {

    double[] solveRegressionWithModifiedMethodOfCmlr(
            int totalNumberOfExperimentsGroup, int initialNumberOfExperimentsGroup, double[][] independentVariables,
            double[] correctCoefficients, double[][] errors);
}

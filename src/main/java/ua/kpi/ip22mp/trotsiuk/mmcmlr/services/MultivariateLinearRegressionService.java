package ua.kpi.ip22mp.trotsiuk.mmcmlr.services;

public interface MultivariateLinearRegressionService {

    double[] solveRegressionWithModifiedMethodOfCmlr(
            int experimentsNumber, int initialExperimentsNumber, double[][] independentVariables,
            double[] correctCoefficients, double[][] errors);
}

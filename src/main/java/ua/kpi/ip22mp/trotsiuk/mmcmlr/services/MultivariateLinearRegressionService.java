package ua.kpi.ip22mp.trotsiuk.mmcmlr.services;

public interface MultivariateLinearRegressionService {

    double[] solveRegressionWithModifiedMethodOfCmlr(
            int experimentsNumber, int initialExperimentsNumber, int validationExperimentsNumber,
            double[][] independentVariables, double[] correctCoefficients, double[][] errorsMatrix);
}

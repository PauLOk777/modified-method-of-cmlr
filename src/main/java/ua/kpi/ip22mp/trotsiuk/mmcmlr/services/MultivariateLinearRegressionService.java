package ua.kpi.ip22mp.trotsiuk.mmcmlr.services;

import ua.kpi.ip22mp.trotsiuk.mmcmlr.dto.RegressionCalculationDto;

public interface MultivariateLinearRegressionService {

    RegressionCalculationDto solveRegressionWithModifiedMethodOfCmlr(
            int totalNumberOfExperimentsGroup, int initialNumberOfExperimentsGroup, double[][] independentVariables,
            double[] correctCoefficients, double[][] errors);
}

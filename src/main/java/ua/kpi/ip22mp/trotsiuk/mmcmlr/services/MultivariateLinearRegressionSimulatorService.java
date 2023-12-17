package ua.kpi.ip22mp.trotsiuk.mmcmlr.services;

import ua.kpi.ip22mp.trotsiuk.mmcmlr.dto.RegressionCalculationStatisticsDto;

public interface MultivariateLinearRegressionSimulatorService {

    RegressionCalculationStatisticsDto simulateMultipleTimesModifiedMethodOfCmlrWithUniformlyDistributedRandomNumbers(
            int repetitionsNumberOfActiveExperiments, int numberOfValidationSequences, int numberOfRuns,
            double[] correctCoefficients, double[][] independentVariables, double start, double end
    );

    RegressionCalculationStatisticsDto simulateMultipleTimesModifiedMethodOfCmlrWithNormallyDistributedRandomNumbers(
            int repetitionsNumberOfActiveExperiments, int numberOfValidationSequences, int numberOfRuns,
            double[] correctCoefficients, double[][] independentVariables, double mean, double stdDev
    );
}

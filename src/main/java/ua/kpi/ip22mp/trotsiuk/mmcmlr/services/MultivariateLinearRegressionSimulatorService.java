package ua.kpi.ip22mp.trotsiuk.mmcmlr.services;

import ua.kpi.ip22mp.trotsiuk.mmcmlr.dto.RegressionSimulationStatisticsDto;

public interface MultivariateLinearRegressionSimulatorService {

    RegressionSimulationStatisticsDto simulateMultipleTimesModifiedMethodOfCmlrWithUniformlyDistributedRandomNumbers(
            int repetitionsNumberOfActiveExperiments, int numberOfValidationSequences, int numberOfRuns,
            double[] correctCoefficients, double[][] independentVariables, double start, double end
    );

    RegressionSimulationStatisticsDto simulateMultipleTimesModifiedMethodOfCmlrWithNormallyDistributedRandomNumbers(
            int repetitionsNumberOfActiveExperiments, int numberOfValidationSequences, int numberOfRuns,
            double[] correctCoefficients, double[][] independentVariables, double mean, double stdDev
    );
}

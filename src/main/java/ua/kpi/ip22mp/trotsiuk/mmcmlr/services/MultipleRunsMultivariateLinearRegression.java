package ua.kpi.ip22mp.trotsiuk.mmcmlr.services;

import ua.kpi.ip22mp.trotsiuk.mmcmlr.dto.MultipleRunsOfRegressionCalculationDto;

public interface MultipleRunsMultivariateLinearRegression {

    MultipleRunsOfRegressionCalculationDto multipleRunsOfModifiedMethodOfCmlrWithRandomNumbers(
            int repetitionsNumberOfActiveExperiments, int numberOfValidationSequences, int numberOfRuns,
            double[] correctCoefficients, double[][] independentVariables, double start, double end
    );

    MultipleRunsOfRegressionCalculationDto multipleRunsOfModifiedMethodOfCmlrWithNormallyDistributedRandomNumbers(
            int repetitionsNumberOfActiveExperiments, int numberOfValidationSequences, int numberOfRuns,
            double[] correctCoefficients, double[][] independentVariables, double mean, double stdDev
    );
}

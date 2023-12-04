package ua.kpi.ip22mp.trotsiuk.mmcmlr.services.impl;

import org.springframework.stereotype.Service;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.dto.MultipleRunsOfRegressionCalculationDto;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.dto.RegressionCalculationDto;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultipleRunsMultivariateLinearRegression;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultivariateLinearRegressionService;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.RandomNumbersService;

import static ua.kpi.ip22mp.trotsiuk.mmcmlr.util.VectorUtils.getNumberOfMissingZeros;

@Service
public class MultipleRunsMultivariateLinearRegressionImpl implements MultipleRunsMultivariateLinearRegression {

    private MultivariateLinearRegressionService multivariateLinearRegressionService;
    private RandomNumbersService randomNumbersService;

    public MultipleRunsMultivariateLinearRegressionImpl(MultivariateLinearRegressionService multivariateLinearRegressionService, RandomNumbersService randomNumbersService) {
        this.multivariateLinearRegressionService = multivariateLinearRegressionService;
        this.randomNumbersService = randomNumbersService;
    }

    @Override
    public MultipleRunsOfRegressionCalculationDto multipleRunsOfModifiedMethodOfCmlrWithRandomNumbers(
            int repetitionsNumberOfActiveExperiments, int numberOfValidationSequences, int numberOfRuns,
            double[] correctCoefficients, double[][] independentVariables, double start, double end) {
        int countOfCorrectModels = 0;
        int countOfIncorrectModelsWithOneIncorrectZero = 0;
        int countOfIncorrectModelsWithTwoPlusIncorrectZeros = 0;

        for (int i = 0; i < numberOfRuns; i++) {
            double[][] errors = randomNumbersService.generateRandomDoubleNumbersInMatrix(independentVariables.length,
                    repetitionsNumberOfActiveExperiments + numberOfValidationSequences, start, end);
            RegressionCalculationDto regressionCalculationDto = multivariateLinearRegressionService
                    .solveRegressionWithModifiedMethodOfCmlr(repetitionsNumberOfActiveExperiments, numberOfValidationSequences,
                            independentVariables, correctCoefficients, errors);

            int numberOfMissingZeros = getNumberOfMissingZeros(correctCoefficients, regressionCalculationDto.coefficients());

            switch (numberOfMissingZeros) {
                case 0:
                    countOfCorrectModels++;
                    break;
                case 1:
                    countOfIncorrectModelsWithOneIncorrectZero++;
                    break;
                default: countOfIncorrectModelsWithTwoPlusIncorrectZeros++;
            }
        }

        return new MultipleRunsOfRegressionCalculationDto((double) countOfCorrectModels / numberOfRuns * 100,
                (double) countOfIncorrectModelsWithOneIncorrectZero / numberOfRuns * 100,
                (double) countOfIncorrectModelsWithTwoPlusIncorrectZeros / numberOfRuns * 100);
    }

    @Override
    public MultipleRunsOfRegressionCalculationDto multipleRunsOfModifiedMethodOfCmlrWithNormallyDistributedRandomNumbers(
            int repetitionsNumberOfActiveExperiments, int numberOfValidationSequences, int numberOfRuns,
            double[] correctCoefficients, double[][] independentVariables, double mean, double stdDev) {
        int countOfCorrectModels = 0;
        int countOfIncorrectModelsWithOneIncorrectZero = 0;
        int countOfIncorrectModelsWithTwoPlusIncorrectZeros = 0;

        for (int i = 0; i < numberOfRuns; i++) {
            double[][] errors = randomNumbersService.generateMatrixOfNormallyDistributedRandomNumbers(mean, stdDev,
                    independentVariables.length, repetitionsNumberOfActiveExperiments + numberOfValidationSequences);
            RegressionCalculationDto regressionCalculationDto = multivariateLinearRegressionService
                    .solveRegressionWithModifiedMethodOfCmlr(repetitionsNumberOfActiveExperiments, numberOfValidationSequences,
                            independentVariables, correctCoefficients, errors);

            int numberOfMissingZeros = getNumberOfMissingZeros(correctCoefficients, regressionCalculationDto.coefficients());

            switch (numberOfMissingZeros) {
                case 0:
                    countOfCorrectModels++;
                    break;
                case 1:
                    countOfIncorrectModelsWithOneIncorrectZero++;
                    break;
                default: countOfIncorrectModelsWithTwoPlusIncorrectZeros++;
            }
        }

        return new MultipleRunsOfRegressionCalculationDto((double) countOfCorrectModels / numberOfRuns * 100,
                (double) countOfIncorrectModelsWithOneIncorrectZero / numberOfRuns * 100,
                (double) countOfIncorrectModelsWithTwoPlusIncorrectZeros / numberOfRuns * 100);
    }
}

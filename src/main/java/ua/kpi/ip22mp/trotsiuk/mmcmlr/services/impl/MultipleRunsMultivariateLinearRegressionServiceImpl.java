package ua.kpi.ip22mp.trotsiuk.mmcmlr.services.impl;

import org.springframework.stereotype.Service;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.dto.MultipleRunsOfRegressionCalculationDto;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.dto.RegressionCalculationDto;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultipleRunsMultivariateLinearRegressionService;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultivariateLinearRegressionService;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.RandomNumbersService;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_EVEN;
import static ua.kpi.ip22mp.trotsiuk.mmcmlr.util.VectorUtils.getNumberOfMissingZeros;

@Service
public class MultipleRunsMultivariateLinearRegressionServiceImpl implements MultipleRunsMultivariateLinearRegressionService {

    private static final int PERCENTAGE_SCALE = 2;

    private MultivariateLinearRegressionService multivariateLinearRegressionService;
    private RandomNumbersService randomNumbersService;

    public MultipleRunsMultivariateLinearRegressionServiceImpl(MultivariateLinearRegressionService multivariateLinearRegressionService, RandomNumbersService randomNumbersService) {
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

        return new MultipleRunsOfRegressionCalculationDto(calculatePercentage(countOfCorrectModels, numberOfRuns),
                calculatePercentage(countOfIncorrectModelsWithOneIncorrectZero, numberOfRuns),
                calculatePercentage(countOfIncorrectModelsWithTwoPlusIncorrectZeros, numberOfRuns));
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

        return new MultipleRunsOfRegressionCalculationDto(calculatePercentage(countOfCorrectModels, numberOfRuns),
                calculatePercentage(countOfIncorrectModelsWithOneIncorrectZero, numberOfRuns),
                calculatePercentage(countOfIncorrectModelsWithTwoPlusIncorrectZeros, numberOfRuns));
    }

    private static double calculatePercentage(int numerator, int denominator) {
        return valueOf(numerator)
                .multiply(valueOf(100))
                .divide(valueOf(denominator), PERCENTAGE_SCALE, HALF_EVEN)
                .doubleValue();
    }
}

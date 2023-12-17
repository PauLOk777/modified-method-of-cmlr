package ua.kpi.ip22mp.trotsiuk.mmcmlr.services.impl;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.springframework.stereotype.Service;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.dto.RegressionSimulationStatisticsDto;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultivariateLinearRegressionSimulatorService;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultivariateLinearRegressionService;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.RandomNumbersService;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_EVEN;
import static ua.kpi.ip22mp.trotsiuk.mmcmlr.util.VectorUtils.calculateComparisonMeasureValue;
import static ua.kpi.ip22mp.trotsiuk.mmcmlr.util.VectorUtils.getNumberOfMissingZeros;

@Service
public class MultivariateLinearRegressionSimulatorSimulatorServiceImpl implements MultivariateLinearRegressionSimulatorService {

    private static final int PERCENTAGE_SCALE = 2;

    private final MultivariateLinearRegressionService multivariateLinearRegressionService;
    private final RandomNumbersService randomNumbersService;

    public MultivariateLinearRegressionSimulatorSimulatorServiceImpl(
            MultivariateLinearRegressionService multivariateLinearRegressionService,
            RandomNumbersService randomNumbersService) {
        this.multivariateLinearRegressionService = multivariateLinearRegressionService;
        this.randomNumbersService = randomNumbersService;
    }

    @Override
    public RegressionSimulationStatisticsDto simulateMultipleTimesModifiedMethodOfCmlrWithUniformlyDistributedRandomNumbers(
            int repetitionsNumberOfActiveExperiments, int numberOfValidationSequences, int numberOfRuns,
            double[] correctCoefficients, double[][] independentVariables, double start, double end) {
        RealMatrix independentVariablesMatrix = new Array2DRowRealMatrix(independentVariables);
        int countOfCorrectModels = 0;
        int countOfIncorrectModelsWithOneIncorrectZero = 0;
        int countOfIncorrectModelsWithTwoPlusIncorrectZeros = 0;
        double totalComparisonMeasureValueForCorrectModels = 0;
        double minComparisonMeasureValueForCorrectModels = -1;
        double maxComparisonMeasureValueForCorrectModels = -1;

        for (int i = 0; i < numberOfRuns; i++) {
            double[][] randomVariables = randomNumbersService.generateMatrixOfUniformlyDistributedDoubleRandomNumbers(
                    independentVariables.length, repetitionsNumberOfActiveExperiments + numberOfValidationSequences, start, end);
            double[] resultCoefficients = simulateModifiedMethodOfCmlr(independentVariablesMatrix, correctCoefficients,
                    new Array2DRowRealMatrix(randomVariables), repetitionsNumberOfActiveExperiments, numberOfValidationSequences);

            int numberOfMissingZeros = getNumberOfMissingZeros(correctCoefficients, resultCoefficients);

            switch (numberOfMissingZeros) {
                case 0:
                    double comparisonMeasureValue = calculateComparisonMeasureValue(resultCoefficients, correctCoefficients);
                    totalComparisonMeasureValueForCorrectModels += comparisonMeasureValue;
                    if (minComparisonMeasureValueForCorrectModels == -1) {
                        minComparisonMeasureValueForCorrectModels = comparisonMeasureValue;
                        maxComparisonMeasureValueForCorrectModels = comparisonMeasureValue;
                    }

                    if (minComparisonMeasureValueForCorrectModels > comparisonMeasureValue) {
                        minComparisonMeasureValueForCorrectModels = comparisonMeasureValue;
                    }

                    if (maxComparisonMeasureValueForCorrectModels < comparisonMeasureValue) {
                        maxComparisonMeasureValueForCorrectModels = comparisonMeasureValue;
                    }

                    countOfCorrectModels++;
                    break;
                case 1:
                    countOfIncorrectModelsWithOneIncorrectZero++;
                    break;
                default: countOfIncorrectModelsWithTwoPlusIncorrectZeros++;
            }
        }

        return new RegressionSimulationStatisticsDto(calculatePercentage(countOfCorrectModels, numberOfRuns),
                calculatePercentage(countOfIncorrectModelsWithOneIncorrectZero, numberOfRuns),
                calculatePercentage(countOfIncorrectModelsWithTwoPlusIncorrectZeros, numberOfRuns),
                totalComparisonMeasureValueForCorrectModels / numberOfRuns,
                minComparisonMeasureValueForCorrectModels, maxComparisonMeasureValueForCorrectModels);
    }

    @Override
    public RegressionSimulationStatisticsDto simulateMultipleTimesModifiedMethodOfCmlrWithNormallyDistributedRandomNumbers(
            int repetitionsNumberOfActiveExperiments, int numberOfValidationSequences, int numberOfRuns,
            double[] correctCoefficients, double[][] independentVariables, double mean, double stdDev) {
        RealMatrix independentVariablesMatrix = new Array2DRowRealMatrix(independentVariables);
        int countOfCorrectModels = 0;
        int countOfIncorrectModelsWithOneIncorrectZero = 0;
        int countOfIncorrectModelsWithTwoPlusIncorrectZeros = 0;
        double totalComparisonMeasureValueForCorrectModels = 0;
        double minComparisonMeasureValueForCorrectModels = -1;
        double maxComparisonMeasureValueForCorrectModels = -1;

        for (int i = 0; i < numberOfRuns; i++) {
            double[][] randomVariables = randomNumbersService.generateMatrixOfNormallyDistributedRandomNumbers(mean, stdDev,
                    independentVariables.length, repetitionsNumberOfActiveExperiments + numberOfValidationSequences);
            double[] resultCoefficients = simulateModifiedMethodOfCmlr(independentVariablesMatrix, correctCoefficients,
                    new Array2DRowRealMatrix(randomVariables), repetitionsNumberOfActiveExperiments, numberOfValidationSequences);

            int numberOfMissingZeros = getNumberOfMissingZeros(correctCoefficients, resultCoefficients);

            switch (numberOfMissingZeros) {
                case 0:
                    double comparisonMeasureValue = calculateComparisonMeasureValue(resultCoefficients, correctCoefficients);
                    totalComparisonMeasureValueForCorrectModels += comparisonMeasureValue;
                    if (minComparisonMeasureValueForCorrectModels == -1) {
                        minComparisonMeasureValueForCorrectModels = comparisonMeasureValue;
                        maxComparisonMeasureValueForCorrectModels = comparisonMeasureValue;
                    }

                    if (minComparisonMeasureValueForCorrectModels > comparisonMeasureValue) {
                        minComparisonMeasureValueForCorrectModels = comparisonMeasureValue;
                    }

                    if (maxComparisonMeasureValueForCorrectModels < comparisonMeasureValue) {
                        maxComparisonMeasureValueForCorrectModels = comparisonMeasureValue;
                    }

                    countOfCorrectModels++;
                    break;
                case 1:
                    countOfIncorrectModelsWithOneIncorrectZero++;
                    break;
                default: countOfIncorrectModelsWithTwoPlusIncorrectZeros++;
            }
        }

        return new RegressionSimulationStatisticsDto(calculatePercentage(countOfCorrectModels, numberOfRuns),
                calculatePercentage(countOfIncorrectModelsWithOneIncorrectZero, numberOfRuns),
                calculatePercentage(countOfIncorrectModelsWithTwoPlusIncorrectZeros, numberOfRuns),
                totalComparisonMeasureValueForCorrectModels / numberOfRuns,
                minComparisonMeasureValueForCorrectModels, maxComparisonMeasureValueForCorrectModels);
    }

    private double[] simulateModifiedMethodOfCmlr(RealMatrix independentVariables, double[] correctCoefficients,
                                                  RealMatrix randomVariables, int repetitionsNumberOfActiveExperiments,
                                                  int numberOfValidationSequences) {
        RealVector dependentVariables = calculateDependentVariables(independentVariables, correctCoefficients);
        RealMatrix dependentVariablesGroups = calculateDependentVariablesGroups(dependentVariables, randomVariables);
        return multivariateLinearRegressionService.solveRegressionWithModifiedMethodOfCmlr(dependentVariablesGroups,
                independentVariables, repetitionsNumberOfActiveExperiments, numberOfValidationSequences);
    }

    private RealVector calculateDependentVariables(
            RealMatrix independentVariables, double[] correctCoefficients) {
        RealVector dependentVariables = new ArrayRealVector(independentVariables.getRowDimension());

        for (int i = 0; i < dependentVariables.getDimension(); i++) {
            double dependentVariable = correctCoefficients[0];

            for (int j = 0; j < independentVariables.getColumnDimension(); j++) {
                dependentVariable += correctCoefficients[j + 1] * independentVariables.getEntry(i, j);
            }

            dependentVariables.setEntry(i, dependentVariable);
        }

        return dependentVariables;
    }

    private RealMatrix calculateDependentVariablesGroups(RealVector dependentVariables, RealMatrix randomVariables) {
        RealMatrix adjustedDependentVariables =
                new Array2DRowRealMatrix(dependentVariables.getDimension(), randomVariables.getColumnDimension());

        for (int i = 0; i < randomVariables.getColumnDimension(); i++) {
            adjustedDependentVariables.setColumnVector(i, dependentVariables.add(randomVariables.getColumnVector(i)));
        }

        return adjustedDependentVariables;
    }

    private static double calculatePercentage(int numerator, int denominator) {
        return valueOf(numerator)
                .multiply(valueOf(100))
                .divide(valueOf(denominator), PERCENTAGE_SCALE, HALF_EVEN)
                .doubleValue();
    }
}

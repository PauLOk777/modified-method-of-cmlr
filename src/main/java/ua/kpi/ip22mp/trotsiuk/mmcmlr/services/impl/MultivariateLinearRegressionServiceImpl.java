package ua.kpi.ip22mp.trotsiuk.mmcmlr.services.impl;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.helpers.CombinationsGenerator;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.ClusterAnalysisService;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultivariateLinearRegressionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Comparator.comparingInt;
import static org.apache.commons.math3.linear.MatrixUtils.inverse;
import static ua.kpi.ip22mp.trotsiuk.mmcmlr.constants.ClusterAnalysisConstants.CLUSTER_M1_MODIFIED_METHOD;
import static ua.kpi.ip22mp.trotsiuk.mmcmlr.constants.ClusterAnalysisConstants.CLUSTER_M2_MODIFIED_METHOD;

@Service
public class MultivariateLinearRegressionServiceImpl implements MultivariateLinearRegressionService {

    //TODO ask for cluster threshold
    private static final double CLUSTER_THRESHOLD = 0.2;
    //TODO ask for percentage from min RSS
    private static final double PERCENTAGE_FROM_MIN_RSS = 3;

    private final ClusterAnalysisService clusterAnalysisService;

    @Autowired
    public MultivariateLinearRegressionServiceImpl(ClusterAnalysisService clusterAnalysisService) {
        this.clusterAnalysisService = clusterAnalysisService;
    }

    @Override
    public double[] solveRegressionWithModifiedMethodOfCmlr(
            int experimentsNumber, int initialExperimentsNumber, double[][] independentVariables,
            double[] correctCoefficients, double[][] errors) {
        RealMatrix independentVariablesMatrix = new Array2DRowRealMatrix(independentVariables);
        RealMatrix errorsMatrix = new Array2DRowRealMatrix(errors);

        RealVector dependentVariables =
                calculateDependentVariables(independentVariablesMatrix, correctCoefficients);
        RealMatrix adjustedInitialDependentVariables =
                adjustDependentVariables(dependentVariables, errorsMatrix, 0, initialExperimentsNumber);
        RealMatrix designMatrixWithAllIndependentVariables = createDesignMatrix(independentVariablesMatrix);
        RealVector meanOfInitialDependentVariables = meanOfMatrixColumns(adjustedInitialDependentVariables);

        RealVector leastSquareMethodResults = solveRegressionWithLeastSquaresMethod(
                designMatrixWithAllIndependentVariables, meanOfInitialDependentVariables);

        List<Map<Integer, Double>> clusters = clusterAnalysisService.provideClustersByModifiedMethod(
                leastSquareMethodResults.toArray(), CLUSTER_THRESHOLD);

        List<Map<Integer, Double>> partialDescriptions =
                CombinationsGenerator.generateValuesCombinations(clusters.get(CLUSTER_M2_MODIFIED_METHOD));
        partialDescriptions.forEach(map -> map.putAll(clusters.get(CLUSTER_M1_MODIFIED_METHOD)));
        List<RealMatrix> partialDescriptionsDesignMatrices = getPartialDescriptionsDesignMatrices(
                partialDescriptions, designMatrixWithAllIndependentVariables);

        updatePartialDescriptionsWithCorrectCoefficients(
                partialDescriptions, partialDescriptionsDesignMatrices,
                meanOfInitialDependentVariables, designMatrixWithAllIndependentVariables.getColumnDimension());

        RealMatrix adjustedValidationDependentVariables =
                adjustDependentVariables(dependentVariables, errorsMatrix, initialExperimentsNumber, experimentsNumber);

        RealVector residualSumOfSquares = calculateResidualSumOfSquaresForAllPartialDescriptions(partialDescriptions,
                adjustedValidationDependentVariables, designMatrixWithAllIndependentVariables);

        filterPartialDescriptionsByPercentageFromMinRss(partialDescriptions, residualSumOfSquares, PERCENTAGE_FROM_MIN_RSS);
        RealMatrix resultDesignMatrix =
                getPartialDescriptionDesignMatrixWithLowestNumberOfIndependentVariables(
                        partialDescriptions, designMatrixWithAllIndependentVariables);

        RealMatrix adjustedDependentVariables =
                new Array2DRowRealMatrix(errorsMatrix.getRowDimension(), errorsMatrix.getColumnDimension());
        adjustedDependentVariables.setSubMatrix(adjustedInitialDependentVariables.getData(), 0, 0);
        adjustedDependentVariables.setSubMatrix(adjustedValidationDependentVariables.getData(),
                0, adjustedInitialDependentVariables.getColumnDimension());
        RealVector meanOfAllDependentVariables = meanOfMatrixColumns(adjustedDependentVariables);

        return solveRegressionWithLeastSquaresMethod(resultDesignMatrix, meanOfAllDependentVariables).toArray();
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

    private RealMatrix adjustDependentVariables(
            RealVector dependentVariables, RealMatrix errors, int start, int end) {
        RealMatrix adjustedDependentVariables =
                new Array2DRowRealMatrix(dependentVariables.getDimension(), end - start);

        for (int i = 0; i < end - start; i++) {
            adjustedDependentVariables.setColumnVector(i, dependentVariables.add(errors.getColumnVector(start + i)));
        }

        return adjustedDependentVariables;
    }

    private RealMatrix createDesignMatrix(RealMatrix independentVariables) {
        RealMatrix designMatrix = new Array2DRowRealMatrix(
                independentVariables.getRowDimension(), independentVariables.getColumnDimension() + 1);
        designMatrix.setColumnVector(0, new ArrayRealVector(independentVariables.getRowDimension(), 1.0));
        designMatrix.setSubMatrix(independentVariables.getData(), 0, 1);
        return designMatrix;
    }

    private RealVector meanOfMatrixColumns(RealMatrix matrix) {
        RealVector meanVector = matrix.getColumnVector(0);

        for (int i = 1; i < matrix.getColumnDimension(); i++) {
            meanVector = meanVector.add(matrix.getColumnVector(i));
        }

        return meanVector.mapDivide(matrix.getColumnDimension());
    }

    private RealVector solveRegressionWithLeastSquaresMethod(
            RealMatrix designMatrix, RealVector meanOfDependentVariables) {
        return inverse(designMatrix.transpose().multiply(designMatrix))
                .operate(designMatrix.transpose().operate(meanOfDependentVariables));
    }

    private List<RealMatrix> getPartialDescriptionsDesignMatrices(List<Map<Integer, Double>> partialDescriptions,
                                                                  RealMatrix designMatrixWithAllIndependentVariables) {
        return partialDescriptions.stream()
                .map(partialDescription ->
                        getPartialDescriptionDesignMatrix(partialDescription, designMatrixWithAllIndependentVariables))
                .toList();
    }

    private RealMatrix getPartialDescriptionDesignMatrix(Map<Integer, Double> partialDescription,
                                                         RealMatrix designMatrixWithAllIndependentVariables) {
        RealMatrix designMatrixForPartialDescription = new Array2DRowRealMatrix(
                designMatrixWithAllIndependentVariables.getRowDimension(), partialDescription.size());
        int designMatrixForPartialDescriptionColumnCounter = 0;

        for (int j = 0; j < designMatrixWithAllIndependentVariables.getColumnDimension(); j++) {
            if (partialDescription.containsKey(j)) {
                designMatrixForPartialDescription.setColumnVector(designMatrixForPartialDescriptionColumnCounter,
                        designMatrixWithAllIndependentVariables.getColumnVector(j).copy());
                designMatrixForPartialDescriptionColumnCounter++;
            }
        }

        return designMatrixForPartialDescription;
    }

    private void updatePartialDescriptionsWithCorrectCoefficients(List<Map<Integer, Double>> partialDescriptions,
                                                                  List<RealMatrix> partialDescriptionsDesignMatrices,
                                                                  RealVector meanOfDependentVariables,
                                                                  int totalNumbersOfCoefficients) {
        for (int i = 0; i < partialDescriptionsDesignMatrices.size(); i++) {
            Map<Integer, Double> partialDescription = partialDescriptions.get(i);
            RealVector coefficients = solveRegressionWithLeastSquaresMethod(
                    partialDescriptionsDesignMatrices.get(i), meanOfDependentVariables);
            int coefficientsCounter = 0;

            for (int j = 0; j < totalNumbersOfCoefficients; j++) {
                if (partialDescription.containsKey(j)) {
                    partialDescription.put(j, coefficients.getEntry(coefficientsCounter));
                    coefficientsCounter++;
                }
            }
        }
    }

    public RealVector calculateResidualSumOfSquaresForAllPartialDescriptions(
            List<Map<Integer, Double>> partialDescriptions, RealMatrix dependentVariables,
            RealMatrix independentVariablesDesignMatrix) {
        RealVector residualSumOfSquaresForAllPartialDescriptions = new ArrayRealVector(partialDescriptions.size());
        for (int i = 0; i < partialDescriptions.size(); i++) {
            double totalSum = 0;

            for (int j = 0; j < dependentVariables.getColumnDimension(); j++) {
                totalSum += calculateResidualSumOfSquares(partialDescriptions.get(i),
                        dependentVariables.getColumnVector(j), independentVariablesDesignMatrix);
            }

            residualSumOfSquaresForAllPartialDescriptions.setEntry(i, totalSum);
        }

        return residualSumOfSquaresForAllPartialDescriptions;
    }

    private double calculateResidualSumOfSquares(Map<Integer, Double> partialDescription,
                                                 RealVector dependentVariables,
                                                 RealMatrix independentVariablesDesignMatrix) {
        double residualSumOfSquares = 0;

        for (int i = 0; i < independentVariablesDesignMatrix.getRowDimension(); i++) {
            double singleExperimentSum = 0;

            for (int j = 0; j < independentVariablesDesignMatrix.getColumnDimension(); j++) {
                singleExperimentSum +=
                        independentVariablesDesignMatrix.getEntry(i, j) * partialDescription.getOrDefault(j, 0.0);
            }

            residualSumOfSquares += Math.pow(dependentVariables.getEntry(i) - singleExperimentSum, 2);
        }

        return residualSumOfSquares;
    }

    private void filterPartialDescriptionsByPercentageFromMinRss(List<Map<Integer, Double>> partialDescriptions,
                                                                 RealVector residualSumOfSquares,
                                                                 double percentageFromMinRss) {
        double minimalRss = residualSumOfSquares.getMinValue();
        double minimalRssDeviation = percentageFromMinRss / 100;
        int partialDescriptionsCounter = 0;
        for (int i = 0; i < residualSumOfSquares.getDimension(); i++) {
            if (residualSumOfSquares.getEntry(i) > minimalRss + minimalRss * minimalRssDeviation) {
                partialDescriptions.remove(i - partialDescriptionsCounter);
                partialDescriptionsCounter++;
            }
        }
    }

    private RealMatrix getPartialDescriptionDesignMatrixWithLowestNumberOfIndependentVariables(
            List<Map<Integer, Double>> partialDescriptions, RealMatrix designMatrixWithAllIndependentVariables) {
        Map<Integer, Double> partialDescription = partialDescriptions.stream()
                .min(comparingInt(Map::size))
                .orElse(new HashMap<>());
        return getPartialDescriptionDesignMatrix(partialDescription, designMatrixWithAllIndependentVariables);
    }
}

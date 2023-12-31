package ua.kpi.ip22mp.trotsiuk.mmcmlr.services.impl;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.util.CombinationsGenerator;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.ClusterAnalysisService;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultivariateLinearRegressionService;

import java.util.List;
import java.util.Map;

import static org.apache.commons.math3.linear.MatrixUtils.inverse;
import static ua.kpi.ip22mp.trotsiuk.mmcmlr.constants.ClusterAnalysisConstants.CLUSTER_M1_NAME;
import static ua.kpi.ip22mp.trotsiuk.mmcmlr.constants.ClusterAnalysisConstants.CLUSTER_M2_NAME;

@Service
public class MultivariateLinearRegressionServiceImpl implements MultivariateLinearRegressionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultivariateLinearRegressionServiceImpl.class);

    @Value("${percentage.from.min.rss}")
    private double percentageFromMinRss;

    private final ClusterAnalysisService clusterAnalysisService;

    public MultivariateLinearRegressionServiceImpl(ClusterAnalysisService clusterAnalysisService) {
        this.clusterAnalysisService = clusterAnalysisService;
    }

    @Override
    public double[] solveRegressionWithModifiedMethodOfCmlr(
            RealMatrix dependentVariablesGroups, RealMatrix independentVariables,
            int repetitionsNumberOfActiveExperiments, int numberOfValidationSequences) {
        int numberOfExperiments = dependentVariablesGroups.getRowDimension();
        RealMatrix adjustedInitialDependentVariables = dependentVariablesGroups
                .getSubMatrix(0, numberOfExperiments - 1, 0, repetitionsNumberOfActiveExperiments - 1);
        RealMatrix adjustedValidationDependentVariables = dependentVariablesGroups
                .getSubMatrix(0, numberOfExperiments - 1, repetitionsNumberOfActiveExperiments,
                        repetitionsNumberOfActiveExperiments + numberOfValidationSequences - 1);
        RealMatrix designMatrixWithAllIndependentVariables = createDesignMatrix(independentVariables);
        RealVector meanOfInitialDependentVariables = meanOfMatrixColumns(adjustedInitialDependentVariables);
        RealVector meanOfAllDependentVariables = meanOfMatrixColumns(dependentVariablesGroups);

        RealVector initialResults = solveRegressionWithLeastSquaresMethod(
                designMatrixWithAllIndependentVariables, meanOfInitialDependentVariables);
        Map<String, Map<Integer, Double>> clusters =
                clusterAnalysisService.provideClustersByModifiedMethod(initialResults.toArray());

        if (LOGGER.isDebugEnabled()) {
            displayClusters(clusters);
        }

        List<Map<Integer, Double>> partialDescriptions = generatePartialDescriptions(clusters,
                meanOfInitialDependentVariables, designMatrixWithAllIndependentVariables);
        RealVector residualSumOfSquaresForPartialDescriptions =
                calculateResidualSumOfSquaresForAllPartialDescriptions(partialDescriptions,
                        adjustedValidationDependentVariables, designMatrixWithAllIndependentVariables);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Partial descriptions before filtering");
            displayPartialDescriptionsAndTheirRss(partialDescriptions, residualSumOfSquaresForPartialDescriptions);
        }

        residualSumOfSquaresForPartialDescriptions = filterPartialDescriptionsByPercentageFromMinRssAndReturnNewRssVector(
                partialDescriptions, residualSumOfSquaresForPartialDescriptions);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Partial descriptions after filtering");
            displayPartialDescriptionsAndTheirRss(partialDescriptions, residualSumOfSquaresForPartialDescriptions);
        }

        Map<Integer, Double> resultPartialDescription =
                getPartialDescriptionWithLowestNumberOfIndependentVariablesAndRss(
                        partialDescriptions, residualSumOfSquaresForPartialDescriptions);
        RealMatrix resultDesignMatrix =
                getPartialDescriptionDesignMatrix(resultPartialDescription, designMatrixWithAllIndependentVariables);

        return constructCoefficientsArrayForPartialDescription(resultPartialDescription,
                solveRegressionWithLeastSquaresMethod(resultDesignMatrix, meanOfAllDependentVariables),
                designMatrixWithAllIndependentVariables.getColumnDimension()).toArray();
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

    private List<Map<Integer, Double>> generatePartialDescriptions(
            Map<String, Map<Integer, Double>> clusters, RealVector meanOfDependentVariables, RealMatrix designMatrix) {
        List<Map<Integer, Double>> partialDescriptions =
                CombinationsGenerator.generateValuesCombinations(clusters.get(CLUSTER_M2_NAME));
        partialDescriptions.forEach(map -> map.putAll(clusters.get(CLUSTER_M1_NAME)));
        List<RealMatrix> partialDescriptionsDesignMatrices =
                getPartialDescriptionsDesignMatrices(partialDescriptions, designMatrix);
        updatePartialDescriptionsWithCorrectCoefficients(partialDescriptions, partialDescriptionsDesignMatrices,
                meanOfDependentVariables, designMatrix.getColumnDimension());
        return partialDescriptions;
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

    private RealVector filterPartialDescriptionsByPercentageFromMinRssAndReturnNewRssVector(
            List<Map<Integer, Double>> partialDescriptions, RealVector residualSumOfSquares) {
        RealVector newRssVector = new ArrayRealVector();
        double minimalRss = residualSumOfSquares.getMinValue();
        double minimalRssDeviation = percentageFromMinRss / 100;
        int partialDescriptionsCounter = 0;
        for (int i = 0; i < residualSumOfSquares.getDimension(); i++) {
            if (residualSumOfSquares.getEntry(i) > minimalRss + minimalRss * minimalRssDeviation) {
                partialDescriptions.remove(i - partialDescriptionsCounter);
                partialDescriptionsCounter++;
            } else {
                newRssVector = newRssVector.append(residualSumOfSquares.getEntry(i));
            }
        }

        return newRssVector;
    }

    private Map<Integer, Double> getPartialDescriptionWithLowestNumberOfIndependentVariablesAndRss(
            List<Map<Integer, Double>> partialDescriptions, RealVector residualSumOfSquares) {
        int minSize = partialDescriptions.stream()
                .map(Map::size)
                .min(Integer::compareTo)
                .get();

        double minRssValue = 0;
        int minRssValueIndex = 0;

        for (int i = 0; i < partialDescriptions.size(); i++) {
            if (partialDescriptions.get(i).size() == minSize) {
                minRssValue = residualSumOfSquares.getEntry(i);
                minRssValueIndex = i;
                break;
            }
        }

        for (int i = 0; i < partialDescriptions.size(); i++) {
            if (partialDescriptions.get(i).size() == minSize && minRssValue > residualSumOfSquares.getEntry(i)) {
                minRssValue = residualSumOfSquares.getEntry(i);
                minRssValueIndex = i;
            }
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result Partial description : {}", partialDescriptions.get(minRssValueIndex));
            LOGGER.debug("Result RSS: {}", residualSumOfSquares.getEntry(minRssValueIndex));
        }

        return partialDescriptions.get(minRssValueIndex);
    }

    private RealVector constructCoefficientsArrayForPartialDescription(
            Map<Integer, Double> partialDescription, RealVector coefficients, int numberOfAllCoefficients) {
        RealVector fullVectorOfCoefficients = new ArrayRealVector(numberOfAllCoefficients, 0);
        int nonNullCoefficientsCounter = 0;

        for (int i = 0; i < numberOfAllCoefficients; i++) {
            if (partialDescription.containsKey(i)) {
                fullVectorOfCoefficients.setEntry(i, coefficients.getEntry(nonNullCoefficientsCounter));
                nonNullCoefficientsCounter++;
            }
        }

        return fullVectorOfCoefficients;
    }

    private void displayClusters(Map<String, Map<Integer, Double>> clusters) {
        LOGGER.debug("Cluster M1: {}", clusters.get(CLUSTER_M1_NAME));
        LOGGER.debug("Cluster M2: {}", clusters.get(CLUSTER_M2_NAME));
    }

    private void displayPartialDescriptionsAndTheirRss(List<Map<Integer, Double>> partialDescriptions,
                                                       RealVector residualSumOfSquares) {
        for (int i = 0; i < partialDescriptions.size(); i++) {
            LOGGER.debug("Partial description {} : {}", i, partialDescriptions.get(i));
            LOGGER.debug("RSS {}: {}", i, residualSumOfSquares.getEntry(i));
        }
    }
}

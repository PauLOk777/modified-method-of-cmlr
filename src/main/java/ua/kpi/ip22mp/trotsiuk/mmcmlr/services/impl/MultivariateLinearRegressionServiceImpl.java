package ua.kpi.ip22mp.trotsiuk.mmcmlr.services.impl;

import org.apache.commons.math3.linear.AbstractRealMatrix;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.helpers.CombinationsGenerator;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.ClusterAnalysisService;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultivariateLinearRegressionService;

import java.util.List;
import java.util.Map;

import static org.apache.commons.math3.linear.MatrixUtils.inverse;
import static ua.kpi.ip22mp.trotsiuk.mmcmlr.ClusterAnalysisConstants.CLUSTER_M1_MODIFIED_METHOD;
import static ua.kpi.ip22mp.trotsiuk.mmcmlr.ClusterAnalysisConstants.CLUSTER_M2_MODIFIED_METHOD;

@Service
public class MultivariateLinearRegressionServiceImpl implements MultivariateLinearRegressionService {

    private static final double CLUSTER_THRESHOLD = 0.2;

    private final ClusterAnalysisService clusterAnalysisService;

    @Autowired
    public MultivariateLinearRegressionServiceImpl(ClusterAnalysisService clusterAnalysisService) {
        this.clusterAnalysisService = clusterAnalysisService;
    }

    @Override
    public double[] solveRegressionWithModifiedMethodOfCmlr(
            int experimentsNumber, int initialExperimentsNumber, double[][] independentVariables,
            double[] correctCoefficients, double[][] errors) {
        AbstractRealMatrix independentVariablesMatrix = new Array2DRowRealMatrix(independentVariables);
        AbstractRealMatrix errorsMatrix = new Array2DRowRealMatrix(errors);

        RealVector dependentVariables =
                calculateDependentVariables(independentVariablesMatrix, correctCoefficients);
        AbstractRealMatrix adjustedInitialDependentVariables =
                adjustDependentVariables(dependentVariables, errorsMatrix, 0, initialExperimentsNumber);
        AbstractRealMatrix adjustedValidationDependentVariables =
                adjustDependentVariables(dependentVariables, errorsMatrix, initialExperimentsNumber, experimentsNumber);
        AbstractRealMatrix designMatrixWithAllIndependentVariables = createDesignMatrix(independentVariablesMatrix);
        RealVector meanOfInitialDependentVariables = meanOfMatrixColumns(adjustedInitialDependentVariables);

        RealVector leastSquareMethodResults = solveRegressionWithLeastSquaresMethod(
                designMatrixWithAllIndependentVariables, meanOfInitialDependentVariables);

        List<Map<Integer, Double>> clusters = clusterAnalysisService.provideClustersByModifiedMethod(
                leastSquareMethodResults.toArray(), CLUSTER_THRESHOLD);

        List<Map<Integer, Double>> partialDescriptions =
                CombinationsGenerator.generateCombinations(clusters.get(CLUSTER_M2_MODIFIED_METHOD));
        partialDescriptions.forEach(map -> map.putAll(clusters.get(CLUSTER_M1_MODIFIED_METHOD)));

        return null;
    }

    private RealVector calculateDependentVariables(
            AbstractRealMatrix independentVariables, double[] correctCoefficients) {
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

    private AbstractRealMatrix adjustDependentVariables(
            RealVector dependentVariables, AbstractRealMatrix errors, int start, int end) {
        AbstractRealMatrix adjustedDependentVariables =
                new Array2DRowRealMatrix(dependentVariables.getDimension(), end - start);

        for (int i = 0; i < end - start; i++) {
            adjustedDependentVariables.setColumnVector(i, dependentVariables.add(errors.getColumnVector(start + i)));
        }

        return adjustedDependentVariables;
    }

    private AbstractRealMatrix createDesignMatrix(AbstractRealMatrix independentVariables) {
        AbstractRealMatrix designMatrix = new Array2DRowRealMatrix(
                independentVariables.getRowDimension(), independentVariables.getColumnDimension() + 1);
        designMatrix.setColumnVector(0, new ArrayRealVector(independentVariables.getRowDimension(), 1.0));
        designMatrix.setSubMatrix(independentVariables.getData(), 0, 1);
        return designMatrix;
    }

    private RealVector meanOfMatrixColumns(AbstractRealMatrix matrix) {
        RealVector meanVector = matrix.getColumnVector(0);

        for (int i = 1; i < matrix.getColumnDimension(); i++) {
            meanVector = meanVector.add(matrix.getColumnVector(i));
        }

        return meanVector.mapDivide(matrix.getColumnDimension());
    }

    private RealVector solveRegressionWithLeastSquaresMethod(
            AbstractRealMatrix designMatrix, RealVector meanOfDependentVariables) {
        return inverse(designMatrix.transpose().multiply(designMatrix))
                .operate(designMatrix.transpose().operate(meanOfDependentVariables));
    }
}

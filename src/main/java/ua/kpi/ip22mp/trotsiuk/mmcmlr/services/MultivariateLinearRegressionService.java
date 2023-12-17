package ua.kpi.ip22mp.trotsiuk.mmcmlr.services;

import org.apache.commons.math3.linear.RealMatrix;

public interface MultivariateLinearRegressionService {

    double[] solveRegressionWithModifiedMethodOfCmlr(
            RealMatrix dependentVariablesGroups, RealMatrix independentVariables,
            int repetitionsNumberOfActiveExperiments, int numberOfValidationSequences);
}

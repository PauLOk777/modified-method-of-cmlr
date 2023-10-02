package ua.kpi.ip22mp.trotsiuk.mmcmlr.services.impl;

import org.springframework.stereotype.Service;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultivariateLinearRegressionService;

@Service
public class MultivariateLinearRegressionServiceImpl implements MultivariateLinearRegressionService {

    @Override
    public double[] solveRegressionWithModifiedMethodOfCmlr(
            int experimentsNumber, int initialExperimentsNumber, int validationExperimentsNumber,
            double[][] independentVariables, double[] correctCoefficients, double[][] errorsMatrix) {
        return new double[0];
    }
}

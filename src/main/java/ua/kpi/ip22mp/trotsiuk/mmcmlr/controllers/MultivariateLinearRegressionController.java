package ua.kpi.ip22mp.trotsiuk.mmcmlr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.models.ModifiedMethodOfCmlrRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultivariateLinearRegressionService;

@RestController
@RequestMapping("/multivariate-linear-regression")
public class MultivariateLinearRegressionController {

    private static final String MODIFIED_METHOD_OF_CMLR_PAGE = "modifiedMethodOfCmlr";
    private static final String MODIFIED_METHOD_OF_CMLR_PAGE_WITH_IV = "modifiedMethodOfCmlrWithIv";

    private final MultivariateLinearRegressionService multivariateLinearRegressionService;

    @Autowired
    public MultivariateLinearRegressionController(MultivariateLinearRegressionService multivariateLinearRegressionService) {
        this.multivariateLinearRegressionService = multivariateLinearRegressionService;
    }

    @PostMapping("/modified-method-of-cmlr")
    public double[] solveRegressionWithModifiedMethodOfCmlr(@RequestBody ModifiedMethodOfCmlrRequestBody body) {
        return multivariateLinearRegressionService.solveRegressionWithModifiedMethodOfCmlr(
                body.experimentsNumber(), body.initialExperimentsNumber(), body.validationExperimentsNumber(),
                body.independentVariables(), body.correctCoefficients(), body.errorsMatrix()
        );
    }
}

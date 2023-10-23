package ua.kpi.ip22mp.trotsiuk.mmcmlr.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultivariateLinearRegressionService;

@RestController
@RequestMapping("/multivariate-linear-regression")
public class MultivariateLinearRegressionController {

    private static final String MODIFIED_METHOD_OF_CMLR_PAGE = "modifiedMethodOfCmlr";
    private static final String MODIFIED_METHOD_OF_CMLR_PAGE_WITH_IV = "modifiedMethodOfCmlrWithIv";

    private final MultivariateLinearRegressionService multivariateLinearRegressionService;
    private final Validator modifiedMethodOfCmlrRequestBodyValidator;

    @Autowired
    public MultivariateLinearRegressionController(
            MultivariateLinearRegressionService multivariateLinearRegressionService,
            @Qualifier("modifiedMethodOfCmlrRequestBodyValidator") Validator modifiedMethodOfCmlrRequestBodyValidator) {
        this.multivariateLinearRegressionService = multivariateLinearRegressionService;
        this.modifiedMethodOfCmlrRequestBodyValidator = modifiedMethodOfCmlrRequestBodyValidator;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(modifiedMethodOfCmlrRequestBodyValidator);
    }

    @PostMapping("/modified-method-of-cmlr")
    public double[] solveRegressionWithModifiedMethodOfCmlr(@Valid @RequestBody ModifiedMethodOfCmlrRequestBody body) {
        return multivariateLinearRegressionService.solveRegressionWithModifiedMethodOfCmlr(
                body.totalNumberOfExperimentsGroup(), body.initialNumberOfExperimentsGroup(), body.independentVariables(),
                body.correctCoefficients(), body.errors());
    }
}

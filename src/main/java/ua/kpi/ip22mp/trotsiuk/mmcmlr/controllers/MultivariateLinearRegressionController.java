package ua.kpi.ip22mp.trotsiuk.mmcmlr.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.dto.MultipleRunsOfRegressionCalculationDto;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.dto.RegressionCalculationDto;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.ModifiedMethodOfCmlrRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.NormallyDistributedRandomNumbersMMCMLRRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.RandomNumbersMMCMLRRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultipleRunsMultivariateLinearRegression;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultivariateLinearRegressionService;

@RestController
@RequestMapping("/multivariate-linear-regression")
public class MultivariateLinearRegressionController {

    private final MultivariateLinearRegressionService multivariateLinearRegressionService;
    private final MultipleRunsMultivariateLinearRegression multipleRunsMultivariateLinearRegression;
    private final Validator modifiedMethodOfCmlrRequestBodyValidator;

    @Autowired
    public MultivariateLinearRegressionController(
            MultivariateLinearRegressionService multivariateLinearRegressionService,
            MultipleRunsMultivariateLinearRegression multipleRunsMultivariateLinearRegression,
            @Qualifier("modifiedMethodOfCmlrRequestBodyValidator") Validator modifiedMethodOfCmlrRequestBodyValidator) {
        this.multivariateLinearRegressionService = multivariateLinearRegressionService;
        this.multipleRunsMultivariateLinearRegression = multipleRunsMultivariateLinearRegression;
        this.modifiedMethodOfCmlrRequestBodyValidator = modifiedMethodOfCmlrRequestBodyValidator;
    }

    @PostMapping("/modified-method-of-cmlr")
    public RegressionCalculationDto solveRegressionWithModifiedMethodOfCmlr(@Valid @RequestBody ModifiedMethodOfCmlrRequestBody body,
                                                                            BindingResult bindingResult) throws BindException {
        modifiedMethodOfCmlrRequestBodyValidator.validate(body, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return multivariateLinearRegressionService.solveRegressionWithModifiedMethodOfCmlr(
                body.repetitionsNumberOfActiveExperiments(), body.numberOfValidationSequences(), body.independentVariables(),
                body.correctCoefficients(), body.errors());
    }

    @PostMapping("/modified-method-of-cmlr/multiple-runs/random-numbers")
    public MultipleRunsOfRegressionCalculationDto solveMultipleTimesWithRandomNumbersErrorsRegressionWithModifiedMethodOfCmlr(
            @Valid @RequestBody RandomNumbersMMCMLRRequestBody body) {
        // TODO add validator
        return multipleRunsMultivariateLinearRegression.multipleRunsOfModifiedMethodOfCmlrWithRandomNumbers(
                body.repetitionsNumberOfActiveExperiments(), body.numberOfValidationSequences(), body.numberOfRuns(),
                body.correctCoefficients(), body.independentVariables(), body.range().start(), body.range().end()
        );
    }

    @PostMapping("/modified-method-of-cmlr/multiple-runs/normally-distributed-random-numbers")
    public MultipleRunsOfRegressionCalculationDto solveMultipleTimesWithNormallyDistributedRandomNumbersErrorsRegressionWithModifiedMethodOfCmlr(
            @Valid @RequestBody NormallyDistributedRandomNumbersMMCMLRRequestBody body) {
        // TODO add validator
        return multipleRunsMultivariateLinearRegression.multipleRunsOfModifiedMethodOfCmlrWithNormallyDistributedRandomNumbers(
                body.repetitionsNumberOfActiveExperiments(), body.numberOfValidationSequences(), body.numberOfRuns(),
                body.correctCoefficients(), body.independentVariables(), body.mean(), body.stdDev()
        );
    }
}

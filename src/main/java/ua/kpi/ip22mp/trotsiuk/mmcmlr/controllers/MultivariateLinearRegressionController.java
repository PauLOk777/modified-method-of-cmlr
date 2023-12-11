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
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.NormallyDistributedRandomNumbersMmcmlrRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.RandomNumbersMmcmlrRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultipleRunsMultivariateLinearRegressionService;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultivariateLinearRegressionService;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.validators.NormallyDistributedRandomNumbersMmcmlrRequestBodyValidator;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.validators.RandomNumbersMmcmlrRequestBodyValidator;

@RestController
@RequestMapping("/multivariate-linear-regression")
public class MultivariateLinearRegressionController {

    private final MultivariateLinearRegressionService multivariateLinearRegressionService;
    private final MultipleRunsMultivariateLinearRegressionService multipleRunsMultivariateLinearRegressionService;
    private final Validator modifiedMethodOfCmlrRequestBodyValidator;
    private final Validator normallyDistributedRandomNumbersMmcmlrRequestBodyValidator;
    private final Validator randomNumbersMmcmlrRequestBodyValidator;

    @Autowired
    public MultivariateLinearRegressionController(
            MultivariateLinearRegressionService multivariateLinearRegressionService,
            MultipleRunsMultivariateLinearRegressionService multipleRunsMultivariateLinearRegressionService,
            @Qualifier("modifiedMethodOfCmlrRequestBodyValidator") Validator modifiedMethodOfCmlrRequestBodyValidator,
            @Qualifier("normallyDistributedRandomNumbersMmcmlrRequestBodyValidator")
            NormallyDistributedRandomNumbersMmcmlrRequestBodyValidator normallyDistributedRandomNumbersMmcmlrRequestBodyValidator,
            @Qualifier("randomNumbersMmcmlrRequestBodyValidator")
            RandomNumbersMmcmlrRequestBodyValidator randomNumbersMmcmlrRequestBodyValidator) {
        this.multivariateLinearRegressionService = multivariateLinearRegressionService;
        this.multipleRunsMultivariateLinearRegressionService = multipleRunsMultivariateLinearRegressionService;
        this.modifiedMethodOfCmlrRequestBodyValidator = modifiedMethodOfCmlrRequestBodyValidator;
        this.normallyDistributedRandomNumbersMmcmlrRequestBodyValidator =
                normallyDistributedRandomNumbersMmcmlrRequestBodyValidator;
        this.randomNumbersMmcmlrRequestBodyValidator = randomNumbersMmcmlrRequestBodyValidator;
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
            @Valid @RequestBody RandomNumbersMmcmlrRequestBody body, BindingResult bindingResult) throws BindException {
        randomNumbersMmcmlrRequestBodyValidator.validate(body, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return multipleRunsMultivariateLinearRegressionService.multipleRunsOfModifiedMethodOfCmlrWithRandomNumbers(
                body.repetitionsNumberOfActiveExperiments(), body.numberOfValidationSequences(), body.numberOfRuns(),
                body.correctCoefficients(), body.independentVariables(), body.range().start(), body.range().end()
        );
    }

    @PostMapping("/modified-method-of-cmlr/multiple-runs/normally-distributed-random-numbers")
    public MultipleRunsOfRegressionCalculationDto solveMultipleTimesWithNormallyDistributedRandomNumbersErrorsRegressionWithModifiedMethodOfCmlr(
            @Valid @RequestBody NormallyDistributedRandomNumbersMmcmlrRequestBody body, BindingResult bindingResult) throws BindException {
        normallyDistributedRandomNumbersMmcmlrRequestBodyValidator.validate(body, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return multipleRunsMultivariateLinearRegressionService.multipleRunsOfModifiedMethodOfCmlrWithNormallyDistributedRandomNumbers(
                body.repetitionsNumberOfActiveExperiments(), body.numberOfValidationSequences(), body.numberOfRuns(),
                body.correctCoefficients(), body.independentVariables(), body.mean(), body.stdDev()
        );
    }
}

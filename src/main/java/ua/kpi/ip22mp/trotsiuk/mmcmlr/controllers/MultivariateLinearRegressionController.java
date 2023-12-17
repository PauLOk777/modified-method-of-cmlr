package ua.kpi.ip22mp.trotsiuk.mmcmlr.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.dto.RegressionCalculationStatisticsDto;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.NormallyDistributedRandomNumbersMmcmlrRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.UniformlyDistributedRandomNumbersMmcmlrRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.MultivariateLinearRegressionSimulatorService;

@RestController
@RequestMapping("/multivariate-linear-regression")
public class MultivariateLinearRegressionController {

    private final MultivariateLinearRegressionSimulatorService multivariateLinearRegressionSimulatorService;
    private final Validator normallyDistributedRandomNumbersMmcmlrRequestBodyValidator;
    private final Validator uniformlyDistributedRandomNumbersMmcmlrRequestBodyValidator;

    public MultivariateLinearRegressionController(
            MultivariateLinearRegressionSimulatorService multivariateLinearRegressionSimulatorService,
            @Qualifier("normallyDistributedRandomNumbersMmcmlrRequestBodyValidator")
            Validator normallyDistributedRandomNumbersMmcmlrRequestBodyValidator,
            @Qualifier("uniformlyDistributedRandomNumbersMmcmlrRequestBodyValidator")
            Validator uniformlyDistributedRandomNumbersMmcmlrRequestBodyValidator) {
        this.multivariateLinearRegressionSimulatorService = multivariateLinearRegressionSimulatorService;
        this.normallyDistributedRandomNumbersMmcmlrRequestBodyValidator =
                normallyDistributedRandomNumbersMmcmlrRequestBodyValidator;
        this.uniformlyDistributedRandomNumbersMmcmlrRequestBodyValidator = uniformlyDistributedRandomNumbersMmcmlrRequestBodyValidator;
    }


    @PostMapping("/modified-method-of-cmlr/uniformly-distributed-random-numbers")
    public RegressionCalculationStatisticsDto solveWithModifiedMethodOfCmlrForUniformlyDistributedRandomNumbersForRandomVariables(
            @Valid @RequestBody UniformlyDistributedRandomNumbersMmcmlrRequestBody body, BindingResult bindingResult) throws BindException {
        uniformlyDistributedRandomNumbersMmcmlrRequestBodyValidator.validate(body, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return multivariateLinearRegressionSimulatorService
                .simulateMultipleTimesModifiedMethodOfCmlrWithUniformlyDistributedRandomNumbers(
                        body.repetitionsNumberOfActiveExperiments(), body.numberOfValidationSequences(), body.numberOfRuns(),
                        body.correctCoefficients(), body.independentVariables(), body.range().start(), body.range().end()
        );
    }

    @PostMapping("/modified-method-of-cmlr/normally-distributed-random-numbers")
    public RegressionCalculationStatisticsDto solveWithModifiedMethodOfCmlrForNormallyDistributedRandomNumbersForRandomVariables(
            @Valid @RequestBody NormallyDistributedRandomNumbersMmcmlrRequestBody body, BindingResult bindingResult) throws BindException {
        normallyDistributedRandomNumbersMmcmlrRequestBodyValidator.validate(body, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return multivariateLinearRegressionSimulatorService
                .simulateMultipleTimesModifiedMethodOfCmlrWithNormallyDistributedRandomNumbers(
                        body.repetitionsNumberOfActiveExperiments(), body.numberOfValidationSequences(), body.numberOfRuns(),
                        body.correctCoefficients(), body.independentVariables(), body.mean(), body.stdDev()
        );
    }
}

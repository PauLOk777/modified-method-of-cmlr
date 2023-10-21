package ua.kpi.ip22mp.trotsiuk.mmcmlr.binders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.validators.ModifiedMethodOfCmlrRequestBodyValidator;

@ControllerAdvice("ua.kpi.ip22mp.trotsiuk.mmcmlr.controllers.MultivariateLinearRegressionController")
public class MultivariateLinearRegressionDataBinder {

    private final Validator modifiedMethodOfCmlrRequestBodyValidator;

    @Autowired
    public MultivariateLinearRegressionDataBinder(ModifiedMethodOfCmlrRequestBodyValidator modifiedMethodOfCmlrRequestBodyValidator) {
        this.modifiedMethodOfCmlrRequestBodyValidator = modifiedMethodOfCmlrRequestBodyValidator;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(modifiedMethodOfCmlrRequestBodyValidator);
    }
}

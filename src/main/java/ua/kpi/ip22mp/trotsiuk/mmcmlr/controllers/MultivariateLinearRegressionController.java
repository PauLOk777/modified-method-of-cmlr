package ua.kpi.ip22mp.trotsiuk.mmcmlr.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.models.ModifiedMethodOfCmlrRequestBody;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/modified-method-of-cmlr")
public class MultivariateLinearRegressionController {

    private static final String MODIFIED_METHOD_OF_CMLR_PAGE = "modifiedMethodOfCmlr";
    private static final String MODIFIED_METHOD_OF_CMLR_PAGE_WITH_IV = "modifiedMethodOfCmlrWithIv";

    @PostMapping
    public List<Double> solveRegressionWithModifiedMethodOfCmlr(@RequestBody ModifiedMethodOfCmlrRequestBody body) {
        return new ArrayList<>();
    }
}

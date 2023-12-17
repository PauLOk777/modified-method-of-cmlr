package ua.kpi.ip22mp.trotsiuk.mmcmlr.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.UniformlyDistributedIntRandomNumbersRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.UniformlyDistributedDoubleRandomNumbersRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.RandomNumbersService;

@RestController
@RequestMapping("/random-numbers")
public class RandomNumbersController {

    private final RandomNumbersService randomNumbersService;

    public RandomNumbersController(RandomNumbersService randomNumbersService) {
        this.randomNumbersService = randomNumbersService;
    }

    @PostMapping("/uniformly-distribution/int/matrix")
    public int[][] getMatrixOfRandomIntNumbers(@Valid @RequestBody UniformlyDistributedIntRandomNumbersRequestBody body) {
        return randomNumbersService.generateMatrixOfUniformlyDistributedIntRandomNumbers(
                body.rows(), body.columns(), body.range().start(), body.range().end());
    }

    @PostMapping("/uniformly-distribution/double/matrix")
    public double[][] getMatrixOfRandomDoubleNumbers(@Valid @RequestBody UniformlyDistributedDoubleRandomNumbersRequestBody body) {
        return randomNumbersService.generateMatrixOfUniformlyDistributedDoubleRandomNumbers(
                body.rows(), body.columns(), body.range().start(), body.range().end());
    }
}

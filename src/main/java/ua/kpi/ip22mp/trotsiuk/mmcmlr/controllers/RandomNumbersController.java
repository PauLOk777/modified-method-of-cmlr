package ua.kpi.ip22mp.trotsiuk.mmcmlr.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.IntRandomNumbersRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.MatrixOfNormallyDistributedRandomNumbersRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.DoubleRandomNumbersRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.RandomNumbersService;

@RestController
@RequestMapping("/random-numbers")
public class RandomNumbersController {

    private final RandomNumbersService randomNumbersService;

    @Autowired
    public RandomNumbersController(RandomNumbersService randomNumbersService) {
        this.randomNumbersService = randomNumbersService;
    }

    @PostMapping("/int/matrix")
    public int[][] getRandomIntNumbers(@Valid @RequestBody IntRandomNumbersRequestBody body) {
        return randomNumbersService.generateRandomIntNumbersInMatrix(
                body.rows(), body.columns(), body.range().start(), body.range().end());
    }

    @PostMapping("/double/matrix")
    public double[][] getRandomDoubleNumbers(@Valid @RequestBody DoubleRandomNumbersRequestBody body) {
        return randomNumbersService.generateRandomDoubleNumbersInMatrix(
                body.rows(), body.columns(), body.range().start(), body.range().end());
    }

    @PostMapping("/normal-distribution/matrix")
    public double[][] getMatrixOfNormallyDistributedRandomNumbers(
            @Valid @RequestBody MatrixOfNormallyDistributedRandomNumbersRequestBody body) {
        return randomNumbersService.generateMatrixOfNormallyDistributedRandomNumbers(
                body.mean(), body.stdDev(), body.rows(), body.columns());
    }
}

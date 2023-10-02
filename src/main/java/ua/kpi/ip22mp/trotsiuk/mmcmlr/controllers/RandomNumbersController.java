package ua.kpi.ip22mp.trotsiuk.mmcmlr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.models.NormallyDistributedRandomNumbersRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.models.RandomNumbersRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.RandomNumbersService;

@RestController
@RequestMapping("/random-numbers")
public class RandomNumbersController {

    private final RandomNumbersService randomNumbersService;

    @Autowired
    public RandomNumbersController(RandomNumbersService randomNumbersService) {
        this.randomNumbersService = randomNumbersService;
    }

    @PostMapping("/matrix")
    public double[][] getRandomNumbers(@RequestBody RandomNumbersRequestBody body) {
        return randomNumbersService.generateRandomNumbersInMatrix(
                body.rows(), body.columns(), body.begin(), body.end());
    }

    @PostMapping("/normal-distribution")
    public double[] getNormallyDistributedRandomNumbers(
            @RequestBody NormallyDistributedRandomNumbersRequestBody body) {
        return randomNumbersService.generateNormallyDistributedRandomNumbers(
                body.mean(), body.stdDev(), body.numbersAmount());
    }
}

package ua.kpi.ip22mp.trotsiuk.mmcmlr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.models.NormallyDistributedRandomNumbersRequestBody;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.RandomNumbersService;

import java.util.List;

@RestController
@RequestMapping("/random-numbers")
public class RandomNumbersController {

    private RandomNumbersService randomNumbersService;

    @Autowired
    public RandomNumbersController(RandomNumbersService randomNumbersService) {
        this.randomNumbersService = randomNumbersService;
    }

    @PostMapping("/normal-distribution")
    public List<Double> getNormallyDistributedRandomNumbers(
            @RequestBody NormallyDistributedRandomNumbersRequestBody body) {
        return randomNumbersService.generateNormallyDistributedRandomNumbers(
                body.mean(), body.stdDev(), body.numbersAmount());
    }
}

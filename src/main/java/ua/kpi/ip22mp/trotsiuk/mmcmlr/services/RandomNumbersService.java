package ua.kpi.ip22mp.trotsiuk.mmcmlr.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;

import static java.util.stream.Collectors.toList;

@Service
public class RandomNumbersService {

    private final Random random = new Random();

    public List<Double> generateNormallyDistributedRandomNumbers(double mean, double stdDev, int amountOfNumbers) {
        return DoubleStream.generate(() -> random.nextGaussian(mean, stdDev))
                .limit(amountOfNumbers)
                .boxed()
                .collect(toList());
    }
}

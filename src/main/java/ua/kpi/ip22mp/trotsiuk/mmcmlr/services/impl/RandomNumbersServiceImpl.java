package ua.kpi.ip22mp.trotsiuk.mmcmlr.services.impl;

import org.springframework.stereotype.Service;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.RandomNumbersService;

import java.util.Random;
import java.util.stream.Stream;

@Service
public class RandomNumbersServiceImpl implements RandomNumbersService {

    private final Random random = new Random();

    public int[][] generateRandomIntNumbersInMatrix(int rows, int columns, int start, int end) {
        return Stream.generate(() -> random.ints(columns, start, end).toArray())
                .limit(rows)
                .toArray(int[][]::new);
    }

    public double[][] generateRandomDoubleNumbersInMatrix(int rows, int columns, double start, double end) {
        return Stream.generate(() -> random.doubles(columns, start, end).toArray())
                .limit(rows)
                .toArray(double[][]::new);
    }

    public double[][] generateMatrixOfNormallyDistributedRandomNumbers(double mean, double stdDev, int rows, int columns) {
        double[][] matrix = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = random.nextGaussian(mean, stdDev);
            }
        }

        return matrix;
    }
}

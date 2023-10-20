package ua.kpi.ip22mp.trotsiuk.mmcmlr.services;

public interface RandomNumbersService {

    int[][] generateRandomIntNumbersInMatrix(int rows, int columns, int begin, int end);

    double[][] generateRandomDoubleNumbersInMatrix(int rows, int columns, double begin, double end);

    double[][] generateMatrixOfNormallyDistributedRandomNumbers(double mean, double stdDev, int rows, int columns);
}

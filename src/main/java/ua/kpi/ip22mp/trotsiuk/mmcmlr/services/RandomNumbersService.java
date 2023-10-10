package ua.kpi.ip22mp.trotsiuk.mmcmlr.services;

public interface RandomNumbersService {

    double[][] generateRandomNumbersInMatrix(int rows, int columns, int begin, int end);

    double[][] generateMatrixOfNormallyDistributedRandomNumbers(double mean, double stdDev, int rows, int columns);
}

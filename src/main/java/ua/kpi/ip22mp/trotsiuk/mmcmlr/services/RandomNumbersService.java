package ua.kpi.ip22mp.trotsiuk.mmcmlr.services;

public interface RandomNumbersService {

    int[][] generateMatrixOfUniformlyDistributedIntRandomNumbers(int rows, int columns, int start, int end);

    double[][] generateMatrixOfUniformlyDistributedDoubleRandomNumbers(int rows, int columns, double start, double end);

    double[][] generateMatrixOfNormallyDistributedRandomNumbers(double mean, double stdDev, int rows, int columns);
}

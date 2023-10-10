package ua.kpi.ip22mp.trotsiuk.mmcmlr.models;

public record MatrixOfNormallyDistributedRandomNumbersRequestBody(double mean, double stdDev, int rows, int columns) {}

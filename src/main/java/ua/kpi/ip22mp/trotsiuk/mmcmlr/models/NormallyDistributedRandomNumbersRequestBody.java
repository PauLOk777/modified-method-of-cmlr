package ua.kpi.ip22mp.trotsiuk.mmcmlr.models;

public record NormallyDistributedRandomNumbersRequestBody(double mean, double stdDev, int numbersAmount) {}

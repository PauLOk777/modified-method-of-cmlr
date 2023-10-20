package ua.kpi.ip22mp.trotsiuk.mmcmlr.requests;

import jakarta.validation.constraints.Min;

public record MatrixOfNormallyDistributedRandomNumbersRequestBody(double mean, @Min(0) double stdDev,
                                                                  @Min(1) int rows, @Min(1) int columns) {}

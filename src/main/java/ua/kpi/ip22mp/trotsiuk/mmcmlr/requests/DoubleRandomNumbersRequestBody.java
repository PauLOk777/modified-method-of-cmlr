package ua.kpi.ip22mp.trotsiuk.mmcmlr.requests;

import jakarta.validation.constraints.Min;

public record DoubleRandomNumbersRequestBody(@Min(1) int rows, @Min(1) int columns, double begin, double end) {}

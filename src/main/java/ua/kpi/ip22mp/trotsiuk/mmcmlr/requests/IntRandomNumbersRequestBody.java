package ua.kpi.ip22mp.trotsiuk.mmcmlr.requests;

import jakarta.validation.constraints.Min;

public record IntRandomNumbersRequestBody(@Min(1) int rows, @Min(1) int columns, int begin, int end) {}

package ua.kpi.ip22mp.trotsiuk.mmcmlr.requests;

import jakarta.validation.constraints.Min;

public record MatrixOfNormallyDistributedRandomNumbersRequestBody(
        double mean,
        @Min(value = 0, message =
                "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.MatrixOfNormallyDistributedRandomNumbersRequestBody.stdDev.Min}")
        double stdDev,
        @Min(value = 1, message =
                "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.MatrixOfNormallyDistributedRandomNumbersRequestBody.rows.Min}")
        int rows,
        @Min(value = 1, message =
                "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.MatrixOfNormallyDistributedRandomNumbersRequestBody.columns.Min}")
        int columns) {}

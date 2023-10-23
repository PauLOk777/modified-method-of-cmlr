package ua.kpi.ip22mp.trotsiuk.mmcmlr.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record MatrixOfNormallyDistributedRandomNumbersRequestBody(
        double mean,
        @Min(value = 0, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.MatrixOfNormallyDistributedRandomNumbersRequestBody.stdDev.Min}")
        double stdDev,
        @Min(value = 1, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.MatrixOfNormallyDistributedRandomNumbersRequestBody.rows.Min}")
        @Max(value = 1000, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.MatrixOfNormallyDistributedRandomNumbersRequestBody.rows.Max}")
        int rows,
        @Min(value = 1, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.MatrixOfNormallyDistributedRandomNumbersRequestBody.columns.Min}")
        @Max(value = 1000, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.MatrixOfNormallyDistributedRandomNumbersRequestBody.columns.Max}")
        int columns) {}

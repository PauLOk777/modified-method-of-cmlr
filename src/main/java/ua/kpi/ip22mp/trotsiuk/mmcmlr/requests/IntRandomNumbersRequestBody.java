package ua.kpi.ip22mp.trotsiuk.mmcmlr.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.util.IntRange;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.IntRealRange;

public record IntRandomNumbersRequestBody(
        @Min(value = 1, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.IntRandomNumbersRequestBody.rows.Min}")
        @Max(value = 1000, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.IntRandomNumbersRequestBody.rows.Max}")
        int rows,
        @Min(value = 1, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.IntRandomNumbersRequestBody.columns.Min}")
        @Max(value = 1000, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.IntRandomNumbersRequestBody.columns.Max}")
        int columns,
        @NotNull(message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.IntRandomNumbersRequestBody.range.NotNull}")
        @IntRealRange IntRange range) {}

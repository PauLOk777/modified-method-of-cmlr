package ua.kpi.ip22mp.trotsiuk.mmcmlr.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.util.DoubleRange;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.DoubleRealRange;

public record DoubleRandomNumbersRequestBody(
        @Min(value = 1, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.DoubleRandomNumbersRequestBody.rows.Min}")
        int rows,
        @Min(value = 1, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.DoubleRandomNumbersRequestBody.columns.Min}")
        int columns,
        @NotNull(message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.DoubleRandomNumbersRequestBody.range.NotNull}")
        @DoubleRealRange DoubleRange range) {}

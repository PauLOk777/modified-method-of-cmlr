package ua.kpi.ip22mp.trotsiuk.mmcmlr.requests;

import jakarta.validation.constraints.Min;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.DoubleRealRange;

@DoubleRealRange
public record DoubleRandomNumbersRequestBody(
        @Min(value = 1, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.DoubleRandomNumbersRequestBody.rows.Min}")
        int rows,
        @Min(value = 1, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.DoubleRandomNumbersRequestBody.columns.Min}")
        int columns,
        double begin, double end) {}

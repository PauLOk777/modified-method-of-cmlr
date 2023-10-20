package ua.kpi.ip22mp.trotsiuk.mmcmlr.requests;

import jakarta.validation.constraints.Min;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.IntRealRange;

@IntRealRange
public record IntRandomNumbersRequestBody(
        @Min(value = 1, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.IntRandomNumbersRequestBody.rows.Min}")
        int rows,
        @Min(value = 1, message = "{ua.kpi.ip22mp.trotsiuk.mmcmlr.requests.IntRandomNumbersRequestBody.columns.Min}")
        int columns,
        int begin, int end) {}

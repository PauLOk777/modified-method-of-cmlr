package ua.kpi.ip22mp.trotsiuk.mmcmlr.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.util.IntRange;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.validation.constraints.IntRealRange;

public record UniformlyDistributedIntRandomNumbersRequestBody(
        @Min(value = 1, message = "{rows.Min}")
        @Max(value = 1000, message = "{rows.Max}")
        int rows,
        @Min(value = 1, message = "{columns.Min}")
        @Max(value = 1000, message = "{columns.Max}")
        int columns,
        @NotNull(message = "{range.NotNull}")
        @IntRealRange IntRange range) {}

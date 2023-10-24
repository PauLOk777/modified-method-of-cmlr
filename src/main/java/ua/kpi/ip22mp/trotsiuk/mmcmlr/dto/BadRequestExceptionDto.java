package ua.kpi.ip22mp.trotsiuk.mmcmlr.dto;

import java.util.List;

public record BadRequestExceptionDto(List<String> globalErrors, List<String> fieldErrors) {}

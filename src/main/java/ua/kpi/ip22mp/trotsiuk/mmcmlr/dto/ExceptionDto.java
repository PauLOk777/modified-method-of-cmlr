package ua.kpi.ip22mp.trotsiuk.mmcmlr.dto;

import java.util.List;

public record ExceptionDto(List<String> globalErrors, List<String> fieldErrors) {}

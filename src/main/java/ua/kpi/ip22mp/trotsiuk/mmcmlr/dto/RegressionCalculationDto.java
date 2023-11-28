package ua.kpi.ip22mp.trotsiuk.mmcmlr.dto;

public record RegressionCalculationDto(double[] coefficients, double comparisonValueByPavlov,
                                       int numberOfInitialZeroCoefficients, int numberOfCalculatedZeroCoefficients,
                                       int numberOfMatchingWithCorrectCoefficientsZeros) {}

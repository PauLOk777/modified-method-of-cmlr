package ua.kpi.ip22mp.trotsiuk.mmcmlr.dto;

public record MultipleRunsOfRegressionCalculationDto (
        double percentageOfCorrectModels, double percentageOfIncorrectModelsWithOneIncorrectZero,
        double percentageOfIncorrectModelsWithTwoPlusIncorrectZeros
) {}

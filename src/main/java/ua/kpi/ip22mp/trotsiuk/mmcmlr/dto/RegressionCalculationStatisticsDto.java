package ua.kpi.ip22mp.trotsiuk.mmcmlr.dto;

public record RegressionCalculationStatisticsDto(
        double percentageOfCorrectModels, double percentageOfIncorrectModelsWithOneIncorrectZero,
        double percentageOfIncorrectModelsWithTwoPlusIncorrectZeros, double meanComparisonMeasureValueForCorrectModels,
        double minComparisonMeasureValueForCorrectModels, double maxComparisonMeasureValueForCorrectModels
) {}

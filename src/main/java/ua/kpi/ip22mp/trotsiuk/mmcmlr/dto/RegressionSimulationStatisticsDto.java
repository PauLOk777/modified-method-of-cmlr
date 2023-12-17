package ua.kpi.ip22mp.trotsiuk.mmcmlr.dto;

public record RegressionSimulationStatisticsDto(
        double percentageOfCorrectModels, double percentageOfIncorrectModelsWithOneIncorrectZero,
        double percentageOfIncorrectModelsWithTwoPlusIncorrectZeros, double meanComparisonMeasureValueForCorrectModels,
        double minComparisonMeasureValueForCorrectModels, double maxComparisonMeasureValueForCorrectModels
) {}

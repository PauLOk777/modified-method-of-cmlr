package ua.kpi.ip22mp.trotsiuk.mmcmlr.services;

import java.util.Map;

public interface ClusterAnalysisService {

    Map<String, Map<Integer, Double>> provideClustersByModifiedMethod(double[] coefficients);
}

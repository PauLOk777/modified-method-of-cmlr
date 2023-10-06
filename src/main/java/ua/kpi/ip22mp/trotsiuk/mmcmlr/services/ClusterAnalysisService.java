package ua.kpi.ip22mp.trotsiuk.mmcmlr.services;

import java.util.List;
import java.util.Map;

public interface ClusterAnalysisService {

    List<Map<Integer, Double>> provideClustersByModifiedMethod(double[] coefficients, double threshold);
}

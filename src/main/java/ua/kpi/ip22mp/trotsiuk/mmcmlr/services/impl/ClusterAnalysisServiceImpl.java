package ua.kpi.ip22mp.trotsiuk.mmcmlr.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.kpi.ip22mp.trotsiuk.mmcmlr.services.ClusterAnalysisService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SequencedMap;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.toMap;

@Service
public class ClusterAnalysisServiceImpl implements ClusterAnalysisService {

    @Value("${max.number.of.elements.in.m2.cluster}")
    private int maxNumberOfElementsInM2Cluster;

    @Override
    public List<Map<Integer, Double>> provideClustersByModifiedMethod(double[] coefficients) {
        SequencedMap<Integer, Double> coefficientsSortedByModule =
                sortCoefficientsMapByModule(createMapWithIncrementKeys(coefficients));

        Map<Integer, Double> m1 = new HashMap<>();
        Map.Entry<Integer, Double> firstEntry = coefficientsSortedByModule.pollFirstEntry();
        m1.put(firstEntry.getKey(), firstEntry.getValue());

        Map<Integer, Double> m2 = new HashMap<>();
        Map.Entry<Integer, Double> lastEntry = coefficientsSortedByModule.pollLastEntry();
        m2.put(lastEntry.getKey(), lastEntry.getValue());

        while (!coefficientsSortedByModule.isEmpty()) {
            double averageAbsoluteValueOfM1 = m1.values().stream().mapToDouble(Math::abs).average().orElse(0);
            Map.Entry<Integer, Double> entry = coefficientsSortedByModule.pollFirstEntry();
            if (averageAbsoluteValueOfM1 - Math.abs(entry.getValue()) >= Math.abs(entry.getValue()) - Math.abs(lastEntry.getValue())) {
                m2.put(entry.getKey(), entry.getValue());
                m2.putAll(coefficientsSortedByModule);
                break;
            }

            m1.put(entry.getKey(), entry.getValue());
        }

        if (m2.size() > maxNumberOfElementsInM2Cluster) {
            m2 = m2.entrySet().stream()
                    .sorted(comparingDouble(entry -> -Math.abs(entry.getValue())))
                    .limit(maxNumberOfElementsInM2Cluster)
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, HashMap::new));
        }

        return List.of(m1, m2);
    }

    private Map<Integer, Double> createMapWithIncrementKeys(double[] array) {
        Map<Integer, Double> map = new HashMap<>();

        for (int i = 0; i < array.length; i++) {
            map.put(i, array[i]);
        }

        return map;
    }

    private SequencedMap<Integer, Double> sortCoefficientsMapByModule(Map<Integer, Double> coefficients) {
        return coefficients.entrySet()
                .stream()
                .sorted(comparingDouble(entry -> -Math.abs(entry.getValue())))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}

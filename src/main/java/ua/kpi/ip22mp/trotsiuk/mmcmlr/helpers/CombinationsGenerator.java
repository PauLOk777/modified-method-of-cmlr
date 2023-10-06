package ua.kpi.ip22mp.trotsiuk.mmcmlr.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombinationsGenerator {

    public static <K, V> List<Map<K, V>> generateCombinations(Map<K, V> map) {
        List<Map<K, V>> combinations = new ArrayList<>();
        List<K> keys = new ArrayList<>(map.keySet());

        int totalCombinations = (int) Math.pow(2, keys.size());

        for (int i = 0; i < totalCombinations; i++) {
            Map<K, V> combination = new HashMap<>();
            for (int j = 0; j < keys.size(); j++) {
                if ((i & (1 << j)) != 0) {
                    K key = keys.get(j);
                    V value = map.get(key);
                    combination.put(key, value);
                }
            }
            combinations.add(combination);
        }

        return combinations;
    }
}

package edu.hw3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task3 {
    private Task3() {

    }

    public static <T> Map<T, Integer> freqDict(List<T> inList) {
        Map<T, Integer> frequencyDict = new HashMap<>();
        for (T item : inList) {
            frequencyDict.put(item, frequencyDict.getOrDefault(item, 0) + 1);
        }
        return frequencyDict;
    }
}

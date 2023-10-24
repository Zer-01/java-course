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
            if (frequencyDict.containsKey(item)) {
                frequencyDict.put(item, frequencyDict.get(item) + 1);
            } else {
                frequencyDict.put(item, 1);
            }
        }
        return frequencyDict;
    }
}

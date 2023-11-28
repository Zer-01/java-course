package edu.hw6.Task1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;

public class DiskMap implements Map<String, String> {
    private static final String KEY_VALUE_SEPARATOR = ":";
    private final String path;
    private Map<String, String> inMemMap;

    public DiskMap(String path) {
        this.path = path;
        inMemMap = new HashMap<>();
    }

    public void loadFromFile() {
        Map<String, String> newMap = null;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            newMap = br.lines()
                .map(line -> line.split(KEY_VALUE_SEPARATOR))
                .collect(Collectors.toMap(
                    arr -> arr[0],
                    arr -> arr[1]
                ));
        } catch (IOException e) {
            LogManager.getLogger().error(e);
        }
        inMemMap = newMap;
    }

    public void saveToFile() {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(path))) {
            for (var entry : inMemMap.entrySet()) {
                br.write(entry.getKey() + KEY_VALUE_SEPARATOR + entry.getValue());
                br.newLine();
            }
        } catch (IOException e) {
            LogManager.getLogger().error(e);
        }
    }

    @Override
    public int size() {
        return inMemMap.size();
    }

    @Override
    public boolean isEmpty() {
        return inMemMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return inMemMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return inMemMap.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return inMemMap.get(key);
    }

    @Override
    public String put(String key, String value) {
        String oldValue = inMemMap.put(key, value);
        saveToFile();
        return  oldValue;
    }

    @Override
    public String remove(Object key) {
        String removedValue = inMemMap.remove(key);
        saveToFile();
        return removedValue;
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        inMemMap.putAll(m);
        saveToFile();
    }

    @Override
    public void clear() {
        inMemMap.clear();
        saveToFile();
    }

    @Override
    public @NotNull Set<String> keySet() {
        return inMemMap.keySet();
    }

    @Override
    public @NotNull Collection<String> values() {
        return inMemMap.values();
    }

    @Override
    public @NotNull Set<Map.Entry<String, String>> entrySet() {
        return inMemMap.entrySet();
    }
}

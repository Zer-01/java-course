package edu.hw6;

import edu.hw6.Task1.DiskMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    static Path tmpFile;

    @BeforeAll
    public static void init() throws IOException {
        tmpFile = Files.createTempFile("Tsk1Tst", null);
    }

    @Test
    void addTest() {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("Key1", "Val1");
        testMap.put("Key2", "Val2");
        testMap.put("Key3", "Val3");

        DiskMap diskMap = new DiskMap(tmpFile.toString());
        diskMap.putAll(testMap);

        DiskMap diskMap2 = new DiskMap(tmpFile.toString());
        diskMap2.loadFromFile();

        assertThat(diskMap2)
            .containsExactlyInAnyOrderEntriesOf(testMap);
    }

    @Test
    void removeTest() {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("Key1", "Val1");
        testMap.put("Key2", "Val2");
        testMap.put("Key3", "Val3");

        DiskMap diskMap = new DiskMap(tmpFile.toString());
        diskMap.put("test", "test");
        diskMap.putAll(testMap);
        diskMap.remove("test");

        DiskMap diskMap2 = new DiskMap(tmpFile.toString());
        diskMap2.loadFromFile();

        assertThat(diskMap2)
            .containsExactlyInAnyOrderEntriesOf(testMap);
    }
}

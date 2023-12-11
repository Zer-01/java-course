package edu.hw10.Task1;

import edu.hw10.Task1.classesForTest.TestClass;
import edu.hw10.Task1.classesForTest.TestClassWithAnnotations;
import edu.hw10.Task1.classesForTest.TestRecord;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    RandomObjectGenerator rog = new RandomObjectGenerator();

    @Test
    void generateObjectByConstructor() {
        TestClass tClass = rog.nextObject(TestClass.class);

        assertThat(tClass)
            .isNotNull();
        assertThat(tClass.getStr())
            .isNotNull();
    }

    @Test
    void generateObjectByFabricMethod() throws NoSuchMethodException {
        TestClass tClass = rog.nextObject(TestClass.class, "genClass");

        assertThat(tClass)
            .isNotNull();
        assertThat(tClass.getStr())
            .isNotNull();
    }

    @RepeatedTest(10)
    void generateObjectByConstructorWithAnnot() {
        TestClassWithAnnotations testClass = rog.nextObject(TestClassWithAnnotations.class);

        assertThat(testClass)
            .isNotNull();
        assertThat(testClass.getIntVar())
            .isBetween(-10, 10);
        assertThat(testClass.getFloatVar())
            .isBetween(-0.5F, 0.5F);
    }

    @RepeatedTest(10)
    void generateObjectByFMethodWithAnnot() throws NoSuchMethodException {
        TestClassWithAnnotations testClass = rog.nextObject(TestClassWithAnnotations.class, "genClass");

        assertThat(testClass)
            .isNotNull();
        assertThat(testClass.getIntVar())
            .isBetween(-5, 5);
        assertThat(testClass.getFloatVar())
            .isBetween(-0.1F, 0.1F);
    }

    @Test
    void generateRecord() {
        TestRecord tRecord = rog.nextObject(TestRecord.class);

        assertThat(tRecord)
            .isNotNull();
        assertThat(tRecord.str())
            .isNotNull();
    }
}

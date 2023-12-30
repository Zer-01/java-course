package edu.hw10.Task1.classesForTest;

import edu.hw10.Task1.annotations.Max;
import edu.hw10.Task1.annotations.Min;

public class TestClassWithAnnotations {
    int intVar;
    float floatVar;
    String str;

    public TestClassWithAnnotations(@Min(-10) @Max(10) int intVar, @Min(-0.5) @Max(0.5) float floatVar, String str) {
        this.intVar = intVar;
        this.floatVar = floatVar;
        this.str = str;
    }

    public static TestClassWithAnnotations genClass(
        @Min(-5) @Max(5) int in,
        @Min(-0.1) @Max(0.1) float fl,
        String str
    ) {
        return new TestClassWithAnnotations(in, fl, str);
    }

    public int getIntVar() {
        return intVar;
    }

    public float getFloatVar() {
        return floatVar;
    }

    public String getStr() {
        return str;
    }
}

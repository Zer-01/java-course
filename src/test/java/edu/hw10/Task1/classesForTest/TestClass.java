package edu.hw10.Task1.classesForTest;

public class TestClass {
    int intVar;
    float floatVar;
    String str;

    public TestClass(int intVar, float floatVar, String str) {
        this.intVar = intVar;
        this.floatVar = floatVar;
        this.str = str;
    }

    public static TestClass genClass(int in, float fl, String str) {
        return new TestClass(in, fl, str);
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

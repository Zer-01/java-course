package edu.hw2;

public class Task4 {
    private Task4() {

    }

    public static CallingInfo callingInfo() {
        Thread current = Thread.currentThread();
        StackTraceElement stackElem = current.getStackTrace()[2];
        return new CallingInfo(stackElem.getClassName(), stackElem.getMethodName());
    }

    public record CallingInfo(String className, String methodName) {

    }
}

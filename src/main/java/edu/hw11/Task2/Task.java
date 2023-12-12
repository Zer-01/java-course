package edu.hw11.Task2;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import static net.bytebuddy.implementation.MethodDelegation.to;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class Task {
    private Task() {
    }

    public static void redefine(Class<?> originalClass, String methodName, Class<?> delegation) {
        if (originalClass == null || methodName == null || delegation == null) {
            throw new IllegalArgumentException();
        }

        ByteBuddyAgent.install();

        new ByteBuddy()
            .redefine(originalClass)
            .method(named(methodName))
            .intercept(to(delegation))
            .make()
            .load(ArithmeticUtils.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
    }
}

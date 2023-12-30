package edu.hw11.Task2;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;

public class Task {
    private Task() {
    }

    public static void redefine(Class<?> originalClass, String methodName, Class<?> delegation) {
        if (originalClass == null || methodName == null || delegation == null) {
            throw new IllegalArgumentException();
        }

        ByteBuddyAgent.install();

        new ByteBuddy()
            .redefine(DelegationClass.class)
            .name(ArithmeticUtils.class.getName())
            .make()
            .load(ArithmeticUtils.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
    }
}

package edu.hw11.Task2;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;

public class Task {
    private Task() {
    }

    public static void redefine(Class<?> originalClass, Class<?> delegation) {
        if (originalClass == null || delegation == null) {
            throw new IllegalArgumentException();
        }

        ByteBuddyAgent.install();

        new ByteBuddy()
            .redefine(delegation)
            .name(originalClass.getName())
            .make()
            .load(ArithmeticUtils.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
    }
}

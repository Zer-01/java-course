package edu.hw11.Task2;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {

    @Test
    public void sum_shouldMultiply() {
        int x = 5;
        int y = 5;
        ByteBuddyAgent.install();
        new ByteBuddy()
            .redefine(ArithmeticUtilsOverride.class)
            .name(ArithmeticUtils.class.getName())
            .make()
            .load(ArithmeticUtils.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        assertThat(ArithmeticUtils.sum(x, y)).isEqualTo(x * y);
    }
}

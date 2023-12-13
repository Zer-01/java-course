package edu.hw11.Task2;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    @Test
    void changeMethod() {
        int var1 = 2;
        int var2 = 3;
        int expResult1 = 5;
        int expResult2 = 6;

        int result1 = ArithmeticUtils.sum(var1, var2);

        ByteBuddyAgent.install();

        new ByteBuddy()
            .redefine(DelegationClass.class)
            .name(ArithmeticUtils.class.getName())
            .make()
            .load(ArithmeticUtils.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        int result2 = ArithmeticUtils.sum(var1, var2);

        assertThat(result1)
            .isEqualTo(expResult1);
        assertThat(result2)
            .isEqualTo(expResult2);
    }
}

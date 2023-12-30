package edu.hw11;

import edu.hw1.Main;
import edu.hw11.Task3.FibonacciBCAppender;
import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.Opcodes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    static Class<?> dynamicClass;

    @BeforeAll
    static void init() {
        dynamicClass = new ByteBuddy()
            .subclass(Object.class)
            .name("FibonacciClass")
            .defineMethod("fib", long.class, Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC)
            .withParameter(int.class, "n")
            .intercept(new Implementation.Simple(new FibonacciBCAppender()))
            .make()
            .load(Main.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();
    }

    @Test
    void calculateTest()
        throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        int input = 10;
        long expResult = 55;

        long result = (long) dynamicClass.getMethod("fib", int.class).invoke(null, input);

        assertThat(result)
            .isEqualTo(expResult);
    }

    @Test
    void oneInputTest()
        throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        int input = 1;
        long expResult = 1;

        long result = (long) dynamicClass.getMethod("fib", int.class).invoke(null, input);

        assertThat(result)
            .isEqualTo(expResult);
    }

    @Test
    void negativeInputTest()
        throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        int input = -10;
        long expResult = 0;

        long result = (long) dynamicClass.getMethod("fib", int.class).invoke(null, input);

        assertThat(result)
            .isEqualTo(expResult);
    }
}

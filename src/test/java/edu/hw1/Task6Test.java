package edu.hw1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class Task6Test {
    @Test
    void normalTest1(){
        int num = 4571;//4571 -> 7541-1457 -> 6084 ->
                       //8640-0468 -> 8172 -> 8721-1278 -> 7 443 ->
                       //7443-3447 -> 3996 -> 9963-3699 -> 6264 ->
                       //6642-2466 -> 4176 -> 7641-1467 -> 6174

        int result = Task6.countK(num);

        assertThat(result)
            .isEqualTo(7);
    }

    @Test
    void normalTest2(){
        int num = 4321;//4321 -> 4321-1234 -> 3087 -> 8730-0378 ->
                       //8352 -> 8532-2358 -> 6174

        int result = Task6.countK(num);

        assertThat(result)
            .isEqualTo(3);
    }

    @Test
    void equalDigits() {
        int num = 4444;

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(()->{
                Task6.countK(num);
            });
    }

    @Test
    void wrongSize(){
        int num1 = 12;
        int num2 = 43566;

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(()->{
                Task6.countK(num1);
            });
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(()->{
                Task6.countK(num2);
            });
    }
}

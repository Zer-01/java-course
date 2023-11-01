package edu.hw3;

import edu.hw3.Task6.Stock;
import edu.hw3.Task6.StockMarket;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class Task6Test {
    @Test
    void normalTest() {
        StockMarket sMarket = new StockMarket();
        int[] in = {27, 59, 45, 99, 51, 87, 3, 62, 69, 49};
        for (int num : in) {
            sMarket.add(new Stock(num));
        }

        Stock result = sMarket.mostValuebleStock();

        assertThat(result.price())
            .isEqualTo(99);
    }

    @Test
    void removeTest() {
        StockMarket sMarket = new StockMarket();
        int[] in = {27, 59, 45, 99, 51, 87, 3, 62, 69, 49};
        for (int num : in) {
            sMarket.add(new Stock(num));
        }

        sMarket.remove(new Stock(99));
        Stock result = sMarket.mostValuebleStock();

        assertThat(result.price())
            .isEqualTo(87);
    }

    @Test
    void invalidParam() {
        StockMarket sMarket = new StockMarket();
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> {
                sMarket.add(null);
            });
    }
}

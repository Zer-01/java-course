package edu.hw3.Task6;

import java.util.PriorityQueue;

public class StockMarket {
    private final PriorityQueue<Stock> queue =
        new PriorityQueue<>((stock1, stock2) -> Integer.compare(stock2.price(), stock1.price()));

    public void add(Stock stock) {
        queue.add(stock);
    }

    public Stock mostValuebleStock() {
        return queue.peek();
    }

    public void remove(Stock stock) {
        queue.remove(stock);
    }
}

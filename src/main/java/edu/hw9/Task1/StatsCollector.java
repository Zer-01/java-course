package edu.hw9.Task1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StatsCollector {
    private static final long NEW_METRIC_WAIT_MILLIS = 10;
    private final BlockingQueue<RawMetric> rawMetricQueue;
    private final List<Metric> resultList;
    private ExecutorService executorService;
    private boolean inProgress;
    private final ReadWriteLock readWriteLock;

    public StatsCollector() {
        rawMetricQueue = new LinkedBlockingQueue<>();
        resultList = new LinkedList<>();
        readWriteLock = new ReentrantReadWriteLock();
    }

    public void push(String name, double[] data) {
        if (name == null || data == null) {
            throw new IllegalArgumentException();
        }
        try {
            rawMetricQueue.put(new RawMetric(name, data));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void calcOneMetric() {
        try {
            RawMetric tmp = rawMetricQueue.poll(NEW_METRIC_WAIT_MILLIS, TimeUnit.MILLISECONDS);
            if (tmp != null) {
                readWriteLock.writeLock().lock();
                resultList.add(calculateStat(tmp));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void start(int threadsCount) {
        executorService = Executors.newFixedThreadPool(threadsCount);
        inProgress = true;
        for (int i = 0; i < threadsCount; i++) {
            executorService.submit(() -> {
                while (inProgress) {
                    calcOneMetric();
                }
            });
        }
    }

    public void stop() {
        inProgress = false;
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Metric> stats() {
        try {
            readWriteLock.readLock().lock();
            return new ArrayList<>(resultList);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    private Metric calculateStat(RawMetric in) {
        if (in.data().length < 1) {
            return new Metric(in.name(), 0, 0, 0, 0);
        }
        double sum = 0;
        double min = in.data()[0];
        double max = in.data()[0];
        for (double val : in.data()) {
            sum += val;
            min = Math.min(min, val);
            max = Math.max(max, val);
        }
        return new Metric(in.name(), sum, sum / in.data().length, max, min);
    }
}

package edu.project4.render;

import edu.project4.containers.FractalImage;
import edu.project4.containers.Rect;
import edu.project4.transformations.Transformation;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadRenderer implements Renderer {
    private final int threadCount;
    private static final int WAIT_TIMEOUT = 10;

    public MultiThreadRenderer(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public void render(
        FractalImage canvas,
        Rect world,
        int samples,
        int iterPerSample,
        int symmetry,
        List<Transformation> transforms
    ) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(threadCount)) {
            for (int i = 0; i < threadCount; i++) {
                int samplesForThread =
                    i < threadCount - 1 ? samples / threadCount : samples - samples / threadCount * (threadCount - 1);
                executorService.submit(() -> new SingleThreadRenderer().render(
                    canvas,
                    world,
                    samplesForThread,
                    iterPerSample,
                    symmetry,
                    transforms
                ));
            }

            executorService.shutdown();
            executorService.awaitTermination(WAIT_TIMEOUT, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

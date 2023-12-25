package edu.project4.render;

import edu.project4.containers.FractalImage;
import edu.project4.containers.Point;
import edu.project4.containers.Rect;
import edu.project4.transformations.AffineTransform;
import edu.project4.transformations.Transformation;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SingleThreadRenderer implements Renderer {
    private static final int START_STEPS = 20;

    @Override
    public void render(
        FractalImage canvas,
        Rect world,
        int samples,
        int iterPerSample,
        int symmetry,
        List<Transformation> transforms
    ) {

        Random random = ThreadLocalRandom.current();
        AffineTransform iterationAffine = AffineTransform.randAffine(random);
        for (int num = 0; num < samples; num++) {
            Point randPoint = world.randPoint(random);

            for (int it = -START_STEPS; it < iterPerSample; it++) {
                Transformation transformation = transforms.get(random.nextInt(0, transforms.size()));

                Point newPoint = transformation.apply(iterationAffine.apply(randPoint));
                if (it > 0) {
                    double theta2 = 0;
                    for (int s = 0; s < symmetry; theta2 += Math.PI * 2 / symmetry, s++) {
                        Point rotated = newPoint.rotate(theta2);
                        if (world.contain(rotated)) {
                            int xRes = canvas.width()
                                - (int) ((world.maxX() - rotated.x()) / world.rangeX() * canvas.width());
                            int yRes = canvas.height()
                                - (int) ((world.maxY() - rotated.y()) / world.rangeY() * canvas.height());
                            canvas.updateIfInRange(xRes, yRes, iterationAffine);
                        }
                    }
                }
            }
        }
    }
}

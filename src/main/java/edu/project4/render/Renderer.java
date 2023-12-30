package edu.project4.render;

import edu.project4.containers.FractalImage;
import edu.project4.containers.Rect;
import edu.project4.transformations.Transformation;
import java.util.List;

public interface Renderer {
    void render(
        FractalImage canvas,
        Rect world,
        int samples,
        int iterPerSample,
        int symmetry,
        List<Transformation> transforms
    );
}

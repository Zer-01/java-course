package edu.project4.transformations;

import edu.project4.Point;

public class SinusoidalTransform implements Transformation {
    @Override
    public Point apply(Point point) {
        return new Point(Math.sin(point.x()), Math.sin(point.y()));
    }
}

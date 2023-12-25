package edu.project4.transformations;

import edu.project4.containers.Point;

public class LinearTransform implements Transformation {
    @Override
    public Point apply(Point point) {
        return point;
    }
}

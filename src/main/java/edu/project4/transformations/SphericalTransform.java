package edu.project4.transformations;

import edu.project4.Point;

public class SphericalTransform implements Transformation {
    @Override
    public Point apply(Point point) {
        double div = Math.pow(point.x(), 2) + Math.pow(point.y(), 2);
        return new Point(point.x() / div, point.y() / div);
    }
}

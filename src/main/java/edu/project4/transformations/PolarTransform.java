package edu.project4.transformations;

import edu.project4.Point;

public class PolarTransform implements Transformation {
    @Override
    public Point apply(Point point) {
        double newX = Math.atan(point.y() / point.x()) / Math.PI;
        double newY = Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2)) - 1;
        return new Point(newX, newY);
    }
}

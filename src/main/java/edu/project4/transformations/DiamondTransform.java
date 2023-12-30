package edu.project4.transformations;

import edu.project4.containers.Point;

public class DiamondTransform implements Transformation {
    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        double th = Math.atan2(point.y(), point.x());
        return new Point(Math.sin(th) / r, r * Math.cos(th));
    }
}

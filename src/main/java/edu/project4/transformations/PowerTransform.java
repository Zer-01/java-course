package edu.project4.transformations;

import edu.project4.Point;

public class PowerTransform implements Transformation {
    @Override
    public Point apply(Point point) {
        double th = Math.atan2(point.y(), point.x());
        double rsth = Math.pow(Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2)), Math.sin(th));
        return new Point(rsth * Math.cos(th), rsth * Math.sin(th));
    }
}

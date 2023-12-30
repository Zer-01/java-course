package edu.project4.containers;

import java.util.Random;

public record Rect(double minX, double maxX, double minY, double maxY) {
    public boolean contain(Point point) {
        return point.x() >= minX && point.x() <= maxX && point.y() >= minY && point.y() <= maxY;
    }

    public Point randPoint(Random random) {
        return new Point(random.nextDouble(minX, maxX), random.nextDouble(minY, maxY));
    }

    public double rangeX() {
        return maxX - minX;
    }

    public double rangeY() {
        return maxY - minY;
    }
}

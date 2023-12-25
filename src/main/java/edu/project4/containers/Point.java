package edu.project4.containers;

public record Point(double x, double y) {
    public Point rotate(double theta) {
        double newX = x * Math.cos(theta) - y * Math.sin(theta);
        double newY = x * Math.sin(theta) + y * Math.cos(theta);
        return new Point(newX, newY);
    }
}

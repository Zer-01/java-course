package edu.project2;

public record Cell(Coordinate coordinate, Type type) {
    public enum Type {
        WALL,
        PASSAGE
    }
}

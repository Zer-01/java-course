package edu.project2;

public final class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;
    private final Coordinate start;
    private final Coordinate end;

    public Maze(int height, int width, Cell[][] grid, Coordinate start, Coordinate end) {
        this.height = height;
        this.width = width;
        this.grid = grid;
        this.start = start;
        this.end = end;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell[][] getGrid() {
        return grid.clone();
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}

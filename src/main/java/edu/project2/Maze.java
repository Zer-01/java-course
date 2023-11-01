package edu.project2;

public record Maze(int height, int width, Cell[][] grid, Coordinate start, Coordinate end) {
    @Override
    public Cell[][] grid() {
        return grid.clone();
    }
}

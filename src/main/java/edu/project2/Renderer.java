package edu.project2;

import java.util.List;

public class Renderer {
    private static final String WALL = "\u001b[48;5;8m   \u001b[0m";
    private static final String PASSAGE = "   ";
    private static final String PATH = "\u001b[32;1m * \u001b[0m";
    private static final int CELL_WIDTH = 3;

    private Renderer() {

    }

    public static String render(Maze maze) {
        StringBuilder strB = new StringBuilder(maze.height() * maze.width() * CELL_WIDTH);
        Cell[][] mazeGrid = maze.grid();

        for (int y = 0; y < maze.height(); y++) {
            for (int x = 0; x < maze.width(); x++) {
                if (mazeGrid[y][x] == Cell.WALL) {
                    strB.append(WALL);
                } else {
                    strB.append(PASSAGE);
                }
            }
            strB.append('\n');
        }
        return strB.toString();
    }

    public static String render(Maze maze, List<Coordinate> path) {
        StringBuilder strB = new StringBuilder(maze.height() * maze.width() * CELL_WIDTH);
        Cell[][] mazeGrid = maze.grid();

        for (int y = 0; y < maze.height(); y++) {
            for (int x = 0; x < maze.width(); x++) {
                if (mazeGrid[y][x] == Cell.WALL) {
                    strB.append(WALL);
                } else if (path.contains(new Coordinate(x, y))) {
                    strB.append(PATH);
                } else {
                    strB.append(PASSAGE);
                }
            }
            strB.append('\n');
        }
        return strB.toString();
    }
}

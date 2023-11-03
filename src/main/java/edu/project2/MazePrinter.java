package edu.project2;

import java.util.List;

public class MazePrinter {
    private MazePrinter() {

    }

    @SuppressWarnings("RegexpSinglelineJava")
    public static void print(Maze maze) {
        String out = Renderer.render(maze);

        System.out.println();
        System.out.println(out);
    }

    @SuppressWarnings("RegexpSinglelineJava")
    public static void print(Maze maze, List<Coordinate> path) {
        String out = Renderer.render(maze, path);

        System.out.println();
        System.out.println(out);
    }
}

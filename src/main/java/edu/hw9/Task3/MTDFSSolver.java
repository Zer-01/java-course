package edu.hw9.Task3;

import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class MTDFSSolver {
    private MTDFSSolver() {
    }

    public static List<Coordinate> solve(Maze maze) {
        if (maze == null) {
            throw new IllegalArgumentException();
        }
        DFSTask task = new DFSTask(maze, new HashSet<>(), maze.start());
        Set<Coordinate> result = ForkJoinPool.commonPool().invoke(task);
        if (result != null) {
            return result.stream().toList();
        } else {
            return null;
        }
    }
}

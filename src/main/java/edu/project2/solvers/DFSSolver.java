package edu.project2.solvers;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DFSSolver implements Solver {
    private Maze maze;
    private Cell[][] gridCache;
    private Set<Coordinate> answerSells;
    private final static int[][] POSSIBLE_MOVES = {
        {0, 1, 0, -1},  //x
        {1, 0, -1, 0}}; //y

    @Override
    public List<Coordinate> solve(Maze maze) {
        this.maze = maze;
        gridCache = maze.grid();
        answerSells = new HashSet<>();
        if (dfsStep(maze.start())) {
            return answerSells.stream().toList();
        } else {
            return null;
        }
    }

    private boolean dfsStep(Coordinate coordinate) {
        if (outOfBounds(coordinate) || !isPassage(coordinate) || answerSells.contains(coordinate)) {
            return false;
        }

        answerSells.add(coordinate);
        if (coordinate.equals(maze.end())) {
            return true;
        } else {
            for (int i = 0; i < POSSIBLE_MOVES[0].length; i++) {
                boolean result = dfsStep(new Coordinate(
                    coordinate.x() + POSSIBLE_MOVES[0][i],
                    coordinate.y() + POSSIBLE_MOVES[1][i]
                ));
                if (result) {
                    return true;
                }
            }
        }
        answerSells.remove(coordinate);
        return false;
    }

    private boolean isPassage(Coordinate coord) {
        return gridCache[coord.y()][coord.x()].equals(Cell.PASSAGE);
    }

    private boolean outOfBounds(Coordinate coord) {
        return coord.x() < 0 || coord.x() > gridCache[0].length - 1 || coord.y() < 0
            || coord.y() > gridCache.length - 1;
    }
}

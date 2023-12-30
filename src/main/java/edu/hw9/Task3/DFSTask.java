package edu.hw9.Task3;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class DFSTask extends RecursiveTask<Set<Coordinate>> {
    private final Maze maze;
    private final Set<Coordinate> answerSells;
    private final Coordinate curCoord;
    private final static int[][] POSSIBLE_MOVES = {
        {0, 1, 0, -1},  //x
        {1, 0, -1, 0}}; //y

    public DFSTask(Maze maze, Set<Coordinate> answerSells, Coordinate curCoord) {
        this.maze = maze;
        this.answerSells = answerSells;
        this.curCoord = curCoord;
    }

    @Override
    protected Set<Coordinate> compute() {
        if (outOfBounds(curCoord) || !isPassage(curCoord) || answerSells.contains(curCoord)) {
            return null;
        }

        answerSells.add(curCoord);
        if (curCoord.equals(maze.end())) {
            return answerSells;
        } else {
            List<DFSTask> taskList = new ArrayList<>();
            for (int i = 0; i < POSSIBLE_MOVES[0].length; i++) {
                taskList.add(new DFSTask(maze, answerSells, new Coordinate(
                    curCoord.x() + POSSIBLE_MOVES[0][i],
                    curCoord.y() + POSSIBLE_MOVES[1][i]
                )));
                invokeAll(taskList);

                for (var task : taskList) {
                    var path = task.join();
                    if (path != null) {
                        return path;
                    }
                }
            }
            answerSells.remove(curCoord);
            return null;
        }
    }

    private boolean isPassage(Coordinate coord) {
        return maze.grid()[coord.y()][coord.x()].equals(Cell.PASSAGE);
    }

    private boolean outOfBounds(Coordinate coord) {
        return coord.x() < 0 || coord.x() > maze.grid()[0].length - 1 || coord.y() < 0
            || coord.y() > maze.grid().length - 1;
    }
}

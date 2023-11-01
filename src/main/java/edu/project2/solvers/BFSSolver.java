package edu.project2.solvers;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BFSSolver implements Solver {
    private Maze maze;
    private Cell[][] gridCache;
    private final static int[][] POSSIBLE_MOVES = {
        {0, 1, 0, -1},  //x
        {1, 0, -1, 0}}; //y

    @Override
    public List<Coordinate> solve(Maze maze) {
        this.maze = maze;
        this.gridCache=maze.grid();
        Map<Coordinate, Coordinate> pathLog  = new HashMap<>();
        if(BFSStart(pathLog)) {
            return getAnswer(pathLog);
        } else {
            return null;
        }
    }

    private boolean BFSStart(Map<Coordinate, Coordinate> pathLog) {
        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(maze.start());
        Coordinate tempCoord;

        while (!queue.isEmpty()) {
            tempCoord = queue.poll();
            if(tempCoord.equals(maze.end())) {
                return true;
            }
            for (int i = 0; i < POSSIBLE_MOVES[0].length; i++) {
                Coordinate nextCoord = new Coordinate(
                    tempCoord.x() + POSSIBLE_MOVES[0][i],
                    tempCoord.y() + POSSIBLE_MOVES[1][i]
                );
                if(!outOfBounds(nextCoord) && isPassage(nextCoord) && !pathLog.containsKey(nextCoord)) {
                    pathLog.put(nextCoord, tempCoord);
                    queue.add(nextCoord);
                }
            }
        }
        return false;
    }

    private List<Coordinate> getAnswer(Map<Coordinate, Coordinate> pathLog) {
        List<Coordinate> answer = new ArrayList<>();
        answer.add(maze.end());
        Coordinate coordinate = pathLog.get(maze.end());
        while (!coordinate.equals(maze.start())) {
            answer.add(coordinate);
            coordinate=pathLog.get(coordinate);
        }
        answer.add(coordinate);
        return answer;
    }

    private boolean isPassage(Coordinate coord) {
        return gridCache[coord.y()][coord.x()].equals(Cell.PASSAGE);
    }

    private boolean outOfBounds(Coordinate coord) {
        return coord.x() < 0 || coord.x() > gridCache[0].length - 1 || coord.y() < 0 ||
            coord.y() > gridCache.length - 1;
    }
}

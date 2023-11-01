package edu.project2.generators;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.Random;

public class HuntAndKillGenerator implements Generator {
    private final static int MINIMAL_SIDE_SIZE = 4;
    private final static int[][] POSSIBLE_MOVES = {
        {0, 1, 0, -1},  //x
        {1, 0, -1, 0}}; //y
    private final Random rand = new Random();

    @Override
    public Maze generate(int height, int width) {
        if (height < MINIMAL_SIDE_SIZE || width < MINIMAL_SIDE_SIZE) {
            throw new IllegalArgumentException();
        }
        Cell[][] grid = new Cell[height][width];
        fillGrid(grid);
        createMaze(grid);
        Coordinate in = genIn(grid);
        Coordinate out = genOut(grid);
        return new Maze(height, width, grid, in, out);
    }

    private void fillGrid(Cell[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = Cell.WALL;
            }
        }
    }

    private void createMaze(Cell[][] grid) {
        int randX = rand.nextInt(1, grid[0].length - 2);
        int randY = rand.nextInt(1, grid.length - 2);
        grid[randY][randX] = Cell.PASSAGE;
        boolean isInProgress = true;
        while (isInProgress) {
            isInProgress = false;
            for (int x = 1; x < grid[0].length - 1; x++) {
                for (int y = 0; y < grid.length - 1; y++) {
                    isInProgress = isInProgress | hunt(grid, new Coordinate(x, y));
                }
            }
        }
    }

    private boolean hunt(Cell[][] grid, Coordinate startCoord) {
        boolean isChanged = false;
        Coordinate currCoord = startCoord;
        int nextMove;
        while (isPossibleMakePassage(grid, currCoord)) {
            grid[currCoord.y()][currCoord.x()] = Cell.PASSAGE;
            nextMove = rand.nextInt(0, POSSIBLE_MOVES[0].length);
            currCoord = new Coordinate(
                startCoord.x() + POSSIBLE_MOVES[0][nextMove],
                startCoord.y() + POSSIBLE_MOVES[1][nextMove]
            );
            isChanged = true;
        }
        return isChanged;
    }

    private boolean isPossibleMakePassage(Cell[][] grid, Coordinate coord) {
        if (coord.x() <= 0 || coord.x() >= grid[0].length - 1 || coord.y() <= 0 || coord.y() >= grid.length - 1
            || grid[coord.y()][coord.x()].equals(Cell.PASSAGE)) {
            return false;
        }

        int passageAround = 0;
        for (int i = 0; i < POSSIBLE_MOVES[0].length; i++) {
            if (isPassage(grid, coord.x() + POSSIBLE_MOVES[0][i], coord.y() + POSSIBLE_MOVES[1][i])) {
                passageAround++;
            }
        }
        return passageAround == 1;
    }

    private boolean isPassage(Cell[][] grid, int x, int y) {
        return x > 0 && x < grid[0].length - 1 && y > 0 && y < grid.length - 1 && grid[y][x].equals(Cell.PASSAGE);
    }

    private Coordinate genIn(Cell[][] grid) {
        int yPos = rand.nextInt(0, grid.length);
        while (!grid[yPos][1].equals(Cell.PASSAGE)) {
            yPos = (yPos + 1) % grid.length;
        }
        grid[yPos][0] = Cell.PASSAGE;
        return new Coordinate(0, yPos);
    }

    private Coordinate genOut(Cell[][] grid) {
        int yPos = rand.nextInt(0, grid.length);
        while (!grid[yPos][grid[0].length - 2].equals(Cell.PASSAGE)) {
            yPos = (yPos + 1) % grid.length;
        }
        grid[yPos][grid[0].length - 1] = Cell.PASSAGE;
        return new Coordinate(grid[0].length - 1, yPos);
    }
}

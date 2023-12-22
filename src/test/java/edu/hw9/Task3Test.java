package edu.hw9;

import edu.hw9.Task3.MTDFSSolver;
import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    @Test
    void findSolveTest() {
        Cell[][] testGrid = {
            {Cell.WALL, Cell.PASSAGE, Cell.WALL, Cell.WALL},
            {Cell.WALL, Cell.PASSAGE, Cell.PASSAGE, Cell.PASSAGE},
            {Cell.PASSAGE, Cell.PASSAGE, Cell.PASSAGE, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.PASSAGE, Cell.WALL}};

        List<Coordinate> expPath = Arrays.asList(
            new Coordinate(0, 2),
            new Coordinate(1, 2),
            new Coordinate(2, 2),
            new Coordinate(2, 1),
            new Coordinate(3, 1)
        );

        Maze maze = new Maze(4, 4, testGrid, new Coordinate(0, 2), new Coordinate(3, 1));

        List<Coordinate> path = MTDFSSolver.solve(maze);

        assertThat(path)
            .containsExactlyInAnyOrderElementsOf(expPath);
    }

    @Test
    void findSolveForNoSolveMazeTest() {
        Cell[][] testGrid = {
            {Cell.WALL, Cell.PASSAGE, Cell.WALL, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.WALL, Cell.PASSAGE},
            {Cell.PASSAGE, Cell.PASSAGE, Cell.PASSAGE, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.PASSAGE, Cell.WALL}};

        Maze maze = new Maze(4, 4, testGrid, new Coordinate(0, 2), new Coordinate(3, 1));

        List<Coordinate> path = MTDFSSolver.solve(maze);

        assertThat(path)
            .isNull();
    }
}

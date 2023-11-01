package edu.project2;

import edu.project2.solvers.BFSSolver;
import edu.project2.solvers.DFSSolver;
import edu.project2.solvers.Solver;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class SolveTest {

    static Arguments[] solvers() {
        return new Arguments[] {
            Arguments.of(new BFSSolver()),
            Arguments.of(new DFSSolver())
        };
    }

    @ParameterizedTest
    @MethodSource("solvers")
    void findSolveTest(Solver solver) {
        Cell[][] testGrid = {
            {Cell.WALL, Cell.PASSAGE, Cell.WALL, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.PASSAGE, Cell.PASSAGE},
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

        List<Coordinate> path = solver.solve(maze);

        assertThat(path)
            .containsExactlyInAnyOrderElementsOf(expPath);
    }

    @ParameterizedTest
    @MethodSource("solvers")
    void findSolveForNoSolveMazeTest(Solver solver) {
        Cell[][] testGrid = {
            {Cell.WALL, Cell.PASSAGE, Cell.WALL, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.WALL, Cell.PASSAGE},
            {Cell.PASSAGE, Cell.PASSAGE, Cell.PASSAGE, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.PASSAGE, Cell.WALL}};

        Maze maze = new Maze(4, 4, testGrid, new Coordinate(0, 2), new Coordinate(3, 1));

        List<Coordinate> path = solver.solve(maze);

        assertThat(path)
            .isNull();
    }
}

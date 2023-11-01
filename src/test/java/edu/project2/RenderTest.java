package edu.project2;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RenderTest {
    private static final String WALL = "\u001b[48;5;8m   \u001b[0m";
    private static final String PASSAGE = "   ";
    private static final String PATH = "\u001b[32;1m * \u001b[0m";

    @Test
    void renderTestWithoutSolve() {
        Cell[][] testGrid = {
            {Cell.WALL, Cell.PASSAGE, Cell.WALL, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.PASSAGE, Cell.PASSAGE},
            {Cell.PASSAGE, Cell.PASSAGE, Cell.PASSAGE, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL}};

        String expectedStr =
                WALL + PASSAGE + WALL + WALL + '\n' +
                WALL + WALL + PASSAGE + PASSAGE + '\n' +
                PASSAGE + PASSAGE + PASSAGE + WALL + '\n' +
                WALL + WALL + WALL + WALL + '\n';

        Maze maze = new Maze(4, 4, testGrid, new Coordinate(0, 2), new Coordinate(3, 1));

        String str = Renderer.render(maze);

        assertThat(str)
            .isEqualTo(expectedStr);
    }

    @Test
    void renderTestWithSolve() {
        Cell[][] testGrid = {
            {Cell.WALL, Cell.PASSAGE, Cell.WALL, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.PASSAGE, Cell.PASSAGE},
            {Cell.PASSAGE, Cell.PASSAGE, Cell.PASSAGE, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL}};

        String expectedStr =
                WALL + PASSAGE + WALL + WALL + '\n' +
                WALL + WALL + PATH + PATH + '\n' +
                PATH + PATH + PATH + WALL + '\n' +
                WALL + WALL + WALL + WALL + '\n';

        List<Coordinate> path = Arrays.asList(
            new Coordinate(0, 2),
            new Coordinate(1, 2),
            new Coordinate(2, 2),
            new Coordinate(2, 1),
            new Coordinate(3, 1)
        );
        Maze maze = new Maze(4, 4, testGrid, new Coordinate(0, 2), new Coordinate(3, 1));

        String str = Renderer.render(maze, path);

        assertThat(str)
            .isEqualTo(expectedStr);
    }

    @Test
    void renderTestWitNullSolve() {
        Cell[][] testGrid = {
            {Cell.WALL, Cell.PASSAGE, Cell.WALL, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.PASSAGE, Cell.PASSAGE},
            {Cell.PASSAGE, Cell.PASSAGE, Cell.PASSAGE, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL}};

        String expectedStr =
                WALL + PASSAGE + WALL + WALL + '\n' +
                WALL + WALL + PASSAGE + PASSAGE + '\n' +
                PASSAGE + PASSAGE + PASSAGE + WALL + '\n' +
                WALL + WALL + WALL + WALL + '\n';

        Maze maze = new Maze(4, 4, testGrid, new Coordinate(0, 2), new Coordinate(3, 1));

        String str = Renderer.render(maze, null);

        assertThat(str)
            .isEqualTo(expectedStr);
    }

    @Test
    void nullMazeTest() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(()->{
                Renderer.render(null);
            });
    }
}

package edu.project2;

import edu.project2.generators.HuntAndKillGenerator;
import edu.project2.solvers.BFSSolver;
import edu.project2.solvers.Solver;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class GenerateTest {
    @Test
    void generateAndSolveNormalTest() {
        HuntAndKillGenerator huntAndKillGenerator = new HuntAndKillGenerator();
        Solver solver = new BFSSolver();
        Maze maze = huntAndKillGenerator.generate(100, 100);

        List<Coordinate> solve = solver.solve(maze);

        assertThat(solve)
            .isNotNull();
    }

    @Test
    void IllegalArgumentTest() {
        HuntAndKillGenerator huntAndKillGenerator = new HuntAndKillGenerator();

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                huntAndKillGenerator.generate(0, 0);
            });
    }
}

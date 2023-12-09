package edu.project4;

import edu.project4.containers.FractalImage;
import edu.project4.containers.Rect;
import edu.project4.render.MultiThreadRenderer;
import edu.project4.render.Renderer;
import edu.project4.render.SingleThreadRenderer;
import edu.project4.transformations.DiamondTransform;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class RenderTest {
    static Arguments[] renderers() {
        return new Arguments[] {
            Arguments.of(new SingleThreadRenderer()),
            Arguments.of(new MultiThreadRenderer(2))
        };
    }

    @ParameterizedTest
    @MethodSource("renderers")
    void normalTest(Renderer renderer) {
        FractalImage emptyFractalImage = FractalImage.create(10, 10);
        FractalImage fractalImage = FractalImage.create(10, 10);

        assertThat(fractalImage.data())
            .isEqualTo(emptyFractalImage.data());

        renderer.render(
            fractalImage,
            new Rect(-1, 1, -1, 1),
            100,
            10,
            1,
            List.of(new DiamondTransform())
        );

        assertThat(fractalImage.data())
            .isNotEqualTo(emptyFractalImage.data());
    }
}

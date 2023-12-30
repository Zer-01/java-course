package edu.project4;

import edu.project4.containers.FractalImage;
import edu.project4.containers.Pixel;
import edu.project4.utils.GammaCorrection;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GammaTest {
    @Test
    void gammaTest() {
        FractalImage image = FractalImage.create(2, 1);
        image.setPixel(0, 0, new Pixel(6, 6, 6, 100));
        image.setPixel(0, 1, new Pixel(6, 6, 6, 50));

        GammaCorrection.correct(image, 2.2);

        assertThat(image.pixel(0, 0))
            .isEqualTo(new Pixel(6, 6, 6, 100));
        assertThat(image.pixel(0, 1))
            .isEqualTo(new Pixel(5, 5, 5, 50));
    }
}

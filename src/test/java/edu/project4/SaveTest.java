package edu.project4;

import edu.project4.containers.FractalImage;
import edu.project4.containers.Pixel;
import edu.project4.utils.ImageFormat;
import edu.project4.utils.ImageUtils;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class SaveTest {
    @TempDir Path tempDit;

    @Test
    void saveImageTest() {
        FractalImage image = FractalImage.create(3, 1);
        image.setPixel(0, 0, new Pixel(255, 0, 0, 0));
        image.setPixel(0, 1, new Pixel(0, 255, 0, 0));
        image.setPixel(0, 2, new Pixel(0, 0, 255, 0));
        Path savePath = tempDit.resolve("test");

        assertThatNoException()
            .isThrownBy(() -> ImageUtils.save(image, savePath, ImageFormat.PNG));

        assertThat(tempDit)
            .exists();
    }
}

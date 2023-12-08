package edu.project4.utils;

import edu.project4.containers.FractalImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.nio.file.Path;

public final class ImageUtils {
    private ImageUtils() {
    }

    public static void save(FractalImage image, Path filename, ImageFormat format) throws IOException {
        BufferedImage bufferedImage = getBuffImage(image);
        String fileFormat = String.valueOf(format).toLowerCase();
        ImageIO.write(bufferedImage, fileFormat, filename.toFile());
    }

    private static BufferedImage getBuffImage(FractalImage image) {
        BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);
        int[] bufferedPixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < image.height(); i++) {
            for (int j = 0; j < image.width(); j++) {
                bufferedPixels[i * image.width() + j] = (image.pixel(j, i).r() << 16)
                    | (image.pixel(j, i).g() << 8)
                    | image.pixel(j, i).b();
            }
        }
        return bufferedImage;
    }
}

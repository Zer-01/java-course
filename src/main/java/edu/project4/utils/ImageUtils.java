package edu.project4.utils;

import edu.project4.containers.FractalImage;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public final class ImageUtils {
    private final static int RED_BYTE_OFFSET = 16;
    private final static int GREEN_BYTE_OFFSET = 8;

    private ImageUtils() {
    }

    public static void save(FractalImage image, Path filename, ImageFormat format) throws IOException {
        if (image == null || filename == null || format == null) {
            throw new IllegalArgumentException();
        }
        BufferedImage bufferedImage = getBuffImage(image);
        String fileFormat = String.valueOf(format).toLowerCase();
        ImageIO.write(bufferedImage, fileFormat, filename.toFile());
    }

    private static BufferedImage getBuffImage(FractalImage image) {
        BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);
        int[] bufferedPixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < image.width(); i++) {
            for (int j = 0; j < image.height(); j++) {
                bufferedPixels[j * image.width() + i] = (image.pixel(j, i).r() << RED_BYTE_OFFSET)
                    | (image.pixel(j, i).g() << GREEN_BYTE_OFFSET)
                    | image.pixel(j, i).b();
            }
        }
        return bufferedImage;
    }
}

package edu.project4.utils;

import edu.project4.containers.FractalImage;
import edu.project4.containers.Pixel;

public class GammaCorrection {
    private GammaCorrection() {
    }

    public static void correct(FractalImage image, double gamma) {
        double max = 0.0;
        double[][] normals = new double[image.height()][image.width()];
        for (int i = 0; i < image.width(); i++) {
            for (int j = 0; j < image.height(); j++) {
                if (image.pixel(j, i).hitCount() != 0) {
                    normals[j][i] = Math.log10(image.pixel(j, i).hitCount());
                    if (normals[j][i] > max) {
                        max = normals[j][i];
                    }
                }
            }
        }

        for (int i = 0; i < image.width(); i++) {
            for (int j = 0; j < image.height(); j++) {
                normals[j][i] /= max;
                Pixel pixel = image.pixel(j, i);
                double mult = Math.pow(normals[j][i], (1.0 / gamma));
                int r = (int) (pixel.r() * mult);
                int g = (int) (pixel.g() * mult);
                int b = (int) (pixel.b() * mult);
                image.setPixel(j, i, new Pixel(r, g, b, pixel.hitCount()));
            }
        }
    }
}

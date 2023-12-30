package edu.project4.containers;

import edu.project4.transformations.AffineTransform;

public record FractalImage(Pixel[][] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException();
        }
        Pixel[][] pixels = new Pixel[height][width];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[j][i] = new Pixel(0, 0, 0, 0);
            }
        }
        return new FractalImage(pixels, width, height);
    }

    public Pixel pixel(int y, int x) {
        return data[y][x];
    }

    public void setPixel(int y, int x, Pixel pixel) {
        data[y][x] = pixel;
    }

    public void updateIfInRange(int x, int y, AffineTransform transform) {
        int r;
        int g;
        int b;
        if (x < width && y < height) {
            synchronized (data[y][x]) {
                Pixel pixel = data[y][x];
                if (pixel.hitCount() == 0) {
                    r = transform.rCol();
                    g = transform.gCol();
                    b = transform.bCol();
                } else {
                    r = (pixel.r() + transform.rCol()) / 2;
                    g = (pixel.g() + transform.gCol()) / 2;
                    b = (pixel.b() + transform.bCol()) / 2;
                }
                data[y][x] = new Pixel(r, g, b, pixel.hitCount() + 1);
            }
        }
    }
}

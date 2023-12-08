package edu.project4.containers;

import edu.project4.transformations.AffineTransform;

public record FractalImage(Pixel[][] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        return new FractalImage(new Pixel[height][width], width, height);
    }

    Pixel pixel(int x, int y) {
        return data[y][x];
    }

    public boolean contain(int x, int y) {
        return x >= 0 && x <= width && y >= 0 && y <= height;
    }

    public void updateIfInRange(int x, int y, AffineTransform transform) {
        int r;
        int g;
        int b;
        Pixel pixel = data[y][x];
        synchronized (pixel) {
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

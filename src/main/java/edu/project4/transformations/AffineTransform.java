package edu.project4.transformations;

import edu.project4.containers.Point;
import java.util.Random;

public record AffineTransform(
    double a,
    double b,
    double c,
    double d,
    double e,
    double f,
    int rCol,
    int gCol,
    int bCol
) {
    private static final double MIN_AFFINE = -1;
    private static final double MAX_AFFINE = 1;
    private static final int MIN_COLOUR_NUM = 64;
    private static final int MAX_COLOUR_NUM = 256;

    public Point apply(Point point) {
        double x = a * point.x() + b * point.y() + c;
        double y = d * point.x() + e * point.y() + f;
        return new Point(x, y);
    }

    public static AffineTransform randAffine(Random rand) {
        return new AffineTransform(
            rand.nextDouble(MIN_AFFINE, MAX_AFFINE),
            rand.nextDouble(MIN_AFFINE, MAX_AFFINE),
            rand.nextDouble(MIN_AFFINE, MAX_AFFINE),
            rand.nextDouble(MIN_AFFINE, MAX_AFFINE),
            rand.nextDouble(MIN_AFFINE, MAX_AFFINE),
            rand.nextDouble(MIN_AFFINE, MAX_AFFINE),
            rand.nextInt(MIN_COLOUR_NUM, MAX_COLOUR_NUM),
            rand.nextInt(MIN_COLOUR_NUM, MAX_COLOUR_NUM),
            rand.nextInt(MIN_COLOUR_NUM, MAX_COLOUR_NUM)
        );
    }

    public static AffineTransform[] randAffineArray(int size, Random rand) {
        AffineTransform[] result = new AffineTransform[size];
        for (int i = 0; i < size; i++) {
            result[i] = randAffine(rand);
        }
        return result;
    }
}

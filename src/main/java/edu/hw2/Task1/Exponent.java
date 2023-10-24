package edu.hw2.Task1;

public record Exponent(Expr base, double exp) implements Expr {
    @Override
    public double evaluate() {
        return Math.pow(base.evaluate(), exp);
    }
}

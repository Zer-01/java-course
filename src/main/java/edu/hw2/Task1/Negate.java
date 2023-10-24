package edu.hw2.Task1;

public record Negate(Expr obj) implements Expr {
    @Override
    public double evaluate() {
        return -obj.evaluate();
    }
}

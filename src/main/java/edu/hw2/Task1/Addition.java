package edu.hw2.Task1;

public record Addition(Expr num1, Expr num2) implements Expr {
    @Override
    public double evaluate() {
        return num1.evaluate() + num2.evaluate();
    }
}

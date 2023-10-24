package edu.hw2.Task1;

    public sealed interface Expr permits Addition, Constant, Exponent, Multiplication, Negate {
        double evaluate();
    }

package edu.hw2;

public class Task1 {
    private Task1() {
    }

    public static sealed interface Expr {
        double evaluate();

        public record Constant(double num) implements Expr {
            @Override
            public double evaluate() {
                return num;
            }
        }

        public record Negate(Expr obj) implements Expr {
            @Override
            public double evaluate() {
                return -obj.evaluate();
            }
        }

        public record Exponent(Expr base, double exp) implements Expr {
            @Override
            public double evaluate() {
                return Math.pow(base.evaluate(), exp);
            }
        }

        public record Addition(Expr num1, Expr num2) implements Expr {
            @Override
            public double evaluate() {
                return num1.evaluate() + num2.evaluate();
            }
        }

        public record Multiplication(Expr num1, Expr num2) implements Expr {
            @Override
            public double evaluate() {
                return num1.evaluate() * num2.evaluate();
            }
        }
    }
}

package edu.hw9.Task1;

public record Metric(String name, double sum, double avg, double max, double min) {
    @Override
    public String toString() {
        return "Metric: " + name
            + ", Sum: " + sum
            + ", Average: " + avg
            + ", Max: " + max
            + ", Min: " + min;
    }
}

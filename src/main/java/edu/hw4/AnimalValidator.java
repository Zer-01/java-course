package edu.hw4;

import java.util.LinkedHashSet;
import java.util.Set;

public class AnimalValidator {
    private AnimalValidator() {

    }

    public static Set<ValidationError> validateAnimal(Animal animal) {
        Set<ValidationError> result = new LinkedHashSet<>();

        if (animal.weight() <= 0) {
            result.add(new ValidationError("weight", "Weight field must be positive"));
        }
        if (animal.age() < 0) {
            result.add(new ValidationError("age", "Age field must be positive or 0"));
        }
        if (animal.height() <= 0) {
            result.add(new ValidationError("height", "Height field must be positive"));
        }
        return result;
    }

    public static boolean isError(Animal animal) {
        return animal.weight() <= 0 || animal.age() < 0 || animal.height() <= 0;
    }
}

package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Tasks {
    private static final int TASK11_HEIGHT = 100;

    private Tasks() {

    }

    public static List<Animal> sortByHeight(List<Animal> list) { //task1
        return list.stream().sorted(Comparator.comparingInt(Animal::height))
            .collect(Collectors.toList());
    }

    public static List<Animal> sortByWeight(List<Animal> list, long k) { //Task2
        return list.stream().sorted(Comparator.comparingInt(Animal::weight).reversed())
            .limit(k)
            .collect(Collectors.toList());
    }

    public static Map<Animal.Type, Integer> getTypesCount(List<Animal> list) { //Task3
        return list.stream()
            .collect(Collectors.toMap(Animal::type, count -> 1, Math::addExact));
    }

    public static Animal getLongestName(List<Animal> list) { //Task4
        return list.stream()
            .max(Comparator.comparingInt(animal -> animal.name().length()))
            .orElse(null);
    }

    public static Animal.Sex getMostPopularSex(List<Animal> list) { //Task5
        if (list.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return list.stream()
            .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()))
            .entrySet().stream()
            .max((count1, count2) -> (int) (count1.getValue() - count2.getValue()))
            .map(Map.Entry::getKey).orElse(null);
    }

    public static Map<Animal.Type, Animal> heaviestAnimalOfEveryType(List<Animal> list) { //Task6
        return list.stream()
            .collect(Collectors.toMap(
                Animal::type, animal -> animal,
                BinaryOperator.maxBy(Comparator.comparingInt(Animal::weight))
            ));
    }

    public static Animal getKOldestAnimal(List<Animal> list, int k) { //Task7
        return list.stream()
            .sorted(Comparator.comparingInt(Animal::age).reversed())
            .skip(k - 1)
            .findFirst()
            .orElse(null);
    }

    public static Optional<Animal> getHeaviestBelowK(List<Animal> list, int k) { //Task8
        return list.stream()
            .filter(animal -> animal.height() < k)
            .max(Comparator.comparingInt(Animal::weight));
    }

    public static Integer getPawsCount(List<Animal> list) { //Task9
        return list.stream()
            .mapToInt(Animal::paws).sum();
    }

    public static List<Animal> ageNotEqualPawsCount(List<Animal> list) { //Task10
        return list.stream()
            .filter(animal -> animal.age() != animal.paws())
            .toList();
    }

    public static List<Animal> canBiteAndHigher100(List<Animal> list) { //Task11
        return list.stream()
            .filter(animal -> animal.bites() && animal.height() > TASK11_HEIGHT)
            .toList();
    }

    public static Integer weightMoreThanHeightCount(List<Animal> list) { //Task12
        return (int) list.stream()
            .filter(animal -> animal.weight() > animal.height())
            .count();
    }

    public static List<Animal> moreThanTwoWordNames(List<Animal> list) { //Task13
        return list.stream()
            .filter(animal -> animal.name().split(" ").length > 2)
            .toList();
    }

    public static Boolean isThereADogHigherK(List<Animal> list, int k) { //Task14
        return list.stream()
            .anyMatch(animal -> animal.type() == Animal.Type.DOG && animal.height() > k);
    }

    public static Map<Animal.Type, Integer> kToIAgeAnimalsWeightSum(List<Animal> list, int k, int i) { //Task15
        return list.stream()
            .filter(animal -> animal.age() >= k && animal.age() <= i)
            .collect(Collectors.toMap(
                Animal::type, Animal::weight,
                Math::addExact
            ));
    }

    public static List<Animal> sortedByTypeSexName(List<Animal> list) { //Task16
        return list.stream()
            .sorted(Comparator.comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name))
            .toList();
    }

    public static Boolean spidersBiteMoreOftenThanDogs(List<Animal> list) { //Task17
        boolean isDogExist = list.stream().anyMatch(a -> a.type() == Animal.Type.DOG);
        boolean isSpiderExist = list.stream().anyMatch(a -> a.type() == Animal.Type.SPIDER);

        long biteDogCount = list.stream()
            .filter(animal -> animal.type() == Animal.Type.DOG && animal.bites())
            .count();
        long biteSpiderCount = list.stream()
            .filter(animal -> animal.type() == Animal.Type.SPIDER && animal.bites())
            .count();
        return isDogExist && isSpiderExist && biteSpiderCount > biteDogCount;
    }

    @SafeVarargs public static Animal getHeaviestFish(List<Animal>... lists) { //Task18
        Animal result = null;
        for (List<Animal> list : lists) {
            Optional<Animal> tmp = list.stream()
                .filter(animal -> animal.type() == Animal.Type.FISH)
                .max(Comparator.comparingInt(Animal::weight));
            if (tmp.isPresent() && (result == null || tmp.get().weight() > result.weight())) {
                result = tmp.get();
            }
        }
        return result;
    }

    public static Map<String, Set<ValidationError>> validateAnimals(List<Animal> list) { //Task19
        return list.stream()
            .filter(AnimalValidator::isError)
            .collect(Collectors.toMap(Animal::name, AnimalValidator::validateAnimal));
    }

    public static Map<String, String> friendlyValidateAnimals(List<Animal> list) { //Task20
        return list.stream()
            .filter(AnimalValidator::isError)
            .collect(Collectors.toMap(
                Animal::name,
                animal -> AnimalValidator.validateAnimal(animal).stream()
                    .map(ValidationError::field)
                    .collect(Collectors.joining(", "))
            ));
    }
}

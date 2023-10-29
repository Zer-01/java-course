package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Tasks {
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
            .collect(Collectors.toMap(Animal::type, o->1, Math::addExact));
    }

    public static Animal getLongestName(List<Animal> list) { //Task4
        return list.stream()
            .max(Comparator.comparingInt(o -> o.name().length()))
            .orElse(null);
    }

    public static Animal.Sex getMostPopularSex(List<Animal> list) { //Task5
        return list.stream()
            .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()))
            .entrySet().stream()
            .max((o1, o2) -> (int) (o1.getValue() - o2.getValue()))
            .get().getKey();
    }


}
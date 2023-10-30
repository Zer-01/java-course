package edu.hw4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TasksTest {
    private static List<Animal> inList;

    @BeforeAll
    public static void init() {
        Animal an0 = new Animal("Whisker word2 word3", Animal.Type.CAT, Animal.Sex.M, 3, 12,
            9, true
        );
        Animal an1 = new Animal("Fluffy", Animal.Type.CAT, Animal.Sex.F, 2, 15, 7, true);
        Animal an2 = new Animal("Buddy", Animal.Type.DOG, Animal.Sex.M, 4, 110, 30, true);
        Animal an3 = new Animal("Luna", Animal.Type.DOG, Animal.Sex.F, 3, 90, 28, true);
        Animal an4 = new Animal("Polly word2 word3", Animal.Type.BIRD, Animal.Sex.F, 5, 4,
            6, false
        );
        Animal an5 = new Animal("Kiwi", Animal.Type.BIRD, Animal.Sex.M, 2, 3, 5, false);
        Animal an6 = new Animal("Nemo", Animal.Type.FISH, Animal.Sex.M, 1, 2, 3, false);
        Animal an7 = new Animal("Dory", Animal.Type.FISH, Animal.Sex.F, 1, 3, 4, false);
        Animal an8 = new Animal("Fang", Animal.Type.SPIDER, Animal.Sex.M, 2, 1, 2, true);
        Animal an9 = new Animal("Spi", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, 1, false);
        inList = List.of(an0, an1, an2, an3, an4, an5, an6, an7, an8, an9);
    }

    @Test
    void heightSortTest() { //Task1Test
        var result = Tasks.sortByHeight(inList);

        assertThat(result.size())
            .isEqualTo(10);
        for (int i = 1; i < 10; i++) {
            assertThat(result.get(i - 1).height() <= result.get(i).height())
                .isTrue();
        }
    }

    @Test
    void weightSortTest() { //Task2Test
        long k = 4;
        var result = Tasks.sortByWeight(inList, k);

        assertThat(result.size())
            .isEqualTo(k);
        assertThat(result.get((int) k - 1).name())
            .isEqualTo("Fluffy");
        for (int i = 1; i < k; i++) {
            assertThat(result.get(i - 1).weight() >= result.get(i).weight())
                .isTrue();
        }
    }

    @Test
    void typeCountTest() { //Task3Test
        Map<Animal.Type, Integer> expMap = new HashMap<>();
        expMap.put(Animal.Type.CAT, 2);
        expMap.put(Animal.Type.DOG, 2);
        expMap.put(Animal.Type.BIRD, 2);
        expMap.put(Animal.Type.FISH, 2);
        expMap.put(Animal.Type.SPIDER, 2);

        var result = Tasks.getTypesCount(inList);

        assertThat(result)
            .containsExactlyInAnyOrderEntriesOf(expMap);
    }

    @Test
    void longestNameTest() { //Task4Test
        Animal result = Tasks.getLongestName(inList);

        assertThat(result.name())
            .isEqualTo("Whisker word2 word3");
    }

    @Test
    void longestNameEmptyTest() { //Task4Test
        List<Animal> input = new ArrayList<>();

        Animal result = Tasks.getLongestName(input);

        assertThat(result)
            .isNull();
    }

    @Test
    void Task5Test() { //Task5Test
        var result = Tasks.getMostPopularSex(inList);

        assertThat(result)
            .isEqualTo(Animal.Sex.M);
    }

    @Test
    void weightMapTest() { //Task6Test
        Map<Animal.Type, Animal> expMap = new HashMap<>();
        expMap.put(Animal.Type.CAT, inList.get(0));
        expMap.put(Animal.Type.DOG, inList.get(2));
        expMap.put(Animal.Type.BIRD, inList.get(4));
        expMap.put(Animal.Type.FISH, inList.get(7));
        expMap.put(Animal.Type.SPIDER, inList.get(8));
        var result = Tasks.heaviestAnimalOfEveryType(inList);

        assertThat(result)
            .containsExactlyInAnyOrderEntriesOf(expMap);
    }

    @Test
    void kOldestTest() { //Task7Test
        Animal result = Tasks.getKOldestAnimal(inList, 2);

        assertThat(result)
            .isEqualTo(inList.get(2));
    }

    @Test
    void heaviestBelowKTest() { //Task8Test
        Optional<Animal> result = Tasks.getHeaviestBelowK(inList, 12);

        assertThat(result.get())
            .isEqualTo(inList.get(4));
    }

    @Test
    void countPawsTest() { //Task9Test
        Integer result = Tasks.getPawsCount(inList);

        assertThat(result)
            .isEqualTo(36);
    }

    @Test
    void getListWhereAgeNotEqualPawsCount() { //Task10Test
        List<Animal> expResult = Arrays.asList(inList.get(0), inList.get(1), inList.get(3), inList.get(4),
            inList.get(6), inList.get(7), inList.get(8), inList.get(9)
        );
        var result = Tasks.ageNotEqualPawsCount(inList);

        assertThat(result)
            .containsExactlyInAnyOrderElementsOf(expResult);
    }

    @Test
    void getListOfAnimalThatCanBiteAndHigher100() { //Task11Test
        List<Animal> expResult = Arrays.asList(inList.get(2));
        var result = Tasks.canBiteAndHigher100(inList);

        assertThat(result)
            .containsExactlyInAnyOrderElementsOf(expResult);
    }

    @Test
    void weightMoreThanHeightCountTest() { //Task12Test
        Integer result = Tasks.weightMoreThanHeightCount(inList);

        assertThat(result)
            .isEqualTo(5);
    }

    @Test
    void moreThanTwoWordNamesTest() { //Task13Test
        var expResult = Arrays.asList(inList.get(4), inList.get(0));
        var result = Tasks.moreThanTwoWordNames(inList);

        assertThat(result)
            .containsExactlyInAnyOrderElementsOf(expResult);
    }

    @Test
    void isThereADogHigherKTrueTest() { //Task14Test
        Boolean result = Tasks.isThereADogHigherK(inList, 100);

        assertThat(result)
            .isTrue();
    }

    @Test
    void isThereADogHigherKFalseTest() { //Task14Test
        Boolean result = Tasks.isThereADogHigherK(inList, 150);

        assertThat(result)
            .isFalse();
    }

    @Test
    void KToIAgeAnimalsWeightSumTest() { //Task15Test
        Integer result = Tasks.kToIAgeAnimalsWeightSum(inList, 2, 4);

        assertThat(result)
            .isEqualTo(81);
    }

    @Test
    void sortedByTypeSexNameTest() { //Task16Test
        var expResult = new ArrayList<>(inList);
        expResult.sort(Comparator.comparing(Animal::type)
            .thenComparing(Animal::sex)
            .thenComparing(Animal::name));

        var result = Tasks.sortedByTypeSexName(inList);

        assertThat(result)
            .isEqualTo(expResult);
    }

    @Test
    void spidersBiteMoreOftenThanDogsTest() { //Task17Test
        var result = Tasks.spidersBiteMoreOftenThanDogs(inList);

        assertThat(result)
            .isFalse();
    }

    @Test
    void spidersBiteMoreOftenThanDogsNotEnoughDataTest() { //Task17Test
        var testList = Arrays.asList(inList.get(8));
        var result = Tasks.spidersBiteMoreOftenThanDogs(testList);

        assertThat(result)
            .isFalse();
    }

    @Test
    void heaviestFishSearchTest() { //Task18Test
        Animal testFish = new Animal("Test Fish", Animal.Type.FISH, Animal.Sex.F, 1, 3, 10,
            false
        );
        var inList2 = new ArrayList<>(inList);
        inList2.add(testFish);

        var result = Tasks.getHeaviestFish(inList, inList2);

        assertThat(result)
            .isEqualTo(testFish);
    }

    @Test
    void validateAnimalsTest() { //Task19Test
        List<Animal> input = Arrays.asList(
            new Animal("Dory", Animal.Type.FISH, Animal.Sex.F, -1, 3, 4, false),
            new Animal("Fang", Animal.Type.SPIDER, Animal.Sex.M, 2, 1, 2, true),
            new Animal("Spi", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, 0, false)
        );
        Map<String, Set<ValidationError>> expResult = new HashMap<>();
        expResult.put(input.get(0).name(), AnimalValidator.validateAnimal(input.get(0)));
        expResult.put(input.get(2).name(), AnimalValidator.validateAnimal(input.get(2)));

        var result = Tasks.validateAnimals(input);

        assertThat(result)
            .containsExactlyInAnyOrderEntriesOf(expResult);
    }

    @Test
    void friendlyValidateAnimalsTest() { //Task20Test
        List<Animal> input = Arrays.asList(
            new Animal("Dory", Animal.Type.FISH, Animal.Sex.F, -1, -1, 4, false),
            new Animal("Fang", Animal.Type.SPIDER, Animal.Sex.M, 2, 1, 2, true),
            new Animal("Spi", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, 0, false)
        );
        Map<String, String> expResult = new HashMap<>();
        expResult.put("Dory", "age, height");
        expResult.put("Spi", "weight");

        var result = Tasks.friendlyValidateAnimals(input);

        assertThat(result)
            .containsExactlyInAnyOrderEntriesOf(expResult);
    }
}

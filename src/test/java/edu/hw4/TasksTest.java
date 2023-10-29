package edu.hw4;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

public class TasksTest {
    private static List<Animal> inList;

    @BeforeAll
    public static void init(){
        Animal an1 = new Animal("Whisker", Animal.Type.CAT, Animal.Sex.M, 3, 12, 9, true);
        Animal an2 = new Animal("Fluffy", Animal.Type.CAT, Animal.Sex.F, 2, 10, 7, true);
        Animal an3 = new Animal("Buddy", Animal.Type.DOG, Animal.Sex.M, 4, 18, 30, true);
        Animal an4 = new Animal("Luna", Animal.Type.DOG, Animal.Sex.F, 3, 16, 28, true);
        Animal an5 = new Animal("Polly", Animal.Type.BIRD, Animal.Sex.F, 5, 4, 6, false);
        Animal an6 = new Animal("Kiwi", Animal.Type.BIRD, Animal.Sex.M, 1, 3, 5, false);
        Animal an7 = new Animal("Nemo", Animal.Type.FISH, Animal.Sex.M, 1, 2, 4, false);
        Animal an8 = new Animal("Dory", Animal.Type.FISH, Animal.Sex.F, 1, 3, 3, false);
        Animal an9 = new Animal("Fang", Animal.Type.SPIDER, Animal.Sex.M, 2, 1, 2, true);
        Animal an10 = new Animal("Spi", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, 1, false);
        inList = List.of(an1, an2, an3, an4, an5, an6, an7, an8, an9, an10);
    }

    @Test
    void heightSortTest() {
        var result = Tasks.sortByHeight(inList);

        assertThat(result.size())
            .isEqualTo(10);
        for(int i = 1; i<10;i++)
            assertThat(result.get(i - 1).height() <= result.get(i).height())
                .isTrue();
    }

    @Test
    void weightSortTest() {
        long k = 4;
        var result = Tasks.sortByWeight(inList, k);

        assertThat(result.size())
            .isEqualTo(k);
        assertThat(result.get((int)k - 1).name())
            .isEqualTo("Fluffy");
        for(int i = 1; i<k;i++)
            assertThat(result.get(i - 1).weight() >= result.get(i).weight())
                .isTrue();
    }

    @Test
    void typeCountTest() {
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
    void longestNameTest() {
        Animal result = Tasks.getLongestName(inList);

        assertThat(result.name())
            .isEqualTo("Whisker");
    }

    @Test
    void longestNameEmptyTest() {
        List<Animal> input = new ArrayList<>();

        Animal result = Tasks.getLongestName(input);

        assertThat(result)
            .isNull();
    }

    @Test
    void Task5Test() {
        var result = Tasks.getMostPopularSex(inList);

        assertThat(result)
            .isEqualTo(Animal.Sex.M);
    }
}

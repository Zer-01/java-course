package edu.hw7;

import edu.hw7.Task3.Person;
import edu.hw7.Task3.PersonDatabase;
import edu.hw7.Task3.RWLockDatabase;
import edu.hw7.Task3.SyncDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    private static final int PERSON_LIST_SIZE = 3_000_000;
    volatile boolean isFinished = false;
    AtomicInteger errorsCount = new AtomicInteger();

    static Arguments[] databases() {
        return new Arguments[] {
            Arguments.of(new RWLockDatabase()),
            Arguments.of(new SyncDatabase())
        };
    }

    @ParameterizedTest
    @MethodSource("databases")
    void multiThreadTest(PersonDatabase database) throws InterruptedException {
        List<Person> list = personListGenerator();
        Thread[] readers = new Thread[4];

        for (int thr = 0; thr < 4; thr++) {
            readers[thr] = new Thread(() -> {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                Person tmp = list.get(0);
                while (!isFinished) {
                    if (database.findByName(tmp.name()) != null) {
                        if (database.findByAddress(tmp.address()) == null ||
                            database.findByPhone(tmp.phoneNumber()) == null) {
                            errorsCount.incrementAndGet();
                        }
                    }
                    tmp = list.get(random.nextInt(0, PERSON_LIST_SIZE));
                }
            });
        }

        Thread writer = new Thread(() -> {
            Person tmp = list.get(0);
            for (int i = 1; i < PERSON_LIST_SIZE; i++) {
                database.add(tmp);
                tmp = list.get(i);
            }
            isFinished = true;
        });

        for (int thr = 0; thr < 4; thr++) {
            readers[thr].start();
        }
        writer.start();

        writer.join();
        for (int thr = 0; thr < 4; thr++) {
            readers[thr].join();
        }

        assertThat(errorsCount.get())
            .isZero();
    }

    @ParameterizedTest
    @MethodSource("databases")
    void oneThreadTest(PersonDatabase database) {
        database.add(new Person(1, "Name1", "address1", "number1"));
        database.add(new Person(2, "Name2", "address", "number2"));
        database.add(new Person(3, "Name3", "address", "number1"));
        database.delete(1);

        assertThat(database.findByName("Name2"))
            .hasSize(1);
        assertThat(database.findByPhone("number1"))
            .hasSize(1);
        assertThat(database.findByAddress("address"))
            .hasSize(2);
    }

    List<Person> personListGenerator() {
        List<Person> list = new ArrayList<>(PERSON_LIST_SIZE);
        for (int i = 0; i < PERSON_LIST_SIZE; i++) {
            list.add(new Person(i, "Name" + i, "address" + i, "Number" + i));
        }
        return list;
    }
}

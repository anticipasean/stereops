package cyclops.reactive.collection.container;

import cyclops.reactive.collection.container.mutable.DequeX;
import cyclops.reactive.collection.container.mutable.ListX;
import cyclops.reactive.collection.container.mutable.QueueX;
import cyclops.reactive.collection.container.mutable.SetX;
import cyclops.reactive.collection.container.mutable.SortedSetX;
import cyclops.reactive.collection.companion.CyclopsCollectors;
import java.util.Arrays;
import org.junit.Test;

public class ExampleTest {

    @Test(expected = UnsupportedOperationException.class)
    public void immutable() {

        ListX.fromIterable(ListX.immutableCollector(),
                           Arrays.asList(1,
                                         2,
                                         3,
                                         4))
             .plus(5);
    }

    @Test
    public void list() {

        ListX.of(1,
                 2,
                 3)
             .map(i -> i + 2)
             .plus(5)
             .map(i -> "hello" + i)
             .forEach(System.out::println);
    }

    @Test
    public void listToSetX() {
        SetX<String> set = ListX.of(1,
                                    2,
                                    3)
                                .concatMap(i -> Arrays.asList(i + 2,
                                                              10))
                                .plus(5)
                                .map(i -> "hello" + i)
                                .toSetX()
                                .collect(CyclopsCollectors.toSetX());

        set.printOut();

    }

    @Test
    public void listFlatMap() {
        ListX.of(1,
                 2,
                 3)
             .concatMap(i -> Arrays.asList(i + 2,
                                           10))
             .plus(5)
             .map(i -> "hello" + i)
             .forEach(System.out::println);

    }

    @Test
    public void deque() {
        DequeX.of(1,
                  2,
                  3)
              .map(i -> i + 2)
              .plus(5)
              .map(i -> "hello" + i)
              .forEach(System.out::println);

    }

    @Test
    public void set() {
        SetX.of(1,
                2,
                3)
            .map(i -> i + 2)
            .plus(5)
            .map(i -> "hello" + i)
            .forEach(System.out::println);
    }

    @Test
    public void sortedSet() {
        SortedSetX.of(1,
                      2,
                      3)
                  .map(i -> i + 2)
                  .plus(5)
                  .map(i -> "hello" + i)
                  .forEach(System.out::println);
    }

    @Test
    public void queue() {
        QueueX.of(1,
                  2,
                  3)
              .map(i -> i + 2)
              .plus(5)
              .map(i -> "hello" + i)
              .forEach(System.out::println);
    }
}

package cyclops.reactive.collection.container.immutable;

import cyclops.reactive.collection.container.immutable.BagX;
import cyclops.reactive.collection.container.immutable.LinkedListX;
import cyclops.reactive.collection.container.immutable.OrderedSetX;
import cyclops.reactive.collection.container.immutable.PersistentQueueX;
import cyclops.reactive.collection.container.immutable.PersistentSetX;
import cyclops.reactive.collection.container.immutable.VectorX;
import org.junit.Test;

public class PersistentExample {

    @Test
    public void list() {
        VectorX.of(1,
                   2,
                   3)
               .map(i -> i + 2)
               .plus(5)
               .map(i -> "hello" + i)
               .forEach(System.out::println);

    }

    @Test
    public void stack() {
        LinkedListX.of(1,
                       2,
                       3)
                   .map(i -> i + 2)
                   .plus(5)
                   .map(i -> "hello" + i)
                   .forEach(System.out::println);

    }

    @Test
    public void set() {
        PersistentSetX.of(1,
                          2,
                          3)
                      .map(i -> i + 2)
                      .plus(5)
                      .map(i -> "hello" + i)
                      .forEach(System.out::println);
    }

    @Test
    public void bag() {
        BagX.of(1,
                2,
                3)
            .map(i -> i + 2)
            .plus(5)
            .map(i -> "hello" + i)
            .forEach(System.out::println);
    }

    @Test
    public void orderedSet() {
        OrderedSetX.of(1,
                       2,
                       3)
                   .map(i -> i + 2)
                   .plus(5)
                   .map(i -> "hello" + i)
                   .forEach(System.out::println);
    }

    @Test
    public void queue() {
        PersistentQueueX.of(1,
                            2,
                            3)
                        .map(i -> i + 2)
                        .plus(5)
                        .map(i -> "hello" + i)
                        .forEach(System.out::println);
    }
}

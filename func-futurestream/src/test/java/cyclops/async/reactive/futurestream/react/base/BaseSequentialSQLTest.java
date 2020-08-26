package cyclops.async.reactive.futurestream.react.base;

import static cyclops.container.immutable.tuple.Tuple.tuple;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import cyclops.async.reactive.futurestream.FutureStream;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import org.junit.Test;

public abstract class BaseSequentialSQLTest {

    BiPredicate TRUE = (t, u) -> true;

    abstract protected <U> FutureStream<U> of(U... array);

    abstract protected <U> FutureStream<U> ofThread(U... array);

    abstract protected <U> FutureStream<U> react(Supplier<U>... array);

    @Test
    public void testCrossJoin() {

        // {A} x {B}
        // ---------------------------------------------------------------------
        assertEquals(asList(),
                     of().crossJoin(of())
                         .toList());
        assertEquals(asList(),
                     of().crossJoin(of(1))
                         .toList());
        assertEquals(asList(),
                     of().crossJoin(of(1,
                                       2))
                         .toList());

        assertEquals(asList(),
                     of("A").crossJoin(of())
                            .toList());
        assertEquals(asList(tuple("A",
                                  1)),
                     of("A").crossJoin(of(1))
                            .toList());
        assertEquals(asList(tuple("A",
                                  1),
                            tuple("A",
                                  2)),
                     of("A").crossJoin(of(1,
                                          2))
                            .toList());

        assertEquals(asList(),
                     of("A",
                        "B").crossJoin(of())
                            .toList());
        assertEquals(asList(tuple("A",
                                  1),
                            tuple("B",
                                  1)),
                     of("A",
                        "B").crossJoin(of(1))
                            .toList());
        assertEquals(asList(tuple("A",
                                  1),
                            tuple("A",
                                  2),
                            tuple("B",
                                  1),
                            tuple("B",
                                  2)),
                     of("A",
                        "B").crossJoin(of(1,
                                          2))
                            .toList());

        assertEquals(asList(),
                     of("A",
                        "B",
                        "C").crossJoin(of())
                            .toList());
        assertEquals(asList(tuple("A",
                                  1),
                            tuple("B",
                                  1),
                            tuple("C",
                                  1)),
                     of("A",
                        "B",
                        "C").crossJoin(of(1))
                            .toList());
        assertEquals(asList(tuple("A",
                                  1),
                            tuple("A",
                                  2),
                            tuple("B",
                                  1),
                            tuple("B",
                                  2),
                            tuple("C",
                                  1),
                            tuple("C",
                                  2)),
                     of("A",
                        "B",
                        "C").crossJoin(of(1,
                                          2))
                            .toList());


    }


    @Test
    public void testOnEmpty() throws X {
        assertEquals(asList(1),
                     of().onEmpty(1)
                         .toList());
        assertEquals(asList(1),
                     of().onEmptyGet(() -> 1)
                         .toList());

        assertEquals(asList(2),
                     of(2).onEmpty(1)
                          .toList());
        assertEquals(asList(2),
                     of(2).onEmptyGet(() -> 1)
                          .toList());
        assertEquals(asList(2),
                     of(2).onEmptyError(() -> new X())
                          .toList());

        assertEquals(asList(2,
                            3),
                     of(2,
                        3).onEmpty(1)
                          .toList());
        assertEquals(asList(2,
                            3),
                     of(2,
                        3).onEmptyGet(() -> 1)
                          .toList());
        assertEquals(asList(2,
                            3),
                     of(2,
                        3).onEmptyError(() -> new X())
                          .toList());
    }

    @Test
    public void testConcat() {

        assertEquals(asList(1,
                            2,
                            3,
                            4),
                     of(1).concat(of(2,
                                     3,
                                     4))
                          .toList());
        assertEquals(asList(1,
                            2,
                            3,
                            4),
                     of(1,
                        2).concat(of(3,
                                     4))
                          .toList());
        assertEquals(asList(1,
                            2,
                            3,
                            4),
                     of(1).concat(2,
                                  3,
                                  4)
                          .toList());
        assertEquals(asList(1,
                            2,
                            3,
                            4),
                     of(1,
                        2).concat(3,
                                  4)
                          .toList());
    }

    @SuppressWarnings("serial")
    public class X extends Exception {

    }

}

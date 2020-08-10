package com.oath.cyclops.comprehensions.donotation.typed;

import static cyclops.container.immutable.tuple.Tuple.tuple;
import static cyclops.reactive.ReactiveSeq.range;

import cyclops.reactive.ReactiveSeq;
import org.junit.Test;

public class DoTest {

    @Test
    public void doGen2() {

        ReactiveSeq.range(1,
                          10)
                   .forEach2(i -> range(0,
                                        i),
                             (i, j) -> tuple(i,
                                             j));

        //  .forEach(System.out::println);

    }

}

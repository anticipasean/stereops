package com.oath.cyclops.streams;

import cyclops.container.foldable.Foldable;
import cyclops.reactive.ReactiveSeq;
import cyclops.streams.CollectableTest;


public class ReactiveSeqCollectableTest extends CollectableTest {

    @Override
    public <T> Foldable<T> of(T... values) {
        return ReactiveSeq.of(values);
    }

}

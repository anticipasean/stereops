package com.oath.cyclops.streams;

import cyclops.container.foldable.Folds;
import cyclops.reactive.ReactiveSeq;
import cyclops.streams.CollectableTest;


public class ReactiveSeqCollectableTest extends CollectableTest {

    @Override
    public <T> Folds<T> of(T... values) {
        return ReactiveSeq.of(values);
    }

}

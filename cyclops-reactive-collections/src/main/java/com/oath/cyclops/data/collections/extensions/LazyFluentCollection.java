package com.oath.cyclops.data.collections.extensions;

import cyclops.container.persistent.PersistentCollection;
import cyclops.reactive.ReactiveSeq;

public interface LazyFluentCollection<T, C extends PersistentCollection<T>> {

    C get();

    ReactiveSeq<T> stream();


}

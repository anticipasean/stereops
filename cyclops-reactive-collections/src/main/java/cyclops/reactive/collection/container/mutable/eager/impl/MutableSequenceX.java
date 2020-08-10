package cyclops.reactive.collection.container.mutable.eager.impl;

import cyclops.reactive.collection.container.CollectionX;
import cyclops.reactive.collection.container.IndexedSequenceX;

/**
 * @param <T> type of data held in this Collection
 * @author johnmcclean
 */
public interface MutableSequenceX<T> extends CollectionX<T>, IndexedSequenceX<T> {

    @Override
    MutableSequenceX<T> plus(T e);

    @Override
    MutableSequenceX<T> plusAll(Iterable<? extends T> list);

    @Override
    MutableSequenceX<T> insertAt(int i,
                                 T e);


    @Override
    MutableSequenceX<T> insertAt(int i,
                                 Iterable<? extends T> list);

    @Override
    MutableSequenceX<T> removeValue(T e);


    @Override
    MutableSequenceX<T> removeAll(Iterable<? extends T> list);

    @Override
    MutableSequenceX<T> removeAt(long i);


}

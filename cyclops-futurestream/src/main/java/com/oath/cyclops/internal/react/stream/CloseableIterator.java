package com.oath.cyclops.internal.react.stream;

import com.oath.cyclops.async.adapters.Queue;
import com.oath.cyclops.react.async.subscription.Continueable;
import java.util.Iterator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CloseableIterator<T> implements Iterator<T> {

    private final Iterator<T> iterator;
    private final Continueable subscription;
    private final Queue queue;

    @Override
    public boolean hasNext() {
        if (!iterator.hasNext()) {
            close();
        }
        return iterator.hasNext();
    }

    public void close() {
        subscription.closeAll(queue);
    }

    @Override
    public T next() {
        final T next = iterator.next();
        return next;
    }

}

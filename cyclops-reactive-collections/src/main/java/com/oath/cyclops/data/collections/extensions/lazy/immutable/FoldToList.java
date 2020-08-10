package com.oath.cyclops.data.collections.extensions.lazy.immutable;

import cyclops.container.persistent.PersistentList;
import java.util.Iterator;


public interface FoldToList<T> {

    PersistentList<T> from(final Iterator<T> i,
                           int depth);
}

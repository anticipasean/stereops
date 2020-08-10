package cyclops.pure.typeclasses.taglessfinal;

import cyclops.function.higherkinded.Higher;
import cyclops.container.control.Option;

public interface StoreAlgebra<W,K,V> {
    Higher<W, Option<V>> get(K key);
    Higher<W, Void> put(K key, V value);

}

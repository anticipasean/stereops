package cyclops.pure.typeclasses.taglessfinal;

import cyclops.function.higherkinded.DataWitness.io;
import cyclops.function.higherkinded.Higher;
import cyclops.container.control.eager.option.Option;
import cyclops.container.immutable.impl.HashMap;
import cyclops.reactive.IO;

public class StoreIO<K,V> implements StoreAlgebra<io,K,V> {
    private  HashMap<K,V> map = HashMap.empty();
    @Override
    public Higher<io, Option<V>> get(K key) {
        return IO.of(map.get(key));
    }

    @Override
    public Higher<io, Void> put(K key, V value) {
        map = map.put(key,value);

        return IO.of((Void)null);
    }
}

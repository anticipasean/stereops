package cyclops.typeclasses.taglessfinal;

import cyclops.function.hkt.DataWitness.io;
import cyclops.function.hkt.Higher;
import cyclops.control.Option;
import cyclops.container.persistent.impl.HashMap;
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

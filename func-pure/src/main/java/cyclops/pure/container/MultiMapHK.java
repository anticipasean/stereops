package cyclops.pure.container;


import cyclops.container.immutable.impl.HashMap;
import cyclops.function.higherkinded.DataWitness.multiMapHK;
import cyclops.function.higherkinded.Higher;
import cyclops.function.higherkinded.Higher2;
import cyclops.container.control.eager.option.Option;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.pure.typeclasses.Pure;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/*
 Higher kinded multimap
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultiMapHK<W, K, V> implements Higher2<multiMapHK, K, V> {

    private final HashMap<K, Higher<W, V>> multiMap;
    private final Appender<W, V> appender;
    private final Pure<W> pure;

    public static <W, K, V> MultiMapHK<W, K, V> empty(Appender<W, V> appender,
                                                      Pure<W> pure) {
        return new MultiMapHK<>(HashMap.empty(),
                                appender,
                                pure);
    }

    public MultiMapHK<W, K, V> put(K key,
                                   V value) {
        Higher<W, V> hkt = multiMap.get(key)
                                   .map(v -> appender.append(v,
                                                             value))
                                   .orElseGet(() -> pure.unit(value));
        return new MultiMapHK<>(multiMap.put(key,
                                             hkt),
                                appender,
                                pure);
    }

    public <R> Option<R> get(K key,
                             Function<? super Higher<W, V>, ? extends R> decoder) {
        return multiMap.get(key)
                       .map(decoder);
    }

    public Option<Higher<W, V>> get(K key) {
        return multiMap.get(key);
    }

    public boolean containsKey(K key) {
        return multiMap.containsKey(key);
    }

    public boolean contains(Tuple2<K, Higher<W, V>> keyAndValue) {
        return multiMap.contains(keyAndValue);
    }

    @FunctionalInterface
    public interface Appender<W, V> {

        Higher<W, V> append(Higher<W, V> container,
                            V value);
    }
}

package cyclops.immutables.encoding;

import cyclops.container.immutable.ImmutableMap;
import cyclops.container.immutable.impl.TreeMap;
import cyclops.container.immutable.tuple.Tuple2;
import java.util.stream.StreamSupport;
import org.immutables.encode.Encoding;

@Encoding
class CyclopsSortedMapEncoding<K extends Comparable<K>, V> {

    @Encoding.Impl
    private final TreeMap<K, V> field = TreeMap.empty(Comparable::compareTo);

    CyclopsSortedMapEncoding() {

    }

    @Encoding.Builder
    static final class Builder<K extends Comparable<K>, V> {

        private TreeMap<K, V> map = TreeMap.empty(Comparable::compareTo);

        Builder() {

        }

        @Encoding.Naming(standard = Encoding.StandardNaming.PUT)
        @Encoding.Init
        void put(final K key,
                 final V value) {
            this.map = this.map.put(key,
                                    value);
        }

        @Encoding.Naming(value = "putEntry*", depluralize = true)
        @Encoding.Init
        void putEntry(final Tuple2<K, V> entry) {
            this.map = this.map.put(entry);
        }

        @Encoding.Init
        @Encoding.Copy
        void set(final TreeMap<K, V> elements) {
            this.map = elements;
        }

        @Encoding.Naming(value = "setJavaMap*")
        @Encoding.Init
        void setJavaMap(final java.util.Map<K, V> in_map) {
            this.map = TreeMap.fromMap(Comparable::compareTo,
                                       in_map);
        }

        @Encoding.Naming(value = "setMap*")
        @Encoding.Init
        void setMap(final ImmutableMap<K, V> in_map) {
            this.map = TreeMap.fromMap(Comparable::compareTo,
                                       in_map);
        }

        @Encoding.Naming(value = "setEntries*")
        @Encoding.Init
        void setEntries(final Iterable<Tuple2<K, V>> entries) {
            this.map = TreeMap.fromStream(StreamSupport.stream(entries.spliterator(),
                                                               false),
                                          Comparable::compareTo);
        }

        @Encoding.Build
        TreeMap<K, V> build() {
            return this.map;
        }
    }
}

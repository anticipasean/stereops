package cyclops.immutables.encoding;

import cyclops.container.immutable.ImmutableMap;
import cyclops.container.immutable.impl.LinkedMap;
import cyclops.container.immutable.tuple.Tuple2;
import java.util.stream.StreamSupport;
import org.immutables.encode.Encoding;

@Encoding
class CyclopsLinkedMapEncoding<K, V> {

    @Encoding.Impl
    private final LinkedMap<K, V> field = LinkedMap.empty();

    CyclopsLinkedMapEncoding() {

    }

    @Encoding.Builder
    static final class Builder<K, V> {

        private LinkedMap<K, V> map = LinkedMap.empty();

        Builder() {

        }

        @Encoding.Naming(standard = Encoding.StandardNaming.PUT)
        @Encoding.Init
        void put(final K key,
                 final V value) {
            this.map = this.map.put(key,
                                    value);
        }

        @Encoding.Naming(standard = Encoding.StandardNaming.ADD_ALL)
        @Encoding.Init
        void putEntry(final Tuple2<K, V> entry) {
            this.map = (LinkedMap<K, V>) this.map.put(entry);
        }

        @Encoding.Init
        @Encoding.Copy
        void set(final LinkedMap<K, V> elements) {
            this.map = elements;
        }

        @Encoding.Naming(value = "setJavaMap*")
        @Encoding.Init
        void setJavaMap(final java.util.Map<K, V> in_map) {
            this.map = LinkedMap.fromMap(in_map);
        }

        @Encoding.Naming(value = "setMap*")
        @Encoding.Init
        void setMap(final ImmutableMap<K, V> in_map) {
            this.map = LinkedMap.fromMap(in_map);
        }

        @Encoding.Naming(value = "setEntries*")
        @Encoding.Init
        void setEntries(final Iterable<Tuple2<K, V>> entries) {
            this.map = LinkedMap.fromStream(StreamSupport.stream(entries.spliterator(),
                                                                 false));
        }

        @Encoding.Build
        LinkedMap<K, V> build() {
            return this.map;
        }
    }
}

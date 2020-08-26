package cyclops.container.immutable.impl;

import cyclops.container.basetests.BaseImmutableMapTest;
import cyclops.container.immutable.ImmutableMap;
import cyclops.container.immutable.tuple.Tuple;
import cyclops.container.immutable.tuple.Tuple2;
import java.util.Map;
import java.util.stream.Stream;


public class ImmutableTrieMapTest extends BaseImmutableMapTest {


    @Override
    protected <K, V> ImmutableMap<K, V> empty() {
        return TrieMap.empty();
    }

    @Override
    protected <K, V> ImmutableMap<K, V> of(K k1,
                                           V v1) {
        return TrieMap.of(k1,
                          v1);
    }

    @Override
    protected <K, V> ImmutableMap<K, V> of(K k1,
                                           V v1,
                                           K k2,
                                           V v2) {
        return TrieMap.of(k1,
                          v1,
                          k2,
                          v2);
    }

    @Override
    protected ImmutableMap<String, Integer> fromMap(Map<String, Integer> map) {
        Stream<Tuple2<String, Integer>> s = map.entrySet()
                                               .stream()
                                               .map(e -> Tuple.tuple(e.getKey(),
                                                                     e.getValue()));
        TrieMap<String, Integer> x = TrieMap.fromStream(s);
        return x;
    }
}
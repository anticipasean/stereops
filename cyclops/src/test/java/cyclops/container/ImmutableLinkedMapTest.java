package cyclops.container;

import cyclops.container.basetests.BaseImmutableMapTest;
import cyclops.container.immutable.ImmutableMap;
import cyclops.container.immutable.impl.LinkedMap;
import cyclops.container.immutable.tuple.Tuple;
import cyclops.container.immutable.tuple.Tuple2;
import java.util.Map;
import java.util.stream.Stream;


public class ImmutableLinkedMapTest extends BaseImmutableMapTest {


    @Override
    protected <K, V> ImmutableMap<K, V> empty() {
        return LinkedMap.empty();
    }

    @Override
    protected <K, V> ImmutableMap<K, V> of(K k1,
                                           V v1) {
        return LinkedMap.of(k1,
                            v1);
    }

    @Override
    protected <K, V> ImmutableMap<K, V> of(K k1,
                                           V v1,
                                           K k2,
                                           V v2) {
        return LinkedMap.of(k1,
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
        LinkedMap<String, Integer> x = LinkedMap.fromStream(s);
        return x;
    }
}
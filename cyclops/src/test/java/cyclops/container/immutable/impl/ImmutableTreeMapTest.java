package cyclops.container.immutable.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import cyclops.function.companion.Comparators;
import cyclops.container.basetests.BaseImmutableMapTest;
import cyclops.container.immutable.ImmutableMap;
import cyclops.container.immutable.tuple.Tuple;
import cyclops.container.immutable.tuple.Tuple2;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.Ignore;
import org.junit.Test;


public class ImmutableTreeMapTest extends BaseImmutableMapTest {


    @Override
    protected <K, V> ImmutableMap<K, V> empty() {
        return TreeMap.empty(Comparators.naturalOrderIdentityComparator());
    }

    @Override
    protected <K, V> ImmutableMap<K, V> of(K k1,
                                           V v1) {
        return TreeMap.of(Comparators.naturalOrderIdentityComparator(),
                          k1,
                          v1);
    }

    @Override
    protected <K, V> ImmutableMap<K, V> of(K k1,
                                           V v1,
                                           K k2,
                                           V v2) {
        return TreeMap.of(Comparators.naturalOrderIdentityComparator(),
                          k1,
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
        TreeMap<String, Integer> x = TreeMap.fromStream(s,
                                                        Comparators.naturalOrderIdentityComparator());
        return x;
    }

    @Test
    @Ignore
    public void add50000Entries() {
        ImmutableMap<Integer, Integer> map = empty();
        for (int i = 0; i < 50000; i++) {
            map = map.put(i,
                          i * 2);
        }
        assertThat(map.size(),
                   equalTo(50000));
        putAndCompare(map);
    }

    @Test
    public void insertionOrder() {
        ImmutableMap<Integer, Integer> map1 = empty();
        ImmutableMap<Integer, Integer> map2 = empty();
        for (int i = 0; i <= 1000; i++) {
            map1 = map1.put(i,
                            i);
            map2 = map2.put(1000 - i,
                            1000 - i);
        }
        assertEquals(map1,
                     map2);
        assertEquals(map1.hashCode(),
                     map2.hashCode());

    }

}

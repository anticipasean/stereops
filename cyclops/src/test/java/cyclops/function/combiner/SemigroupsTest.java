package cyclops.function.combiner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import cyclops.container.immutable.impl.Chain;
import cyclops.container.immutable.impl.IntMap;
import cyclops.container.immutable.impl.LazySeq;
import cyclops.container.immutable.impl.NonEmptyChain;
import cyclops.container.immutable.impl.Seq;
import cyclops.container.immutable.impl.Vector;
import cyclops.function.companion.Semigroups;
import cyclops.reactive.ReactiveSeq;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;

public class SemigroupsTest {


    @Test
    public void testChainConcat() {
        Chain<Integer> list = Chain.empty();
        list = list.plus(1);
        list = list.plus(2);
        list = list.plus(4);
        Semigroup<Chain<Integer>> combiner = Semigroups.chainConcat();
        assertThat(combiner.apply(list,
                                  Chain.of(4,
                                           5,
                                           6))
                           .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         4,
                                         4,
                                         5,
                                         6)));
    }

    @Test
    public void testNonEmptyChainConcat() {
        NonEmptyChain<Integer> list = Chain.of(1,
                                               2,
                                               4);

        Semigroup<Chain<Integer>> combiner = Semigroups.chainConcat();
        assertThat(combiner.apply(list,
                                  Chain.of(4,
                                           5,
                                           6))
                           .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         4,
                                         4,
                                         5,
                                         6)));
    }

    @Test
    public void testCollectionConcatPVector() {
        Vector<Integer> list = Vector.empty();
        list = list.plus(1);
        list = list.plus(2);
        list = list.plus(4);
        Semigroup<Vector<Integer>> combiner = Semigroups.vectorConcat();
        assertThat(combiner.apply(list,
                                  Vector.of(4,
                                            5,
                                            6))
                           .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         4,
                                         4,
                                         5,
                                         6)));
    }

    @Test
    public void testCollectionConcatSeq() {
        Seq<Integer> list = Seq.of(1,
                                   2,
                                   4);
        Semigroup<Seq<Integer>> combiner = Semigroups.seqConcat();
        assertThat(combiner.apply(list,
                                  Seq.of(4,
                                         5,
                                         6))
                           .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         4,
                                         4,
                                         5,
                                         6)));
    }

    @Test
    public void testCollectionConcatLazySeq() {
        LazySeq<Integer> list = LazySeq.of(1,
                                           2,
                                           4);
        Semigroup<LazySeq<Integer>> combiner = Semigroups.lazySeqConcat();
        assertThat(combiner.apply(list,
                                  LazySeq.of(4,
                                             5,
                                             6))
                           .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         4,
                                         4,
                                         5,
                                         6)));
    }

    @Test
    public void testCollectionConcatIntMap() {
        IntMap<Integer> list = IntMap.of(1,
                                         2,
                                         4);
        Semigroup<IntMap<Integer>> combiner = Semigroups.intMapConcat();
        assertThat(combiner.apply(list,
                                  IntMap.of(4,
                                            5,
                                            6))
                           .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         4,
                                         4,
                                         5,
                                         6)));
    }

    @Test
    public void testCollectionConcatPVector2() {
        Vector<Integer> list = Vector.empty();
        list = list.plus(1);
        list = list.plus(2);
        list = list.plus(4);
        Semigroup<Vector<Integer>> combiner = Semigroups.vectorConcat();
        assertThat(combiner.apply(Vector.of(4,
                                            5,
                                            6),
                                  list)
                           .toList(),
                   equalTo(Arrays.asList(4,
                                         5,
                                         6,
                                         1,
                                         2,
                                         4)));
    }

    @Test
    public void testCombineReactiveSeq() {
        assertThat(Semigroups.combineReactiveSeq()
                             .apply(ReactiveSeq.of(1,
                                                   2,
                                                   3),
                                    ReactiveSeq.of(4,
                                                   5,
                                                   6))
                             .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3,
                                         4,
                                         5,
                                         6)));
    }


    @Test
    public void testCombineStream() {
        assertThat(Semigroups.combineStream()
                             .apply(Stream.of(1,
                                              2,
                                              3),
                                    Stream.of(4,
                                              5,
                                              6))
                             .collect(Collectors.toList()),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3,
                                         4,
                                         5,
                                         6)));
    }

}

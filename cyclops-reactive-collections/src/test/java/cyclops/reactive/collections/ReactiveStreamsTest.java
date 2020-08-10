package cyclops.reactive.collections;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import cyclops.companion.Streamable;
import cyclops.reactive.ReactiveSeq;
import cyclops.reactive.companion.Spouts;
import cyclops.reactive.collections.immutable.BagX;
import cyclops.reactive.collections.immutable.LinkedListX;
import cyclops.reactive.collections.immutable.OrderedSetX;
import cyclops.reactive.collections.immutable.PersistentSetX;
import cyclops.reactive.collections.immutable.VectorX;
import cyclops.reactive.collections.mutable.DequeX;
import cyclops.reactive.collections.mutable.ListX;
import cyclops.reactive.collections.mutable.QueueX;
import cyclops.reactive.collections.mutable.SetX;
import cyclops.reactive.collections.mutable.SortedSetX;
import java.util.Arrays;
import org.junit.Test;
import reactor.core.publisher.Flux;

public class ReactiveStreamsTest {

    @Test
    public void subscribeToEmpty() {

        Spouts.from(ReactiveSeq.<Integer>empty())
              .forEach(System.out::println);

    }

    @Test
    public void subscribeToFlux() {

        assertThat(Spouts.from(Flux.just(1,
                                         2,
                                         3))
                         .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));
    }

    @Test
    public void fromFluxReactiveSeq() {
        assertThat(ReactiveSeq.fromPublisher(Flux.just(1,
                                                       2,
                                                       3))
                              .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));
    }

    @Test
    public void fromFluxListX() {
        assertThat(ListX.fromPublisher(Flux.just(1,
                                                 2,
                                                 3))
                        .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));
    }

    @Test
    public void fromFluxSetX() {
        assertThat(SetX.fromPublisher(Flux.just(1,
                                                2,
                                                3))
                       .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));
    }

    @Test
    public void fromFluxQueueX() {
        assertThat(QueueX.fromPublisher(Flux.just(1,
                                                  2,
                                                  3))
                         .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));
    }

    @Test
    public void fromFluxDequeX() {
        assertThat(DequeX.fromPublisher(Flux.just(1,
                                                  2,
                                                  3))
                         .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));
    }

    @Test
    public void fromFluxSortedSetX() {
        assertThat(SortedSetX.fromPublisher(Flux.just(1,
                                                      2,
                                                      3))
                             .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));
    }

    @Test
    public void fromFluxPSetX() {
        assertThat(PersistentSetX.fromPublisher(Flux.just(1,
                                                          2,
                                                          3))
                                 .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));
    }

    @Test
    public void fromFluxPOrderedSetX() {
        assertThat(OrderedSetX.fromPublisher(Flux.just(1,
                                                       2,
                                                       3))
                              .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));
    }

    @Test
    public void fromFluxPStackX() {
        assertThat(LinkedListX.fromPublisher(Flux.just(1,
                                                       2,
                                                       3))
                              .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));
    }

    @Test
    public void fromFluxPVectorX() {
        assertThat(VectorX.fromPublisher(Flux.just(1,
                                                   2,
                                                   3))
                          .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));
    }

    @Test
    public void fromFluxPBagX() {
        assertThat(BagX.fromPublisher(Flux.just(1,
                                                2,
                                                3))
                       .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));
    }

    @Test
    public void fromFluxStreamableX() {
        assertThat(Streamable.fromPublisher(Flux.just(1,
                                                      2,
                                                      3))
                             .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));
    }

    @Test
    public void publishAndSubscribe() {

        assertThat(Spouts.from(ReactiveSeq.of(1,
                                              2,
                                              3))
                         .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));
    }

    @Test
    public void publishAndSubscribeEmpty() {

        assertThat(Spouts.from(ReactiveSeq.of())
                         .toList(),
                   equalTo(Arrays.asList()));
    }

    @Test
    public void subscribeToFluxIterator() {

        assertThat(ReactiveSeq.fromIterator(Spouts.from(Flux.just(1,
                                                                  2,
                                                                  3))
                                                  .iterator())
                              .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));
    }

    @Test
    public void publishAndSubscribeIterator() {

        assertThat(ReactiveSeq.fromIterator(Spouts.from(ReactiveSeq.of(1,
                                                                       2,
                                                                       3))
                                                  .iterator())
                              .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));
    }

    @Test
    public void publishAndSubscribeEmptyIterator() {

        assertThat(ReactiveSeq.fromIterator(Spouts.from(ReactiveSeq.of())
                                                  .iterator())
                              .toList(),
                   equalTo(Arrays.asList()));
    }
}

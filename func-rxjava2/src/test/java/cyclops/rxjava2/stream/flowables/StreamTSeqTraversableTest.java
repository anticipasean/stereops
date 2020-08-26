package cyclops.rxjava2.stream.flowables;

import cyclops.reactor.container.transformer.StreamT;

import cyclops.container.traversable.Traversable;
import cyclops.monads.AnyMs;
import cyclops.rxjava2.container.higherkinded.FlowableAnyM;
import cyclops.monads.Witness;
import cyclops.monads.Witness.list;
import cyclops.rxjava2.adapter.FlowableReactiveSeq;
import cyclops.reactive.collection.container.mutable.ListX;
import cyclops.rxjava2.container.traversible.AbstractTraversableTest;
import io.reactivex.Flowable;
import org.junit.Test;


public class StreamTSeqTraversableTest extends AbstractTraversableTest {

    @Override
    public <T> Traversable<T> of(T... elements) {
        return AnyMs.liftM(FlowableReactiveSeq.of(elements),
                           Witness.reactiveSeq.ITERATIVE);
    }

    @Override
    public <T> Traversable<T> empty() {

        return AnyMs.liftM(FlowableReactiveSeq.<T>empty(),
                           Witness.reactiveSeq.ITERATIVE);
    }

    @Test
    public void conversion() {
        StreamT<list, Integer> trans = AnyMs.liftM(FlowableReactiveSeq.just(1,
                                                                            2,
                                                                            3),
                                                   list.INSTANCE);

        ListX<Flowable<Integer>> listObs = Witness.list(trans.unwrapTo(FlowableAnyM::fromStream));

    }

}

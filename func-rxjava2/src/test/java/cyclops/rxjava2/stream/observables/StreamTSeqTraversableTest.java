package cyclops.rxjava2.stream.observables;

import cyclops.container.traversable.Traversable;
import cyclops.monads.AnyMs;
import cyclops.monads.Witness;
import cyclops.monads.Witness.list;
import cyclops.reactive.collection.container.mutable.ListX;
import cyclops.reactor.container.transformer.StreamT;
import cyclops.rxjava2.adapter.ObservableReactiveSeq;
import cyclops.rxjava2.container.higherkinded.ObservableAnyM;
import cyclops.rxjava2.container.traversible.AbstractTraversableTest;
import io.reactivex.Observable;
import org.junit.Test;


public class StreamTSeqTraversableTest extends AbstractTraversableTest {

    @Override
    public <T> Traversable<T> of(T... elements) {
        return AnyMs.liftM(ObservableReactiveSeq.of(elements),
                           Witness.reactiveSeq.ITERATIVE);
    }

    @Override
    public <T> Traversable<T> empty() {

        return AnyMs.liftM(ObservableReactiveSeq.<T>empty(),
                           Witness.reactiveSeq.ITERATIVE);
    }

    @Test
    public void conversion() {
        StreamT<list, Integer> trans = AnyMs.liftM(ObservableReactiveSeq.just(1,
                                                                              2,
                                                                              3),
                                                   list.INSTANCE);

        ListX<Observable<Integer>> listObs = Witness.list(trans.unwrapTo(ObservableAnyM::fromStream));

    }

}

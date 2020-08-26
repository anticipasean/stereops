package cyclops.reactor.container.transformer.seq;


import cyclops.types.AbstractTraversableTest;
import cyclops.container.traversable.Traversable;
import cyclops.monads.AnyMs;
import cyclops.monads.Witness.reactiveSeq;
import cyclops.reactive.ReactiveSeq;


public class StreamTSeqTraversableTest extends AbstractTraversableTest {

    @Override
    public <T> Traversable<T> of(T... elements) {
        return AnyMs.liftM(ReactiveSeq.of(elements), reactiveSeq.ITERATIVE);
    }

    @Override
    public <T> Traversable<T> empty() {
        return ReactiveSeq.<T>empty()
                          .to(AnyMs::<reactiveSeq,T>liftM)
                          .apply(reactiveSeq.ITERATIVE);
    }

}

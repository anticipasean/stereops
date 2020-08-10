package cyclops.reactor.container.transformer;

import cyclops.types.AbstractTraversableTest;
import cyclops.container.traversable.Traversable;
import cyclops.monads.AnyMs;
import cyclops.monads.Witness;
import cyclops.reactor.stream.FluxReactiveSeq;


public class StreamTSeqTraversableTest extends AbstractTraversableTest {

    @Override
    public <T> Traversable<T> of(T... elements) {
        return AnyMs.liftM(FluxReactiveSeq.of(elements), Witness.reactiveSeq.ITERATIVE);
    }

    @Override
    public <T> Traversable<T> empty() {
        return AnyMs.liftM(FluxReactiveSeq.empty(), Witness.reactiveSeq.ITERATIVE);

    }

}

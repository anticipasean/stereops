package cyclops.monads.transformers.seq.convertable;

import cyclops.container.foldable.AbstractConvertableSequenceTest;
import cyclops.container.immutable.impl.ConvertableSequence;
import cyclops.monads.AnyMs;
import cyclops.monads.Witness;
import cyclops.reactive.ReactiveSeq;


public class StreamTSeqConvertableSequenceTest extends AbstractConvertableSequenceTest {

    @Override
    public <T> ConvertableSequence<T> of(T... elements) {
        return ReactiveSeq.of(elements) .to(AnyMs::<Witness.reactiveSeq,T>liftM)
          .apply(Witness.reactiveSeq.ITERATIVE).to();
    }

    @Override
    public <T> ConvertableSequence<T> empty() {

        return ReactiveSeq.<T>empty().to(AnyMs::<Witness.reactiveSeq,T>liftM)
          .apply(Witness.reactiveSeq.ITERATIVE).to();
    }

}

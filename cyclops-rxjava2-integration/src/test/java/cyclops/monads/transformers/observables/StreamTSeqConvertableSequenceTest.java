package cyclops.monads.transformers.observables;


import cyclops.container.foldable.AbstractConvertableSequenceTest;
import cyclops.container.persistent.impl.ConvertableSequence;
import cyclops.monads.AnyMs;
import cyclops.monads.Witness;
import cyclops.reactive.ObservableReactiveSeq;


public class StreamTSeqConvertableSequenceTest extends AbstractConvertableSequenceTest {

    @Override
    public <T> ConvertableSequence<T> of(T... elements) {

        return AnyMs.liftM(ObservableReactiveSeq.of(elements),
                           Witness.list.INSTANCE)
                    .to();
    }

    @Override
    public <T> ConvertableSequence<T> empty() {

        return AnyMs.liftM(ObservableReactiveSeq.<T>empty(),
                           Witness.list.INSTANCE)
                    .to();
    }

}

package cyclops.rxjava2.stream.observables;


import cyclops.container.foldable.AbstractConvertableSequenceTest;
import cyclops.container.immutable.impl.ConvertableSequence;
import cyclops.monads.AnyMs;
import cyclops.monads.Witness;
import cyclops.rxjava2.adapter.ObservableReactiveSeq;


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

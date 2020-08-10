package cyclops.monads.transformers;


import cyclops.container.foldable.AbstractConvertableSequenceTest;
import cyclops.container.persistent.impl.ConvertableSequence;
import cyclops.monads.AnyMs;
import cyclops.monads.Witness.list;
import cyclops.reactive.FluxReactiveSeq;


public class StreamTSeqConvertableSequenceTest extends AbstractConvertableSequenceTest {

    @Override
    public <T> ConvertableSequence<T> of(T... elements) {

        return AnyMs.liftM(FluxReactiveSeq.of(elements), list.INSTANCE).to();
    }

    @Override
    public <T> ConvertableSequence<T> empty() {

        return AnyMs.liftM(FluxReactiveSeq.<T>empty(),list.INSTANCE).to();
    }

}

package cyclops.reactor.container.transformer.flowables;


import cyclops.container.foldable.AbstractConvertableSequenceTest;
import cyclops.container.immutable.impl.ConvertableSequence;
import cyclops.monads.AnyMs;
import cyclops.monads.Witness.list;
import cyclops.pure.reactive.FlowableReactiveSeq;


public class StreamTSeqConvertableSequenceTest extends AbstractConvertableSequenceTest {

    @Override
    public <T> ConvertableSequence<T> of(T... elements) {

        return AnyMs.liftM(FlowableReactiveSeq.of(elements),
                           list.INSTANCE)
                    .to();
    }

    @Override
    public <T> ConvertableSequence<T> empty() {

        return AnyMs.liftM(FlowableReactiveSeq.<T>empty(),
                           list.INSTANCE)
                    .to();
    }

}

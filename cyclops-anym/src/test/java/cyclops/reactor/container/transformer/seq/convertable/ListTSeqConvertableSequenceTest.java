package cyclops.reactor.container.transformer.seq.convertable;

import cyclops.container.foldable.AbstractConvertableSequenceTest;
import cyclops.container.immutable.impl.ConvertableSequence;
import cyclops.pure.reactive.collections.mutable.ListX;
import cyclops.monads.AnyMs;
import cyclops.monads.Witness;


public class ListTSeqConvertableSequenceTest extends AbstractConvertableSequenceTest {

    @Override
    public <T> ConvertableSequence<T> of(T... elements) {
        return ListX.<T>of(elements)
                    .to(AnyMs::<Witness.list,T>liftM)
                    .apply(Witness.list.INSTANCE).to();
    }

    @Override
    public <T> ConvertableSequence<T> empty() {

        return ListX.<T>empty()
                   .to(AnyMs::<Witness.list,T>liftM)
                   .apply(Witness.list.INSTANCE)
                   .to();
    }

}

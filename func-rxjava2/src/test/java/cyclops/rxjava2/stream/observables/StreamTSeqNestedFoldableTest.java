package cyclops.rxjava2.stream.observables;



import cyclops.monads.AnyMs;
import cyclops.monads.Witness;
import cyclops.monads.transformers.AbstractNestedFoldableTest;
import cyclops.rxjava2.adapter.ObservableReactiveSeq;


public class StreamTSeqNestedFoldableTest extends AbstractNestedFoldableTest<Witness.list> {

    @Override
    public <T> FoldableTransformerSeq<Witness.list, T> of(T... elements) {
        return AnyMs.liftM(ObservableReactiveSeq.just(elements),
                           Witness.list.INSTANCE);
    }

    @Override
    public <T> FoldableTransformerSeq<Witness.list, T> empty() {
        return AnyMs.liftM(ObservableReactiveSeq.<T>empty(),
                           Witness.list.INSTANCE);
    }

}

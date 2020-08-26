package cyclops.rxjava2.stream.flowables;



import cyclops.monads.AnyMs;
import cyclops.monads.Witness;
import cyclops.monads.transformers.AbstractNestedFoldableTest;
import cyclops.rxjava2.adapter.FlowableReactiveSeq;


public class StreamTSeqNestedFoldableTest extends AbstractNestedFoldableTest<Witness.list> {

    @Override
    public <T> FoldableTransformerSeq<Witness.list, T> of(T... elements) {
        return AnyMs.liftM(FlowableReactiveSeq.just(elements),
                           Witness.list.INSTANCE);
    }

    @Override
    public <T> FoldableTransformerSeq<Witness.list, T> empty() {
        return AnyMs.liftM(FlowableReactiveSeq.<T>empty(),
                           Witness.list.INSTANCE);
    }

}

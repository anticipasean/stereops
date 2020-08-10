package cyclops.reactor.container.transformer;


import com.oath.cyclops.anym.transformers.FoldableTransformerSeq;
import cyclops.monads.AnyMs;
import cyclops.monads.Witness.list;
import cyclops.reactor.stream.FluxReactiveSeq;


public class StreamTSeqNestedFoldableTest extends AbstractNestedFoldableTest<list> {

    @Override
    public <T> FoldableTransformerSeq<list,T> of(T... elements) {
        return AnyMs.liftM(FluxReactiveSeq.of(elements), list.INSTANCE);
    }

    @Override
    public <T> FoldableTransformerSeq<list,T> empty() {
        return AnyMs.liftM(FluxReactiveSeq.empty(), list.INSTANCE);
    }

}

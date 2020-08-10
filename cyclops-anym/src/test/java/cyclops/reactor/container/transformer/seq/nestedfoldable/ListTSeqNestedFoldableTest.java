package cyclops.reactor.container.transformer.seq.nestedfoldable;

import cyclops.reactive.collections.mutable.ListX;

import com.oath.cyclops.anym.transformers.FoldableTransformerSeq;
import cyclops.monads.AnyMs;
import cyclops.monads.Witness;
import cyclops.reactor.container.transformer.AbstractNestedFoldableTest;


public class ListTSeqNestedFoldableTest extends AbstractNestedFoldableTest<Witness.list> {

    @Override
    public <T> FoldableTransformerSeq<Witness.list,T> of(T... elements) {
      return ListX.of(elements)
                  .to(AnyMs::<Witness.list,T>liftM)
                 .apply(Witness.list.INSTANCE);
    }

    @Override
    public <T> FoldableTransformerSeq<Witness.list,T> empty() {
      return ListX.<T>empty()
                  .to(AnyMs::<Witness.list,T>liftM)
                  .apply(Witness.list.INSTANCE);
    }

}

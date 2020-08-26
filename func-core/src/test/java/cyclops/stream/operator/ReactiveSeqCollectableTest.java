package cyclops.stream.operator;

import cyclops.container.foldable.Foldable;
import cyclops.reactive.ReactiveSeq;
import cyclops.stream.CollectableTest;


public class ReactiveSeqCollectableTest extends CollectableTest {

    @Override
    public <T> Foldable<T> of(T... values) {
        return ReactiveSeq.of(values);
    }

}

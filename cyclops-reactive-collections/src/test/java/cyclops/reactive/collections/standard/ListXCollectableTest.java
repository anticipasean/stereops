package cyclops.reactive.collections.standard;

import cyclops.container.foldable.Foldable;
import cyclops.reactive.collections.mutable.ListX;
import cyclops.streams.CollectableTest;


public class ListXCollectableTest extends CollectableTest {

    @Override
    public <T> Foldable<T> of(T... values) {
        return ListX.of(values);
    }

}

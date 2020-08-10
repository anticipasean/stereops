package cyclops.reactive.collection.container.mutable;

import cyclops.container.foldable.Foldable;
import cyclops.reactive.collection.container.mutable.ListX;
import cyclops.streams.CollectableTest;


public class ListXCollectableTest extends CollectableTest {

    @Override
    public <T> Foldable<T> of(T... values) {
        return ListX.of(values);
    }

}

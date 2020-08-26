package cyclops.reactive.collection.container.mutable;

import cyclops.container.foldable.Foldable;
import cyclops.stream.CollectableTest;


public class ListXCollectableTest extends CollectableTest {

    @Override
    public <T> Foldable<T> of(T... values) {
        return ListX.of(values);
    }

}

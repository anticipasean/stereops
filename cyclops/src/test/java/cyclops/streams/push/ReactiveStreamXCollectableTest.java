package cyclops.streams.push;

import cyclops.container.foldable.Foldable;
import cyclops.reactive.companion.Spouts;
import cyclops.streams.CollectableTest;


public class ReactiveStreamXCollectableTest extends CollectableTest {

    @Override
    public <T> Foldable<T> of(T... values) {
        return Spouts.of(values);
    }

}

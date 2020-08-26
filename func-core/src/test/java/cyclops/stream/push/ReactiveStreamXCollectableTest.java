package cyclops.stream.push;

import cyclops.container.foldable.Foldable;
import cyclops.reactive.companion.Spouts;
import cyclops.stream.CollectableTest;


public class ReactiveStreamXCollectableTest extends CollectableTest {

    @Override
    public <T> Foldable<T> of(T... values) {
        return Spouts.of(values);
    }

}

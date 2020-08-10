package cyclops.streams.push;

import cyclops.container.foldable.Folds;
import cyclops.reactive.companion.Spouts;
import cyclops.streams.CollectableTest;


public class ReactiveStreamXCollectableTest extends CollectableTest {

    @Override
    public <T> Folds<T> of(T... values) {
        return Spouts.of(values);
    }

}

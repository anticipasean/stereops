package cyclops.async.reactive.futurestream.react.lazy.sequence;

import cyclops.container.foldable.Folds;
import cyclops.async.reactive.futurestream.LazyReact;
import cyclops.stream.CollectableTest;


public class FutureStreamCollectableTest extends CollectableTest {

    @Override
    public <T> Folds<T> of(T... values) {
        return LazyReact.sequentialBuilder()
                        .of(values);
    }

}

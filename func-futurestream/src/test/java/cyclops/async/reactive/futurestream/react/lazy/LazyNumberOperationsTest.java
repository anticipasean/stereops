package cyclops.async.reactive.futurestream.react.lazy;

import cyclops.async.reactive.futurestream.FutureStream;
import cyclops.async.reactive.futurestream.LazyReact;
import cyclops.async.reactive.futurestream.react.base.BaseLazyNumberOperationsTest;
import java.util.function.Supplier;

public class LazyNumberOperationsTest extends BaseLazyNumberOperationsTest {

    @Override
    protected <U> FutureStream<U> of(U... array) {
        return LazyReact.parallelBuilder()
                        .of(array);
    }

    @Override
    protected <U> FutureStream<U> ofThread(U... array) {
        return LazyReact.sequentialCommonBuilder()
                        .of(array);
    }

    @Override
    protected <U> FutureStream<U> react(Supplier<U>... array) {
        return LazyReact.parallelBuilder()
                        .ofAsync(array);

    }


}

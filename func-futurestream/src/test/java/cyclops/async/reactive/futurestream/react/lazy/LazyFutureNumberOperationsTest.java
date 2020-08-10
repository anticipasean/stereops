package cyclops.async.reactive.futurestream.react.lazy;

import com.oath.cyclops.react.ThreadPools;
import cyclops.async.reactive.futurestream.FutureStream;
import cyclops.async.reactive.futurestream.LazyReact;
import cyclops.async.reactive.futurestream.react.base.BaseNumberOperationsTest;
import java.util.function.Supplier;

public class LazyFutureNumberOperationsTest extends BaseNumberOperationsTest {

    @Override
    protected <U> FutureStream<U> of(U... array) {
        return new LazyReact().of(array);
    }

    @Override
    protected <U> FutureStream<U> ofThread(U... array) {
        return new LazyReact(ThreadPools.getCommonFreeThread()).of(array);
    }

    @Override
    protected <U> FutureStream<U> react(Supplier<U>... array) {
        return LazyReact.parallelBuilder()
                        .ofAsync(array);

    }


}

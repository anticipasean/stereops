package cyclops.async.reactive.futurestream.operation;

import cyclops.async.reactive.futurestream.pipeline.FastFuture;
import cyclops.async.reactive.futurestream.pipeline.stream.LazyStreamWrapper;
import cyclops.reactive.subscription.Continueable;
import cyclops.async.reactive.futurestream.pipeline.stream.BlockingStreamHelper;
import cyclops.async.reactive.futurestream.pipeline.stream.OperationsOnFutures;
import cyclops.async.reactive.futurestream.FutureStream;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OperationsOnFuturesImpl<T> implements OperationsOnFutures<T> {

    private final FutureStream<T> lfs;

    @Override
    public FutureStream<T> fromStreamOfFutures(final Stream<FastFuture<T>> stream) {
        return lfs.fromStreamOfFutures(stream);
    }

    @Override
    public LazyStreamWrapper<T> getLastActive() {
        return lfs.getLastActive();
    }

    @Override
    public FutureStream<T> withLastActive(final LazyStreamWrapper<T> active) {
        return lfs.withLastActive(active);
    }

    @Override
    public T safeJoin(final FastFuture<T> f) {
        return (T) BlockingStreamHelper.getSafe(f,
                                                lfs.getErrorHandler());
    }

    @Override
    public Continueable getSubscription() {
        return lfs.getSubscription();
    }

}

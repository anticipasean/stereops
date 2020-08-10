package cyclops.internal.react.stream.traits.future.operators;

import cyclops.internal.react.async.future.FastFuture;
import cyclops.internal.react.stream.LazyStreamWrapper;
import cyclops.reactive.subscription.Continueable;
import cyclops.stream.async.BlockingStreamHelper;
import cyclops.stream.async.OperationsOnFutures;
import cyclops.futurestream.FutureStream;
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

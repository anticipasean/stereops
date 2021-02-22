package cyclops.container.control.lazy.maybe;

import cyclops.async.exception.ClosedQueueException;
import cyclops.container.MonadicValue;
import cyclops.container.control.eager.option.Option;
import cyclops.reactive.Completable;
import java.io.InvalidObjectException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import org.reactivestreams.Subscriber;

/**
 * @author smccarron
 * @created 2021-02-21
 */
@AllArgsConstructor
public class CompletableMaybe<ORG, T2> implements Maybe<T2>, Completable<ORG> {

    public final CompletableFuture<ORG> complete;
    public final Maybe<T2> maybe;

    private Object writeReplace() {
        return toOption();
    }

    private Object readResolve() throws InvalidObjectException {
        throw new InvalidObjectException("Use Serialization Proxy instead.");
    }

    @Override
    public boolean isPresent() {
        return maybe.isPresent();
    }

    @Override
    public Maybe<T2> recover(Supplier<? extends T2> value) {
        return maybe.recover(value);
    }

    @Override
    public Maybe<T2> recover(T2 value) {
        return maybe.recover(value);
    }

    @Override
    public Maybe<T2> recoverWith(Supplier<? extends Option<T2>> fn) {
        return maybe.recoverWith(fn);
    }

    @Override
    public <R> Maybe<R> map(Function<? super T2, ? extends R> mapper) {
        return maybe.map(mapper);
    }

    @Override
    public <R> Maybe<R> flatMap(Function<? super T2, ? extends MonadicValue<? extends R>> mapper) {
        return maybe.flatMap(mapper);
    }

    @Override
    public <R> R fold(Function<? super T2, ? extends R> some,
                      Supplier<? extends R> none) {
        return maybe.fold(some,
                          none);
    }

    @Override
    public Maybe<T2> filter(Predicate<? super T2> fn) {
        return maybe.filter(fn);
    }

    @Override
    public Maybe<T2> onEmpty(Runnable r) {
        return maybe.onEmpty(r);
    }

    @Override
    public boolean isFailed() {
        return complete.isCompletedExceptionally();
    }

    @Override
    public boolean isDone() {
        return complete.isDone();
    }


    public boolean completeAsNone() {
        return completeExceptionally(new ClosedQueueException());
    }

    @Override
    public boolean complete(ORG done) {
        return complete.complete(done);
    }

    @Override
    public boolean completeExceptionally(Throwable error) {
        return complete.completeExceptionally(error);
    }

    @Override
    public int hashCode() {
        return maybe.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return maybe.equals(obj);
    }

    //        @Override
    //        public <R> R fold(Function<? super T2, ? extends R> fn1,
    //                          Function<? super None<T2>, ? extends R> fn2) {
    //            return maybe.fold(fn1,
    //                              fn2);
    //        }

    @Override
    public void subscribe(Subscriber<? super T2> sub) {
        maybe.subscribe(sub);
    }
}

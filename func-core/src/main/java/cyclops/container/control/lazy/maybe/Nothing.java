package cyclops.container.control.lazy.maybe;

import cyclops.container.MonadicValue;
import cyclops.container.control.lazy.eval.Eval;
import cyclops.container.control.eager.option.Option;
import java.io.InvalidObjectException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;

/**
 * @author smccarron
 * @created 2021-02-21
 */
final class Nothing<T> implements Maybe<T> {

    private static enum NothingInstanceHolder {
        INSTANCE(new Nothing<>());

        @SuppressWarnings("unchecked")
        public <T> Nothing<T> getNothing() {
            return (Nothing<T>) nothing;
        }

        private final Nothing<?> nothing;

        NothingInstanceHolder(Nothing<?> nothing) {
            this.nothing = nothing;
        }
    }

    static <T> Nothing<T> nothing() {
        return NothingInstanceHolder.INSTANCE.getNothing();
    }

    private Object writeReplace() {
        return toOption();
    }

    private Object readResolve() throws InvalidObjectException {
        throw new InvalidObjectException("Use Serialization Proxy instead.");
    }

    @Override
    public <R> Maybe<R> map(final Function<? super T, ? extends R> mapper) {
        return nothing();
    }

    @Override
    public <R> Maybe<R> flatMap(final Function<? super T, ? extends MonadicValue<? extends R>> mapper) {
        return nothing();

    }

    @Override
    public Maybe<T> filter(final Predicate<? super T> test) {
        return nothing();
    }

    @Override
    public Maybe<T> onEmpty(Runnable r) {
        return new Lazy<>(Eval.later(() -> {
            r.run();
            return this;
        }));
    }


    @Override
    public Maybe<T> recover(final T value) {
        return Maybe.of(value);
    }

    @Override
    public Maybe<T> recover(final Supplier<? extends T> value) {
        return new Just<T>(Eval.later((Supplier<T>) value));
    }

    @Override
    public Maybe<T> recoverWith(Supplier<? extends Option<T>> fn) {

        return new Just<>(Eval.narrow(Eval.later(fn))).flatMap(m -> m);

    }


    @Override
    public <R> R fold(final Function<? super T, ? extends R> some,
                      final Supplier<? extends R> none) {
        return none.get();
    }

    @Override
    public Optional<T> toOptional() {
        return Optional.ofNullable(null);
    }

    @Override
    public String toString() {
        return mkString();
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public boolean equals(final Object obj) {

        if (obj instanceof cyclops.container.control.lazy.maybe.Nothing) {
            return true;
        }

        if (obj instanceof Option) {
            return !((Option) obj).isPresent();
        }
        return false;
    }

    @Override
    public T orElse(final T value) {
        return value;
    }

    @Override
    public T orElseGet(final Supplier<? extends T> value) {
        return value.get();
    }

    @Override
    public <R> cyclops.container.control.lazy.maybe.Nothing<R> concatMap(final Function<? super T, ? extends Iterable<? extends R>> mapper) {
        return nothing();
    }

    @Override
    public <R> cyclops.container.control.lazy.maybe.Nothing<R> mergeMap(final Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return nothing();
    }

    @Override
    public void forEach(Consumer<? super T> action) {

    }

    //        @Override
    //        public <R> R fold(Function<? super T, ? extends R> fn1,
    //                          Function<? super None<T>, ? extends R> fn2) {
    //            None<T> none = None.NOTHING_EAGER;
    //            return fn2.apply(none);
    //        }
}

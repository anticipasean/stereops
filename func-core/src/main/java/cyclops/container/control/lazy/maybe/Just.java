package cyclops.container.control.lazy.maybe;

import cyclops.container.MonadicValue;
import cyclops.container.control.lazy.eval.Eval;
import cyclops.container.control.eager.option.Option;
import cyclops.container.foldable.Present;
import java.io.InvalidObjectException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;

/**
 * @author smccarron
 * @created 2021-02-21
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
final class Just<T> implements Maybe<T>, Present<T> {


    private final Eval<T> lazy;


    private Object writeReplace() {
        return toOption();
    }

    private Object readResolve() throws InvalidObjectException {
        throw new InvalidObjectException("Use Serialization Proxy instead.");
    }


    @Override
    public <R> Maybe<R> map(final Function<? super T, ? extends R> mapper) {
        return new cyclops.container.control.lazy.maybe.Just<>(lazy.map(t -> mapper.apply(t)));
    }

    @Override
    public <R> Maybe<R> flatMap(final Function<? super T, ? extends MonadicValue<? extends R>> mapper) {
        Eval<? extends Maybe<? extends R>> ret = lazy.map(mapper.andThen(v -> v.toMaybe()));

        final Eval<Maybe<R>> e3 = (Eval<Maybe<R>>) ret;
        return new Lazy<>(e3);


    }

    @Override
    public Maybe<T> onEmpty(Runnable r) {
        return this;
    }

    @Override
    public Maybe<T> filter(final Predicate<? super T> test) {
        return flatMap(t -> test.test(t) ? this : Maybe.nothing());
    }

    @Override
    public <R> R fold(final Function<? super T, ? extends R> some,
                      final Supplier<? extends R> none) {
        return some.apply(lazy.get());
    }

    @Override
    public Maybe<T> recover(final T value) {
        return this;
    }

    @Override
    public Maybe<T> recover(final Supplier<? extends T> value) {
        return this;
    }


    @Override
    public String toString() {
        return mkString();
    }


    @Override
    public boolean isPresent() {
        return true;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(lazy.get());
    }


    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Nothing) {
            return false;
        }
        //            else if (obj instanceof None) {
        //                return false;
        //            }
        if (obj instanceof Present) {
            return Objects.equals(lazy.get(),
                                  ((Present) obj).orElse(null));
        } else if (obj instanceof Lazy) {
            return Objects.equals(orElse(null),
                                  ((Maybe) obj).orElse(null));
        }
        return false;
    }


    @Override
    public T orElse(T value) {
        return lazy.get();
    }

    @Override
    public T orElseGet(final Supplier<? extends T> value) {
        return lazy.get();
    }

    @Override
    public <R> Maybe<R> concatMap(final Function<? super T, ? extends Iterable<? extends R>> mapper) {
        final Maybe<R> maybe = Maybe.super.concatMap(mapper);
        return maybe;
    }

    @Override
    public <R> Maybe<R> mergeMap(final Function<? super T, ? extends Publisher<? extends R>> mapper) {
        final Maybe<R> m = Maybe.super.mergeMap(mapper);
        return m;
    }


    @Override
    public Maybe<T> recoverWith(Supplier<? extends Option<T>> fn) {
        return this;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        this.lazy.forEach(action);
    }

    //        @Override
    //        public <R> R fold(Function<? super T, ? extends R> fn1,
    //                          Function<? super None<T>, ? extends R> fn2) {
    //            return fn1.apply(this.lazy.get());
    //        }
}

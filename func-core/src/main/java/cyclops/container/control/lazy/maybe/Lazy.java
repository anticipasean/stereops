package cyclops.container.control.lazy.maybe;

import cyclops.container.MonadicValue;
import cyclops.container.control.lazy.eval.Eval;
import cyclops.container.control.lazy.trampoline.Trampoline;
import cyclops.container.control.eager.option.Option;
import cyclops.container.foldable.Present;
import cyclops.reactive.ReactiveSeq;
import cyclops.reactive.companion.Spouts;
import java.io.InvalidObjectException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author smccarron
 * @created 2021-02-21
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
final class Lazy<T> implements Maybe<T> {

    private final Eval<Maybe<T>> lazy;

    private static <T> Lazy<T> lazy(Eval<Maybe<T>> lazy) {
        return new cyclops.container.control.lazy.maybe.Lazy<>(lazy);
    }

    private Object writeReplace() {
        return toOption();
    }

    private Object readResolve() throws InvalidObjectException {
        throw new InvalidObjectException("Use Serialization Proxy instead.");
    }

    @Override
    public <R> Maybe<R> map(final Function<? super T, ? extends R> mapper) {
        return flatMap(t -> Maybe.just(mapper.apply(t)));
    }

    @Override
    public <R> Maybe<R> flatMap(final Function<? super T, ? extends MonadicValue<? extends R>> mapper) {
        return Maybe.fromLazy(lazy.map(m -> m.flatMap(mapper)));

    }

    @Override
    public Maybe<T> filter(final Predicate<? super T> test) {
        return flatMap(t -> test.test(t) ? this : Maybe.nothing());
    }

    @Override
    public <R> R fold(final Function<? super T, ? extends R> some,
                      final Supplier<? extends R> none) {
        Maybe<T> maybe = lazy.get();
        while (maybe instanceof cyclops.container.control.lazy.maybe.Lazy) {
            maybe = ((Lazy<T>) maybe).lazy.get();
        }
        return maybe.fold(some,
                          none);
    }

    @Override
    public ReactiveSeq<T> stream() {
        return Spouts.from(this);
    }

    @Override
    public final void subscribe(final Subscriber<? super T> sub) {
        lazy.subscribe(new Subscriber<Maybe<T>>() {
            boolean onCompleteSent = false;

            @Override
            public void onSubscribe(Subscription s) {
                sub.onSubscribe(s);
            }

            @Override
            public void onNext(Maybe<T> ts) {
                if (ts.isPresent()) {
                    sub.onNext(ts.orElse(null));
                } else if (!onCompleteSent) {
                    sub.onComplete();
                    onCompleteSent = true;
                }
            }

            @Override
            public void onError(Throwable t) {
                sub.onError(t);
            }

            @Override
            public void onComplete() {
                if (!onCompleteSent) {
                    sub.onComplete();
                    onCompleteSent = true;
                }
            }
        });
    }


    @Override
    public Trampoline<Maybe<T>> toTrampoline() {
        Trampoline<Maybe<T>> trampoline = lazy.toTrampoline();
        return new Trampoline<Maybe<T>>() {
            @Override
            public Maybe<T> get() {
                Maybe<T> maybe = lazy.get();
                while (maybe instanceof cyclops.container.control.lazy.maybe.Lazy) {
                    maybe = ((Lazy<T>) maybe).lazy.get();
                }
                return maybe;
            }

            @Override
            public boolean complete() {
                return false;
            }

            @Override
            public Trampoline<Maybe<T>> bounce() {
                Maybe<T> maybe = lazy.get();
                if (maybe instanceof cyclops.container.control.lazy.maybe.Lazy) {
                    return maybe.toTrampoline();
                }
                return Trampoline.done(maybe);

            }
        };
    }


    @Override
    public Maybe<T> onEmpty(Runnable r) {
        return new Lazy<T>(lazy.map(m -> m.onEmpty(r)));
    }

    @Override
    public Maybe<T> recover(final T value) {
        return new Lazy<T>(lazy.map(m -> m.recover(value)));
    }

    @Override
    public Maybe<T> recover(final Supplier<? extends T> value) {
        return new Lazy<T>(lazy.map(m -> m.recover(value)));
    }

    @Override
    public Maybe<T> recoverWith(Supplier<? extends Option<T>> fn) {
        return new Lazy<T>(lazy.map(m -> m.recoverWith(fn)));
    }

    @Override
    public String toString() {

        Maybe<T> maybe = lazy.get();
        while (maybe instanceof cyclops.container.control.lazy.maybe.Lazy) {
            maybe = ((Lazy<T>) maybe).lazy.get();
        }
        return maybe.mkString();
    }


    @Override
    public boolean isPresent() {
        Maybe<T> maybe = lazy.get();
        while (maybe instanceof cyclops.container.control.lazy.maybe.Lazy) {
            maybe = ((Lazy<T>) maybe).lazy.get();
        }
        return maybe.isPresent();
    }

    @Override
    public T orElse(final T value) {
        Maybe<T> maybe = lazy.get();
        while (maybe instanceof cyclops.container.control.lazy.maybe.Lazy) {
            maybe = ((Lazy<T>) maybe).lazy.get();
        }
        return maybe.orElse(value);
    }

    @Override
    public T orElseGet(final Supplier<? extends T> value) {
        Maybe<T> maybe = lazy.get();
        while (maybe instanceof cyclops.container.control.lazy.maybe.Lazy) {
            maybe = ((Lazy<T>) maybe).lazy.get();
        }
        return maybe.orElseGet(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        Maybe<T> maybe = lazy.get();
        while (maybe instanceof cyclops.container.control.lazy.maybe.Lazy) {
            maybe = ((Lazy<T>) maybe).lazy.get();
        }

        return maybe.hashCode();
    }


    @Override
    public boolean equals(final Object obj) {

        if (obj instanceof Nothing) {
            return !isPresent();
        }
        //            else if (obj instanceof None) {
        //                return !isPresent();
        //            }
        else if (obj instanceof Present && isPresent()) {
            return Objects.equals(orElse(null),
                                  ((Present) obj).orElse(null));
        } else if (obj instanceof cyclops.container.control.lazy.maybe.Lazy) {
            if (isPresent()) {
                return Objects.equals(orElse(null),
                                      ((Maybe) obj).orElse(null));
            } else {
                return !((cyclops.container.control.lazy.maybe.Lazy) obj).isPresent();
            }
        }
        return false;
    }

    @Override
    public <R> Maybe<R> concatMap(final Function<? super T, ? extends Iterable<? extends R>> mapper) {
        Eval<? extends Maybe<? extends R>> res = lazy.map(m -> m.concatMap(mapper));
        Eval<Maybe<R>> narrowed = (Eval) res;
        return Maybe.fromLazy(narrowed);

    }

    @Override
    public <R> Maybe<R> mergeMap(final Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return Maybe.fromLazy(lazy.map(m -> m.mergeMap(mapper)));

    }

    //        @Override
    //        public <R> R fold(Function<? super T, ? extends R> fn1,
    //                          Function<? super None<T>, ? extends R> fn2) {
    //            return this.lazy.get()
    //                            .fold(fn1,
    //                                  fn2);
    //        }
}

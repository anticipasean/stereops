package cyclops.container.control.eager.attempt;

import static java.util.Objects.requireNonNull;

import cyclops.container.control.eager.either.Either;
import cyclops.container.control.eager.ior.Ior;
import cyclops.container.control.eager.option.Option;
import cyclops.container.control.lazy.maybe.Maybe;
import cyclops.container.control.lazy.trampoline.Trampoline;
import cyclops.exception.ExceptionSoftener;
import cyclops.function.enhanced.Function3;
import cyclops.function.enhanced.Function4;
import cyclops.reactive.ReactiveSeq;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author smccarron
 * @created 2021-02-21
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
final class Success<T, X extends Throwable> implements Try<T, X> {

    enum EmptySuccess {
        INSTANCE;
    }

    private final T value;
    private final Class<? extends Throwable>[] classes;

    @SafeVarargs
    static <T, X extends Throwable> Try<T, X> of(T value,
                                                 Class<? extends Throwable>... classes) {
        try {
            return new Success<T, X>(requireNonNull(value,
                                                    () -> "value"),
                                     requireNonNull(classes,
                                                    () -> "classes"));
        } catch (final Throwable t) {
            return attemptFailureHandler(t,
                                         classes);
        }
    }

    @Override
    public Either<X, T> asEither() {
        return Either.right(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Try<T, X> withExceptions(Class<? extends X>... toCatch) {
        requireNonNull(toCatch,
                       () -> "toCatch");
        if (toCatch.length == 0) {
            return new Success<T, X>(value,
                                     new Class[]{Throwable.class});
        } else {
            return new Success<>(value,
                                 toCatch);
        }
    }

    @Override
    public Trampoline<Either<X, T>> toTrampoline() {
        return Trampoline.more(() -> Trampoline.done(Either.right(value)));
    }

    @Override
    public Try<T, X> recoverWith(Supplier<? extends Try<T, X>> supplier) {
        requireNonNull(supplier,
                       () -> "supplier");
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> Try<R, Throwable> retry(Function<? super T, ? extends R> fn) {
        requireNonNull(fn,
                       () -> "fn");
        return (Try<R, Throwable>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> Try<R, Throwable> retry(Function<? super T, ? extends R> fn,
                                       int retries,
                                       long delay,
                                       TimeUnit timeUnit) {
        requireNonNull(fn,
                       () -> "fn");
        requireNonNull(timeUnit,
                       () -> "timeUnit");
        return (Try<R, Throwable>) this;
    }

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        requireNonNull(subscriber,
                       () -> "subscriber").onSubscribe(new Subscription() {
            final AtomicBoolean running = new AtomicBoolean(true);
            final AtomicBoolean cancelled = new AtomicBoolean(false);

            @Override
            public void request(final long n) {
                if (n < 1) {
                    subscriber.onError(new IllegalArgumentException("3.9 While the Subscription is not cancelled, Subscription.request(long n) MUST throw a java.lang.IllegalArgumentException if the argument is <= 0."));
                }
                if (!running.compareAndSet(true,
                                           false)) {
                    return;
                }
                try {
                    if (!cancelled.get()) {
                        subscriber.onNext(value);
                    }
                } catch (final Throwable t) {
                    subscriber.onError(t);
                } finally {
                    try {
                        subscriber.onComplete();
                    } catch (Throwable t) {
                        subscriber.onError(t);
                    }
                }
            }

            @Override
            public void cancel() {
                cancelled.set(true);
            }
        });
    }

    @Override
    public Try<T, X> recover(Supplier<? extends T> supplier) {
        requireNonNull(supplier,
                       () -> "supplier");
        return recover(t -> supplier.get());
    }

    @Override
    public Try<T, X> recover(Function<? super X, ? extends T> fn) {
        return this;
    }

    @Override
    public <T2, R1, R2, R3, R> Try<R, X> forEach4(Function<? super T, ? extends Try<R1, X>> value1,
                                                  BiFunction<? super T, ? super R1, ? extends Try<R2, X>> value2,
                                                  Function3<? super T, ? super R1, ? super R2, ? extends Try<R3, X>> value3,
                                                  Function4<? super T, ? super R1, ? super R2, ? super R3, ? extends R> yieldingFunction) {
        return this.flatMap(in -> {

            Try<R1, X> a = value1.apply(in);
            return a.flatMap(ina -> {
                Try<R2, X> b = value2.apply(in,
                                            ina);
                return b.flatMap(inb -> {
                    Try<R3, X> c = value3.apply(in,
                                                ina,
                                                inb);
                    return c.map(in2 -> yieldingFunction.apply(in,
                                                               ina,
                                                               inb,
                                                               in2));
                });

            });

        });
    }

    @Override
    public <T2, R1, R2, R> Try<R, X> forEach3(Function<? super T, ? extends Try<R1, X>> value1,
                                              BiFunction<? super T, ? super R1, ? extends Try<R2, X>> value2,
                                              Function3<? super T, ? super R1, ? super R2, ? extends R> yieldingFunction) {
        return this.flatMap(in -> {

            Try<R1, X> a = value1.apply(in);
            return a.flatMap(ina -> {
                Try<R2, X> b = value2.apply(in,
                                            ina);
                return b.map(in2 -> yieldingFunction.apply(in,
                                                           ina,
                                                           in2));
            });

        });
    }

    @Override
    public <R1, R> Try<R, X> forEach2(Function<? super T, ? extends Try<R1, X>> value1,
                                      BiFunction<? super T, ? super R1, ? extends R> yieldingFunction) {
        return this.flatMap(in -> {
            Try<R1, X> b = value1.apply(in);
            return b.map(in2 -> yieldingFunction.apply(in,
                                                       in2));
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public Try<T, Throwable> toTry() {
        return (Try<T, Throwable>) this;
    }

    @Override
    public Option<X> failureGet() {
        return Option.none();
    }

    @Override
    public <R> Try<R, X> coflatMap(Function<? super Try<T, X>, R> mapper) {
        return requireNonNull(mapper,
                              () -> "mapper").andThen(r -> unit(r))
                                             .apply(this);
    }

    @Override
    public <V> Try<V, X> unit(V value) {
        return Success.of(value,
                          classes);
    }

    @Override
    public Either<X, T> toEitherWithError() {
        return Either.right(value);
    }

    @Override
    public Option<T> get() {
        return Option.of(value);
    }

    @Override
    public T orElse(T value) {
        return this.value;
    }

    @Override
    public T orElseGet(Supplier<? extends T> valueSupplier) {
        requireNonNull(valueSupplier,
                       () -> "valueSupplier");
        return value;
    }

    @Override
    public <R> Try<R, X> map(Function<? super T, ? extends R> mapper) {
        try {
            return new Success<>(requireNonNull(mapper,
                                                () -> "mapper").apply(value),
                                 classes);
        } catch (final Throwable t) {
            return attemptFailureHandler(t);
        }
    }

    @Override
    public <R> Try<R, X> mapOrCatch(CheckedFunction<? super T, ? extends R, X> checkedFunction,
                                    Class<? extends X>... classes) {
        requireNonNull(checkedFunction,
                       () -> "checkedFunction");
        requireNonNull(classes,
                       () -> "classes");
        try {
            return Success.of(checkedFunction.apply(value),
                              classes);
        } catch (final Throwable t) {
            return attemptFailureHandler(t,
                                         classes);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <XR extends Throwable> Try<T, XR> mapFailure(Function<? super X, ? extends XR> fn) {
        return (Try<T, XR>) this;
    }

    @SafeVarargs
    @Override
    public final <R> Try<R, X> flatMapOrCatch(CheckedFunction<? super T, ? extends Try<? extends R, X>, X> fn,
                                              Class<? extends X>... classes) {
        requireNonNull(fn,
                       () -> "fn");
        requireNonNull(classes,
                       () -> "classes");
        try {
            return Success.narrow(fn.apply(value)
                                    .withExceptions(classes));
        } catch (final Throwable t) {
            return attemptFailureHandler(t,
                                         classes);
        }
    }

    @Override
    public Maybe<T> filter(Predicate<? super T> predicate) {
        return Maybe.of(value)
                    .filter(requireNonNull(predicate,
                                           () -> "predicate"));
    }

    @Override
    public Try<T, X> filter(Predicate<? super T> predicate,
                            Function<? super T, ? extends X> errorGenerator) {
        requireNonNull(predicate,
                       () -> "predicate");
        requireNonNull(errorGenerator,
                       () -> "errorGenerator");
        try {
            if (predicate.test(value)) {
                return new Success<>(value,
                                     classes);
            }
            return Failure.of(errorGenerator.apply(value),
                              classes);
        } catch (final Throwable t) {
            return attemptFailureHandler(t);
        }
    }

    @Override
    public Try<T, X> onFail(Consumer<? super X> consumer) {
        return this;
    }

    @Override
    public Try<T, X> onFail(Class<? extends X> t,
                            Consumer<X> consumer) {
        return this;
    }

    @Override
    public Try<T, X> recoverFlatMap(Function<? super X, ? extends Try<T, X>> fn) {
        return this;
    }

    @Override
    public Try<T, X> recoverFlatMapFor(Class<? extends X> t,
                                       Function<? super X, ? extends Try<T, X>> fn) {
        return this;
    }

    @Override
    public Try<T, X> recoverFor(Class<? extends X> t,
                                Function<? super X, ? extends T> fn) {
        return this;
    }

    @Override
    public Optional<T> toOptional() {
        return Optional.empty();
    }

    @Override
    public Option<X> toFailedOption() {
        return Option.none();
    }

    @Override
    public Stream<X> toFailedStream() {
        return ReactiveSeq.empty();
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public void forEach(Consumer<? super T> consumer) {
        requireNonNull(consumer,
                       () -> "consumer").accept(value);
    }

    @Override
    public void forEachFailed(Consumer<? super X> consumer) {

    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public <T2, R> Try<R, X> zip(Try<T2, X> otherAttempt,
                                 BiFunction<? super T, ? super T2, ? extends R> combiner) {
        requireNonNull(otherAttempt,
                       () -> "otherAttempt");
        requireNonNull(combiner,
                       () -> "combiner");
        return flatMap(t -> otherAttempt.map(t2 -> combiner.apply(t,
                                                                  t2)));
    }

    @Override
    public <R> Try<R, X> flatMap(Function<? super T, ? extends Try<? extends R, X>> fn) {
        try {
            return Success.narrow(requireNonNull(fn,
                                                 () -> "fn").apply(value));
        } catch (final Throwable t) {
            return attemptFailureHandler(t);
        }
    }

    @SuppressWarnings("unchecked")
    static <T, X extends Throwable> Try<T, X> narrow(Try<? extends T, X> attempt) {
        return (Try<T, X>) attempt;
    }

    private <R> Try<R, X> attemptFailureHandler(Throwable t) {
        return attemptFailureHandler(t,
                                     classes);
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    private static <R, X extends Throwable> Try<R, X> attemptFailureHandler(Throwable t,
                                                                            Class<? extends Throwable>... classesToCatch) {
        if (classesToCatch.length == 0) {
            return Failure.of((X) t,
                              Throwable.class);
        }
        Optional<Class<? extends Throwable>> error = Stream.of(classesToCatch)
                                                           .filter(c -> c.isAssignableFrom(t.getClass()))
                                                           .findFirst();
        if (error.isPresent()) {
            return Failure.of((X) t,
                              classesToCatch);
        } else {
            throw ExceptionSoftener.throwSoftenedException(t);
        }
    }

    @Override
    public <T2, R> Try<R, X> zip(Either<X, T2> otherSumType,
                                 BiFunction<? super T, ? super T2, ? extends R> combiner) {
        requireNonNull(otherSumType,
                       () -> "otherSumType");
        requireNonNull(combiner,
                       () -> "combiner");
        return toEither().zip(otherSumType,
                              combiner)
                         .fold(f -> Failure.of(f,
                                               classes),
                               s -> Success.of(s,
                                               classes));
    }

    @Override
    public Either<X, T> toEither() {
        return Either.right(value);
    }

    @Override
    public <T2, R> Try<R, X> zip(Ior<X, T2> otherSumType,
                                 BiFunction<? super T, ? super T2, ? extends R> combiner) {
        requireNonNull(otherSumType,
                       () -> "otherSumType");
        requireNonNull(combiner,
                       () -> "combiner");
        return toIor().zip(otherSumType,
                           combiner)
                      .fold(f -> Failure.of(f,
                                            classes),
                            s -> Success.of(s,
                                            classes),
                            // In the event of an error occurring in either one, the failure case takes priority
                            (x, s) -> x != null ? Failure.of(x,
                                                             classes) : Success.of(s,
                                                                                   classes));

    }

    @Override
    public Ior<X, T> toIor() {
        return Ior.right(value);
    }

    @Override
    public Try<T, X> peek(Consumer<? super T> consumer) {
        requireNonNull(consumer,
                       () -> "consumer").accept(value);
        return this;
    }

    @Override
    public Try<T, X> peekFailed(Consumer<? super X> consumer) {
        return this;
    }

    @Override
    public Iterator<T> iterator() {
        return stream().iterator();
    }

    @Override
    public ReactiveSeq<T> stream() {
        return ReactiveSeq.of(value);
    }

    @Override
    public <R> R fold(Function<? super T, ? extends R> fn1,
                      Function<? super X, ? extends R> fn2) {
        requireNonNull(fn2,
                       () -> "fn2");
        return requireNonNull(fn1,
                              () -> "fn1").apply(value);
    }

    @Override
    public <R> R fold(Function<? super T, ? extends R> present,
                      Supplier<? extends R> absent) {
        requireNonNull(absent,
                       () -> "absent");
        return requireNonNull(present,
                              () -> "present").apply(value);
    }

    @Override
    public Try<T, X> bipeek(Consumer<? super T> c1,
                            Consumer<? super X> c2) {
        requireNonNull(c2,
                       () -> "c2");
        requireNonNull(c1,
                       () -> "c1").accept(value);
        return this;
    }

    @Override
    public String mkString() {
        return ReactiveSeq.of("Try.Success",
                              "(value:",
                              Objects.toString(value),
                              ")")
                          .join();
    }


}

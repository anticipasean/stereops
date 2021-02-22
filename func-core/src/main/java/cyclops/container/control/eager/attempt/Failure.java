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
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.val;
import org.reactivestreams.Subscriber;

/**
 * @author smccarron
 * @created 2021-02-21
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
final class Failure<T, X extends Throwable> implements Try<T, X> {

    private final X throwable;
    private final Class<? extends Throwable>[] classes;

    @SuppressWarnings("unchecked")
    static <T, X extends Throwable> Try<T, X> narrow(Try<? extends T, X> attempt) {
        return (Try<T, X>) attempt;
    }

    @SuppressWarnings("unchecked")
    static <T, X extends Throwable> Try<T, X> of(X throwable,
                                                 Class<? extends Throwable>... classes) {
        try {
            return new Failure<>(requireNonNull(throwable,
                                                () -> "throwable"),
                                 requireNonNull(classes,
                                                () -> "classes"));
        } catch (NullPointerException t) {
            if (classes != null) {
                Optional<Class<? extends Throwable>> errorWrappable = Stream.of(classes)
                                                                            .filter(NullPointerException.class::isAssignableFrom)
                                                                            .findFirst();
                if (errorWrappable.isPresent()) {
                    return new Failure<T, X>((X) t,
                                             classes);
                }
            }
            throw t;
        }
    }

    @Override
    public Either<X, T> asEither() {
        return Either.left(throwable);
    }

    @SafeVarargs
    @Override
    public final Try<T, X> withExceptions(Class<? extends X>... toCatch) {
        requireNonNull(toCatch,
                       () -> "toCatch");
        return attemptFailureHandler(throwable,
                                     toCatch);
    }

    @Override
    public Trampoline<Either<X, T>> toTrampoline() {
        return Trampoline.more(() -> Trampoline.done(Either.left(throwable)));
    }

    @Override
    public Try<T, X> recoverWith(Supplier<? extends Try<T, X>> supplier) {
        return requireNonNull(supplier,
                              () -> "supplier").get();
    }

    @Override
    public <R> Try<R, Throwable> retry(Function<? super T, ? extends R> fn) {
        return retry(requireNonNull(fn,
                                    () -> "fn"),
                     3,
                     500,
                     TimeUnit.MILLISECONDS);
    }

    @Override
    public <R> Try<R, Throwable> retry(Function<? super T, ? extends R> fn,
                                       int retries,
                                       long delay,
                                       TimeUnit timeUnit) {
        requireNonNull(fn,
                       () -> "fn");
        requireNonNull(timeUnit,
                       () -> "timeUnit");
        Try<T, Throwable> narrow = this.mapFailure(x -> x);
        final Function<T, Try<R, Throwable>> retry = t -> {
            final long[] sleep = {timeUnit.toMillis(delay)};
            Throwable exception = null;
            for (int count = retries; count >= 0; count--) {
                try {
                    return Success.of(fn.apply(t),
                                      classes);
                } catch (final Throwable e) {
                    exception = e;
                    ExceptionSoftener.softenRunnable(() -> {
                        Thread.sleep(sleep[0]);
                        return;
                    })
                                     .run();
                    sleep[0] = sleep[0] * 2;
                }
            }
            return Failure.of(exception,
                              classes);


        };
        return narrow.flatMap(retry);
    }

    @Override
    public void subscribe(Subscriber<? super T> sub) {
        requireNonNull(sub,
                       () -> "sub").onError(throwable);
    }

    @Override
    public Try<T, X> recover(Supplier<? extends T> s) {
        requireNonNull(s,
                       () -> "s");
        try {
            return Success.of(s.get(),
                              classes);
        } catch (final Throwable t) {
            return attemptFailureHandler(t);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T2, R1, R2, R3, R> Try<R, X> forEach4(Function<? super T, ? extends Try<R1, X>> value1,
                                                  BiFunction<? super T, ? super R1, ? extends Try<R2, X>> value2,
                                                  Function3<? super T, ? super R1, ? super R2, ? extends Try<R3, X>> value3,
                                                  Function4<? super T, ? super R1, ? super R2, ? super R3, ? extends R> yieldingFunction) {
        return (Try<R, X>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T2, R1, R2, R> Try<R, X> forEach3(Function<? super T, ? extends Try<R1, X>> value1,
                                              BiFunction<? super T, ? super R1, ? extends Try<R2, X>> value2,
                                              Function3<? super T, ? super R1, ? super R2, ? extends R> yieldingFunction) {
        return (Try<R, X>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R1, R> Try<R, X> forEach2(Function<? super T, ? extends Try<R1, X>> value1,
                                      BiFunction<? super T, ? super R1, ? extends R> yieldingFunction) {
        return (Try<R, X>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Try<T, Throwable> toTry() {
        return Failure.of(throwable,
                          Throwable.class);
    }

    @Override
    public Option<X> failureGet() {
        return Option.of(throwable);
    }

    @Override
    public Either<X, T> toEither() {
        return Either.left(throwable);
    }

    @Override
    public Ior<X, T> toIor() {
        return Ior.left(throwable);
    }

    @Override
    public <R> Try<R, X> coflatMap(Function<? super Try<T, X>, R> mapper) {
        requireNonNull(mapper,
                       () -> "mapper");
        try {
            return Success.of(mapper.apply(this),
                              classes);
        } catch (final Throwable t) {
            return attemptFailureHandler(t);
        }
    }

    @Override
    public Either<X, T> toEitherWithError() {
        return Either.left(throwable);
    }

    @Override
    public <T1> Try<T1, X> unit(T1 value) {
        return Success.of(value,
                          classes);
    }

    @Override
    public Option<T> get() {
        return Option.none();
    }

    @Override
    public T orElse(T value) {
        return value;
    }

    @Override
    public T orElseGet(Supplier<? extends T> valueSupplier) {
        return requireNonNull(valueSupplier,
                              () -> "valueSupplier").get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> Try<R, X> map(Function<? super T, ? extends R> mapper) {
        requireNonNull(mapper,
                       () -> "mapper");
        return (Try<R, X>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> Try<R, X> mapOrCatch(CheckedFunction<? super T, ? extends R, X> checkedFunction,
                                    Class<? extends X>... classes) {
        requireNonNull(checkedFunction,
                       () -> "checkedFunction");
        requireNonNull(classes,
                       () -> "classes");
        return (Try<R, X>) withExceptions(classes);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <XR extends Throwable> Try<T, XR> mapFailure(Function<? super X, ? extends XR> fn) {
        return Failure.of(requireNonNull(fn,
                                         () -> "fn").apply(throwable),
                          classes);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> Try<R, X> flatMap(Function<? super T, ? extends Try<? extends R, X>> fn) {
        requireNonNull(fn,
                       () -> "fn");
        return (Try<R, X>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> Try<R, X> flatMapOrCatch(CheckedFunction<? super T, ? extends Try<? extends R, X>, X> fn,
                                        Class<? extends X>... classes) {
        requireNonNull(fn,
                       () -> "fn");
        requireNonNull(classes,
                       () -> "classes");
        return (Try<R, X>) this;
    }

    @Override
    public Maybe<T> filter(Predicate<? super T> predicate) {
        requireNonNull(predicate,
                       () -> "predicate");
        return Maybe.nothing();
    }

    @Override
    public Try<T, X> filter(Predicate<? super T> test,
                            Function<? super T, ? extends X> errorGenerator) {
        return this;
    }

    @Override
    public Try<T, X> onFail(Consumer<? super X> consumer) {
        requireNonNull(consumer,
                       () -> "consumer").accept(throwable);
        return this;
    }

    @Override
    public Try<T, X> onFail(Class<? extends X> throwableSubclass,
                            Consumer<X> consumer) {
        requireNonNull(throwableSubclass,
                       () -> "throwableSubclass");
        if (throwableSubclass.isInstance(throwable)) {
            requireNonNull(consumer,
                           () -> "consumer").accept(throwable);
        }
        return this;
    }

    @Override
    public Try<T, X> recover(Function<? super X, ? extends T> fn) {
        try {
            return Success.of(requireNonNull(fn,
                                             () -> "fn").apply(throwable),
                              classes);
        } catch (final Throwable t) {
            return attemptFailureHandler(t);
        }
    }

    @Override
    public Try<T, X> recoverFlatMap(Function<? super X, ? extends Try<T, X>> fn) {
        try {
            return requireNonNull(fn,
                                  () -> "fn").apply(throwable);
        } catch (final Throwable t) {
            return attemptFailureHandler(t);
        }
    }

    private <R> Try<R, X> attemptFailureHandler(Throwable t) {
        return attemptFailureHandler(t,
                                     classes);
    }

    @SuppressWarnings("unchecked")
    private static <R, X extends Throwable> Try<R, X> attemptFailureHandler(Throwable t,
                                                                            Class<? extends Throwable>... classesToCatch) {

        if (classesToCatch == null || classesToCatch.length == 0) {
            return Failure.of((X) t,
                              Throwable.class);
        }
        val error = Stream.of(classesToCatch)
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
    public Try<T, X> recoverFlatMapFor(Class<? extends X> throwableSubClass,
                                       Function<? super X, ? extends Try<T, X>> fn) {
        try {
            if (requireNonNull(throwableSubClass,
                               () -> "throwableSubClass").isInstance(throwable)) {
                return requireNonNull(fn,
                                      () -> "fn").apply(throwable);
            }
            return this;
        } catch (final Throwable t) {
            return attemptFailureHandler(t);
        }
    }

    @Override
    public Try<T, X> recoverFor(Class<? extends X> throwableSubClass,
                                Function<? super X, ? extends T> fn) {
        try {
            if (requireNonNull(throwableSubClass,
                               () -> "throwableSubClass").isInstance(throwable)) {
                return Success.of(requireNonNull(fn,
                                                 () -> "fn").apply(throwable),
                                  classes);
            }
            return this;
        } catch (final Throwable t) {
            return attemptFailureHandler(t);
        }
    }

    @Override
    public Optional<T> toOptional() {
        return Optional.empty();
    }

    @Override
    public ReactiveSeq<T> stream() {
        return ReactiveSeq.empty();
    }

    @Override
    public Option<X> toFailedOption() {
        return Option.of(throwable);
    }

    @Override
    public Stream<X> toFailedStream() {
        return Option.of(throwable)
                     .stream();
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    @Override
    public void forEach(Consumer<? super T> consumer) {

    }

    @Override
    public void forEachFailed(Consumer<? super X> consumer) {
        requireNonNull(consumer,
                       () -> "consumer").accept(throwable);
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T2, R> Try<R, X> zip(Try<T2, X> app,
                                 BiFunction<? super T, ? super T2, ? extends R> fn) {
        return (Try<R, X>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T2, R> Try<R, X> zip(Either<X, T2> app,
                                 BiFunction<? super T, ? super T2, ? extends R> fn) {
        return (Try<R, X>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T2, R> Try<R, X> zip(Ior<X, T2> app,
                                 BiFunction<? super T, ? super T2, ? extends R> fn) {
        return (Try<R, X>) this;
    }

    @Override
    public Try<T, X> peek(Consumer<? super T> consumer) {
        return this;
    }

    @Override
    public Try<T, X> peekFailed(Consumer<? super X> consumer) {
        requireNonNull(consumer,
                       () -> "consumer").accept(throwable);
        return this;
    }

    @Override
    public Iterator<T> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public <R> R fold(Function<? super T, ? extends R> fn1,
                      Function<? super X, ? extends R> fn2) {
        requireNonNull(fn1,
                       () -> "fn1");
        return requireNonNull(fn2,
                              () -> "fn2").apply(throwable);
    }

    @Override
    public <R> R fold(Function<? super T, ? extends R> present,
                      Supplier<? extends R> absent) {
        requireNonNull(present,
                       () -> "present");
        return requireNonNull(absent,
                              () -> "absent").get();
    }

    @Override
    public Try<T, X> bipeek(Consumer<? super T> c1,
                            Consumer<? super X> c2) {
        requireNonNull(c1,
                       () -> "c1");
        requireNonNull(c2,
                       () -> "c2").accept(throwable);
        return this;
    }

    @Override
    public String mkString() {
        return ReactiveSeq.of("Try.Failure",
                              "(error:{",
                              throwable.getClass()
                                       .getName(),
                              "=",
                              throwable.getMessage(),
                              "})")
                          .join();
    }
}

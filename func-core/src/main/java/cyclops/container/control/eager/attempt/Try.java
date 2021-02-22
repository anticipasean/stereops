package cyclops.container.control.eager.attempt;


import cyclops.container.Value;
import cyclops.container.control.eager.attempt.Success.EmptySuccess;
import cyclops.container.control.eager.either.Either;
import cyclops.container.control.eager.ior.Ior;
import cyclops.container.control.eager.option.Option;
import cyclops.container.control.lazy.either.LazyEither;
import cyclops.container.control.lazy.maybe.Maybe;
import cyclops.container.control.lazy.trampoline.Trampoline;
import cyclops.container.factory.Unit;
import cyclops.container.filterable.Filterable;
import cyclops.container.foldable.OrElseValue;
import cyclops.container.foldable.Sealed2;
import cyclops.container.recoverable.RecoverableFrom;
import cyclops.container.transformable.To;
import cyclops.container.transformable.Transformable;
import cyclops.exception.ExceptionSoftener;
import cyclops.function.checked.CheckedFunction0;
import cyclops.function.checked.CheckedFunction0.SpecificallyCheckedF0;
import cyclops.function.checked.CheckedFunction1;
import cyclops.function.checked.CheckedFunction2;
import cyclops.function.checked.CheckedFunction2.SpecificallyCheckedF2;
import cyclops.function.checked.CheckedRunnable.SpecificallyCheckedRunnable;
import cyclops.function.enhanced.Function3;
import cyclops.function.enhanced.Function4;
import cyclops.function.higherkinded.DataWitness.tryType;
import cyclops.function.higherkinded.Higher;
import cyclops.function.higherkinded.Higher2;
import cyclops.reactive.ReactiveSeq;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.val;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/**
 * Success biased Try monad.
 * <p>
 * Key features
 * <p>
 * 1. Lazy / Eager / Reactive operational modes 2. Illegal states are unrepresentable 3. Exception types are explicitly declared
 * 4. Exceptions during execution are not caught be default 5. To handle exceptions during operations provide the Exception
 * classes explicitly on initialization
 * <p>
 * Create a 'successful' value
 * <pre>
 * {@code
 *  Try.success("return-value");
 * }
 * </pre>
 * <p>
 * Create a failure value
 *
 * <pre>
 * {@code
 *  Try.failure(new MyException("error details"));
 * }
 * </pre>
 * <p>
 * Exceute methods that may throw exceptions
 * <p>
 * Non-void methods
 * <pre>
 * {@code
 *
 * Try.withCatch(()-> exceptional2())
 * .map(i->i+" woo!")
 * .onFail(System.out::println)
 * .orElse("public");
 *
 *  //"hello world woo!"
 *
 *  private String exceptional2() throws RuntimeException{
 * return "hello world";
 * }
 * }
 * </pre>
 * <p>
 * Void methods
 * <pre>
 * {@code
 *
 *
 *  //Only catch IOExceptions
 *
 *  Try.runWithCatch(this::exceptional,IOException.class)
 * .onFail(System.err::println);
 *
 * private void exceptional() throws IOException{
 * throw new IOException();
 * }
 *
 * }
 * </pre>
 * <p>
 * Try with resources
 * <pre>
 * {@code
 *
 *   Try.withResources(()->new BufferedReader(new FileReader("file.txt")),
 * this::read,
 * FileNotFoundException.class,IOException.class)
 * .map(this::processData)
 * .recover(e->"public);
 *
 * }
 * </pre>
 * <p>
 * By public Try does not catch exception within it's operators such as transform / flatMap, to catch Exceptions in ongoing
 * operations use @see {@link Try#success(Object, Class...)}
 * <pre>
 * {@code
 *  Try.success(2, RuntimeException.class)
 * .map(i->{throw new RuntimeException();});
 *
 * //Failure[RuntimeException]
 *
 * }
 * </pre>
 *
 * @param <T> Return type (success)
 * @param <X> Base Error type
 * @author johnmcclean
 */
public interface Try<T, X extends Throwable> extends To<Try<T, X>>, RecoverableFrom<X, T>, Value<T>, Unit<T>, Transformable<T>,
                                                     Filterable<T>, Sealed2<T, X>, OrElseValue<T, Try<T, X>>,
                                                     Higher2<tryType, X, T> {


    static <X extends Throwable, T, R> Try<R, X> tailRec(T initial,
                                                         Function<? super T, ? extends Try<? extends Either<T, R>, X>> fn) {
        Try<? extends Either<T, R>, X>[] next = new Try[1];
        next[0] = Try.success(Either.left(initial));
        boolean cont = true;
        do {
            cont = next[0].fold(p -> p.fold(s -> {
                                                next[0] = fn.apply(s);
                                                return true;
                                            },
                                            pr -> false),
                                () -> false);
        } while (cont);
        return next[0].map(x -> x.orElse(null));
    }

    static <X extends Throwable, T> Higher<Higher<tryType, X>, T> widen(Try<T, X> narrow) {
        return narrow;
    }


    static <T, X extends Throwable> Try<T, X> narrowK2(final Higher2<tryType, X, T> t) {
        return (Try<T, X>) t;
    }

    static <T, X extends Throwable> Try<T, X> narrowK(final Higher<Higher<tryType, X>, T> t) {
        return (Try) t;
    }

    static <T, X extends Throwable> Try<T, X> fromEither(final Either<X, T> pub) {
        return Objects.requireNonNull(pub,
                                      () -> "pub")
                      .fold(Try::failure,
                            (Function<T, Try<T, X>>) Try::success);
    }

    /**
     * Construct a Try  that contains a single value extracted from the supplied reactive-streams Publisher, will catch any
     * Exceptions of the provided types
     * <pre>
     * {@code
     *   ReactiveSeq<Integer> stream =  Spouts.of(1,2,3);
     *
     * Try<Integer,Throwable> recover = Try.fromPublisher(stream, RuntimeException.class);
     *
     * //Try[1]
     *
     * }
     * </pre>
     *
     * @param pub Publisher to extract value from
     * @return Try populated with first value from Publisher
     */
    @SafeVarargs
    static <T, X extends Throwable> Try<T, X> fromPublisher(final Publisher<T> pub,
                                                            final Class<X>... classes) {

        return LazyEither.fromPublisher(pub)
                         .mapLeft(t -> {
                             if (classes.length == 0) {
                                 return Try.<T, X>failure((X) t);
                             }
                             val error = Stream.of(classes)
                                               .filter(c -> c.isAssignableFrom(t.getClass()))
                                               .findFirst();
                             if (error.isPresent()) {
                                 return Try.<T, X>failure((X) t);
                             } else {
                                 throw ExceptionSoftener.throwSoftenedException(t);
                             }
                         })
                         .fold(failure -> failure,
                               (Function<T, Try<T, X>>) Try::<T, X>success);
    }

    /**
     * Construct a Try  that contains a single value extracted from the supplied reactive-streams Publisher
     *
     * <pre>
     * {@code
     *   ReactiveSeq<Integer> stream =  Spouts.of(1,2,3);
     *
     * Try<Integer,Throwable> recover = Try.fromPublisher(stream);
     *
     * //Try[1]
     *
     * }
     * </pre>
     *
     * @param pub Publisher to extract value from
     * @return Try populated with first value from Publisher
     */
    static <T> Try<T, Throwable> fromPublisher(final Publisher<T> pub) {
        return fromPublisher(pub,
                             Throwable.class);
    }

    /**
     * Construct a Try  that contains a single value extracted from the supplied Iterable
     *
     * <pre>
     * {@code
     *   ReactiveSeq<Integer> stream =  ReactiveSeq.of(1,2,3);
     *
     * Try<Integer,Throwable> recover = Try.fromIterable(stream);
     *
     * //Try[1]
     *
     * }
     * </pre>
     *
     * @param iterable Iterable to extract value from
     * @return Try populated with first value from Iterable
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    static <T, X extends Throwable> Try<T, X> fromIterable(final Iterable<T> iterable,
                                                           final T alt) {
        if (iterable instanceof Try) {
            return (Try) iterable;
        }
        return ReactiveSeq.fromIterable(iterable)
                          .headOption()
                          .fold(t -> Success.of(t,
                                                Throwable.class),
                                () -> Success.of(alt,
                                                 Throwable.class));
    }

    /**
     * Construct a Failure instance from a throwable (an implementation of Try)
     * <pre>
     * {@code
     *    Failure<Exception> failure = Try.failure(new RuntimeException());
     * }
     * </pre>
     *
     * @param error for Failure
     * @return new Failure with error
     */
    @SuppressWarnings("unchecked")
    static <T, X extends Throwable> Try<T, X> failure(final X error) {
        return Failure.of(error,
                          new Class[]{Throwable.class});
    }

    /**
     * Construct a Success instance (an implementation of Try)
     *
     * <pre>
     * {@code
     *    Success<Integer> success = Try.success(new RuntimeException());
     * }
     * </pre>
     *
     * @param value Successful value
     * @return new Success with value
     */
    static <T, X extends Throwable> Try<T, X> success(final T value) {
        return success(value,
                       Throwable.class);
    }

    @SafeVarargs
    static <T, X extends Throwable> Try<T, X> success(final T value,
                                                      final Class<? extends Throwable>... classes) {
        return Success.of(value,
                          classes);
    }

    @SafeVarargs
    static <T extends AutoCloseable, R, X extends Throwable> Try<R, X> withResources(CheckedFunction0<T> rs,
                                                                                     CheckedFunction<? super T, ? extends R, X> fn,
                                                                                     Class<? extends X>... classes) {
        try {
            T in = ExceptionSoftener.softenSupplier(() -> rs.get())
                                    .get();
            try {
                return Try.success(fn.apply(in));
            } catch (Throwable t) {
                return handleError(t,
                                   classes);
            } finally {
                ExceptionSoftener.softenRunnable(() -> in.close())
                                 .run();

            }
        } catch (Throwable e) {
            return handleError(e,
                               classes);
        }
    }

    @SuppressWarnings("unchecked")
    static <R, X extends Throwable> Try<R, X> handleError(Throwable t,
                                                          Class<? extends X>[] classes) {
        Either<Throwable, ? extends R> x = Either.left(orThrow(Stream.of(classes)
                                                                     .filter(c -> c.isAssignableFrom(t.getClass()))
                                                                     .map(c -> t)
                                                                     .findFirst(),
                                                               t));
        return (Try<R, X>) Try.fromEither(x);
    }

    @SafeVarargs
    static <T1 extends AutoCloseable, T2 extends AutoCloseable, R, X extends Throwable> Try<R, X> withResources(SpecificallyCheckedF0<T1, X> rs1,
                                                                                                                SpecificallyCheckedF0<T2, X> rs2,
                                                                                                                SpecificallyCheckedF2<? super T1, ? super T2, ? extends R, X> fn,
                                                                                                                Class<? extends X>... classes) {
        try {
            T1 t1 = ExceptionSoftener.softenSupplier(() -> rs1.get())
                                     .get();
            T2 t2 = ExceptionSoftener.softenSupplier(() -> rs2.get())
                                     .get();
            try {
                return Try.success(fn.apply(t1,
                                            t2));
            } catch (Throwable t) {
                return handleError(t,
                                   classes);
            } finally {
                ExceptionSoftener.softenRunnable(() -> t1.close())
                                 .run();
                ExceptionSoftener.softenRunnable(() -> t2.close())
                                 .run();
            }
        } catch (Throwable e) {
            return handleError(e,
                               classes);
        }
    }

    static <T extends AutoCloseable, R> Try<R, Throwable> withResources(CheckedFunction0<T> rs,
                                                                        CheckedFunction1<? super T, ? extends R> fn) {
        try {
            T in = ExceptionSoftener.softenSupplier(rs)
                                    .get();
            try {
                return Try.success(fn.apply(in));
            } catch (Throwable t) {
                return Try.failure(t);
            } finally {
                ExceptionSoftener.softenRunnable(() -> in.close())
                                 .run();

            }
        } catch (Throwable e) {
            return Try.failure(e);
        }
    }

    static <T1 extends AutoCloseable, T2 extends AutoCloseable, R> Try<R, Throwable> withResources(CheckedFunction0<T1> rs1,
                                                                                                   CheckedFunction0<T2> rs2,
                                                                                                   CheckedFunction2<? super T1, ? super T2, ? extends R> fn) {
        try {
            T1 t1 = ExceptionSoftener.softenSupplier(rs1)
                                     .get();
            T2 t2 = ExceptionSoftener.softenSupplier(rs2)
                                     .get();
            try {
                return Try.success(fn.apply(t1,
                                            t2));
            } catch (Throwable t) {
                return Try.failure(t);
            } finally {
                ExceptionSoftener.softenRunnable(() -> t1.close())
                                 .run();
                ExceptionSoftener.softenRunnable(() -> t2.close())
                                 .run();
            }
        } catch (Throwable e) {
            return Try.failure(e);
        }
    }

    /**
     * Try to execute supplied Supplier and will Catch specified Excpetions or java.lang.Exception if none specified.
     *
     * @param checkedSupplier CheckedSupplier to recover to execute
     * @param classes         Exception types to catch (or java.lang.Exception if none specified)
     * @return New Try
     */
    @SafeVarargs
    static <T, X extends Throwable> Try<T, X> withCatch(final SpecificallyCheckedF0<T, X> checkedSupplier,
                                                        final Class<? extends X>... classes) {
        Objects.requireNonNull(checkedSupplier,
                               () -> "checkedSupplier");
        try {
            return Try.success(checkedSupplier.get());
        } catch (final Throwable t) {
            if (classes.length == 0) {
                return Try.failure((X) t);
            }
            val error = Stream.of(classes)
                              .filter(c -> c.isAssignableFrom(t.getClass()))
                              .findFirst();
            if (error.isPresent()) {
                return Try.failure((X) t);
            } else {
                throw ExceptionSoftener.throwSoftenedException(t);
            }
        }

    }

    /**
     * Try to execute supplied Runnable and will Catch specified Excpetions or java.lang.Exception if none specified.
     *
     * @param checkedRunnable CheckedRunnable to recover to execute
     * @param classes         Exception types to catch (or java.lang.Exception if none specified)
     * @return New Try
     */
    @SuppressWarnings("unchecked")
    @SafeVarargs
    static <X extends Throwable> Try<EmptySuccess, X> runWithCatch(final SpecificallyCheckedRunnable<X> checkedRunnable,
                                                                   final Class<? extends X>... classes) {
        Objects.requireNonNull(checkedRunnable,
                               () -> "checkedRunnable");
        Objects.requireNonNull(classes,
                               () -> "classes");
        try {
            checkedRunnable.run();
            //Not permitting Void (-> null) as a valid Success value and therefore using a hollow enum
            return Try.success(EmptySuccess.INSTANCE);
        } catch (final Throwable t) {
            return (Try<EmptySuccess, X>) Try.<EmptySuccess, Throwable>failure(t).withExceptions(classes);
        }

    }

    static <T, X extends Throwable> Try<T, X> flatten(Try<? extends Try<T, X>, X> nested) {
        return nested.flatMap(Function.identity());
    }

    static Throwable orThrow(final Optional<Throwable> findFirst,
                             final Throwable t) {
        if (findFirst.isPresent()) {
            return findFirst.get();
        }
        throw ExceptionSoftener.throwSoftenedException(t);

    }


    default int arity() {
        return 2;
    }

    public Either<X, T> asEither();

    public Try<T, X> withExceptions(Class<? extends X>... toCatch);

    public Trampoline<Either<X, T>> toTrampoline();

    @Override
    public Try<T, X> recoverWith(Supplier<? extends Try<T, X>> supplier);

    /**
     * Retry a transformation if it fails. Default settings are to retry up to 7 times, with an doubling backoff period starting @
     * 2 seconds delay before retry.
     *
     * @param fn Function to retry if fails
     */
    public <R> Try<R, Throwable> retry(final Function<? super T, ? extends R> fn);

    /**
     * Retry a transformation if it fails. Retries up to <b>retries</b> times, with an doubling backoff period starting @
     * <b>delay</b> TimeUnits delay before retry.
     *
     * @param fn       Function to retry if fails
     * @param retries  Number of retries
     * @param delay    Delay in TimeUnits
     * @param timeUnit TimeUnit to use for delay
     */
    public <R> Try<R, Throwable> retry(final Function<? super T, ? extends R> fn,
                                       final int retries,
                                       final long delay,
                                       final TimeUnit timeUnit);

    @Override
    public void subscribe(Subscriber<? super T> sub);

    public Try<T, X> recover(Supplier<? extends T> s);

    public <T2, R1, R2, R3, R> Try<R, X> forEach4(Function<? super T, ? extends Try<R1, X>> value1,
                                                  BiFunction<? super T, ? super R1, ? extends Try<R2, X>> value2,
                                                  Function3<? super T, ? super R1, ? super R2, ? extends Try<R3, X>> value3,
                                                  Function4<? super T, ? super R1, ? super R2, ? super R3, ? extends R> yieldingFunction);

    public <T2, R1, R2, R> Try<R, X> forEach3(Function<? super T, ? extends Try<R1, X>> value1,
                                              BiFunction<? super T, ? super R1, ? extends Try<R2, X>> value2,
                                              Function3<? super T, ? super R1, ? super R2, ? extends R> yieldingFunction);

    public <R1, R> Try<R, X> forEach2(Function<? super T, ? extends Try<R1, X>> value1,
                                      BiFunction<? super T, ? super R1, ? extends R> yieldingFunction);

    @Override
    public Try<T, Throwable> toTry();

    public Option<X> failureGet();

    public Either<X, T> toEither();

    public Ior<X, T> toIor();

    public <R> Try<R, X> coflatMap(final Function<? super Try<T, X>, R> mapper);

    @Override
    default <U> Option<U> ofType(Class<? extends U> type) {
        return (Option<U>) Filterable.super.ofType(type);
    }

    @Override
    default Option<T> filterNot(Predicate<? super T> fn) {
        return (Option<T>) Filterable.super.filterNot(fn);
    }

    @Override
    default Option<T> notNull() {
        return (Option<T>) Filterable.super.notNull();
    }

    public Either<X, T> toEitherWithError();

    @Override
    public <T> Try<T, X> unit(final T value);

    public Option<T> get();

    @Override
    public T orElse(T value);

    @Override
    public T orElseGet(Supplier<? extends T> value);

    @Override
    public <R> Try<R, X> map(Function<? super T, ? extends R> mapper);

    /**
     * Perform a mapping operation that may catch the supplied Exception types The supplied Exception types are only applied
     * during this map operation
     *
     * @param checkedFunction mapping function
     * @param classes         exception types to catch
     * @param <R>             return type of mapping function
     * @return Try with result or caught exception
     */
    public <R> Try<R, X> mapOrCatch(CheckedFunction<? super T, ? extends R, X> checkedFunction,
                                    Class<? extends X>... classes);

    public <XR extends Throwable> Try<T, XR> mapFailure(Function<? super X, ? extends XR> fn);

    /**
     * @param fn FlatMap success value or do nothing if Failure (return this)
     * @return Try returned from FlatMap fn
     */
    public <R> Try<R, X> flatMap(Function<? super T, ? extends Try<? extends R, X>> fn);

    /**
     * Perform a flatMapping operation that may catch the supplied Exception types The supplied Exception types are only applied
     * during this map operation
     *
     * @param fn      flatMapping function
     * @param classes exception types to catch
     * @param <R>     return type of mapping function
     * @return Try with result or caught exception
     */
    public <R> Try<R, X> flatMapOrCatch(CheckedFunction<? super T, ? extends Try<? extends R, X>, X> fn,
                                        Class<? extends X>... classes);

    /**
     * @param p Convert a Success to a Failure (with a null value for Exception) if predicate does not hold. Do nothing to a
     *          Failure
     * @return this if Success and Predicate holds, or if Failure. New Failure if Success and Predicate fails
     */
    @Override
    public Maybe<T> filter(Predicate<? super T> p);

    public Try<T, X> filter(Predicate<? super T> test,
                            Function<? super T, ? extends X> errorGenerator);

    /**
     * @param consumer Accept Exception if present (Failure)
     * @return this
     */
    public Try<T, X> onFail(Consumer<? super X> consumer);

    /**
     * @param t        Class type of Exception to handle
     * @param consumer Accept Exception if present (Failure) and if class types fold
     * @return this
     */
    public Try<T, X> onFail(Class<? extends X> t,
                            Consumer<X> consumer);

    /**
     * @param fn Recovery function - transform from a failure to a Success.
     * @return new Try
     */
    public Try<T, X> recover(Function<? super X, ? extends T> fn);

    /**
     * flatMap recovery
     *
     * @param fn Recovery FlatMap function. Map from a failure to a Success
     * @return Success from recovery function
     */
    public Try<T, X> recoverFlatMap(Function<? super X, ? extends Try<T, X>> fn);

    public Try<T, X> recoverFlatMapFor(Class<? extends X> t,
                                       Function<? super X, ? extends Try<T, X>> fn);

    /**
     * Recover if exception is of specified type
     *
     * @param t  Type of exception to fold against
     * @param fn Recovery function
     * @return New Success if failure and types fold / otherwise this
     */
    public Try<T, X> recoverFor(Class<? extends X> t,
                                Function<? super X, ? extends T> fn);

    /**
     * @return Optional present if Success, Optional empty if failure
     */
    @Override
    public Optional<T> toOptional();

    /**
     * @return Stream with value if Sucess, Empty Stream if failure
     */
    @Override
    public ReactiveSeq<T> stream();

    public Option<X> toFailedOption();

    /**
     * @return Stream with error if Failure, Empty Stream if success
     */
    public Stream<X> toFailedStream();

    /**
     * @return true if Success / false if Failure
     */
    public boolean isSuccess();

    /**
     * @return True if Failure / false if Success
     */
    public boolean isFailure();

    /**
     * @param consumer Accept value if Success / not called on Failure
     */
    @Override
    public void forEach(Consumer<? super T> consumer);

    /**
     * @param consumer Accept value if Failure / not called on Failure
     */
    public void forEachFailed(Consumer<? super X> consumer);

    @Override
    public boolean isPresent();

    public <T2, R> Try<R, X> zip(final Try<T2, X> app,
                                 final BiFunction<? super T, ? super T2, ? extends R> fn);

    public <T2, R> Try<R, X> zip(final Either<X, T2> app,
                                 final BiFunction<? super T, ? super T2, ? extends R> fn);

    public <T2, R> Try<R, X> zip(final Ior<X, T2> app,
                                 final BiFunction<? super T, ? super T2, ? extends R> fn);

    /**
     * @param consumer Accept value if Success
     * @return this
     */
    @Override
    public Try<T, X> peek(final Consumer<? super T> consumer);

    /**
     * @param consumer Accept Exception if Failure
     * @return this
     */
    public Try<T, X> peekFailed(final Consumer<? super X> consumer);

    @Override
    public Iterator<T> iterator();

    @Override
    public <R> R fold(Function<? super T, ? extends R> fn1,
                      Function<? super X, ? extends R> fn2);

    @Override
    public <R> R fold(Function<? super T, ? extends R> present,
                      Supplier<? extends R> absent);

    public Try<T, X> bipeek(Consumer<? super T> c1,
                            Consumer<? super X> c2);


    @Override
    public String mkString();

    @Override
    public String toString();


    public interface CheckedFunction<T, R, X extends Throwable> {

        R apply(T t) throws X;

        default Function<T, R> asFunction() {
            return i -> {
                try {
                    return apply(i);
                } catch (Throwable t) {
                    throw ExceptionSoftener.throwSoftenedException(t);
                }
            };
        }
    }

    //    public interface CheckedBiFunction<T1, T2, R, X extends Throwable> {
    //
    //        R apply(T1 t,
    //                T2 t2) throws X;
    //    }
    //
    //    public interface CheckedSupplier<T, X extends Throwable> {
    //
    //        T get() throws X;
    //    }
    //
    //
    //    public interface CheckedConsumer<T, X extends Throwable> {
    //
    //        void accept(T t) throws X;
    //    }
    //
    //    public interface CheckedRunnable<X extends Throwable> {
    //
    //        void run() throws X;
    //    }


}

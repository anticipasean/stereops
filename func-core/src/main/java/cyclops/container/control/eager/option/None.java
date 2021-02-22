package cyclops.container.control.eager.option;

import static java.util.Objects.requireNonNull;

import cyclops.container.MonadicValue;
import java.util.Optional;
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
final class None<T> implements Option<T> {

    private static final long serialVersionUID = 9117268970047322720L;

    /**
     * Better at enforcing singleton use than the public static constant instance and limits type coercion to this holder getter so
     * that warnings need not be suppressed elsewhere
     */
    private static enum NoneInstanceHolder {
        INSTANCE(new None<>());
        private final None<?> none;

        NoneInstanceHolder(None<?> none) {
            this.none = none;
        }

        @SuppressWarnings("unchecked")
        public <T> None<T> getNone() {
            return (None<T>) none;
        }
    }

    static <T> None<T> none() {
        return NoneInstanceHolder.INSTANCE.getNone();
    }

    private Object readResolve() {
        return none();
    }

    @Override
    public <R> Option<R> map(final Function<? super T, ? extends R> mapper) {
        return none();
    }

    @Override
    public <R> Option<R> flatMap(final Function<? super T, ? extends MonadicValue<? extends R>> mapper) {
        return none();

    }

    @Override
    public Option<T> filter(final Predicate<? super T> test) {
        return none();
    }


    @Override
    public Option<T> recover(final T value) {
        return Option.of(value);
    }

    @Override
    public Option<T> recover(final Supplier<? extends T> value) {
        return Option.of(requireNonNull(value, () -> "value").get());
    }

    @Override
    public Option<T> recoverWith(Supplier<? extends Option<T>> fn) {
        return requireNonNull(fn,
                              () -> "fn").get();
    }


    @Override
    public <R> R fold(final Function<? super T, ? extends R> some,
                      final Supplier<? extends R> none) {
        return requireNonNull(none,
                              () -> "none").get();
    }

    @Override
    public Optional<T> toOptional() {
        return Optional.empty();
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

        if (obj instanceof None) {
            return true;
        } else if (obj instanceof Option) {
            Option<T> opt = (Option<T>) obj;
            return !opt.isPresent();
        }
        return false;
    }

    @Override
    public T orElse(final T value) {
        return value;
    }

    @Override
    public T orElseGet(final Supplier<? extends T> value) {
        return requireNonNull(value,
                              () -> "value").get();
    }

    @Override
    public <R> None<R> concatMap(final Function<? super T, ? extends Iterable<? extends R>> mapper) {
        return none();
    }

    @Override
    public <R> None<R> mergeMap(final Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return none();
    }

    @Override
    public void forEach(Consumer<? super T> action) {

    }

//    @Override
//    public <R> R fold(Function<? super T, ? extends R> fn1,
//                      Function<? super None<T>, ? extends R> fn2) {
//        return requireNonNull(fn2,
//                              () -> "fn2").apply(none());
//    }
}

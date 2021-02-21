package cyclops.container.control.option;

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

    private static enum OptionNoneHolder {
        INSTANCE(new None<>());
        private final None<?> none;

        OptionNoneHolder(None<?> none) {
            this.none = none;
        }

        @SuppressWarnings("unchecked")
        public <T> None<T> getNone() {
            return (None<T>) none;
        }
    }

    public static <T> Option<T> none() {
        return OptionNoneHolder.INSTANCE.getNone();
    }

    private Object readResolve() {
        return OptionNoneHolder.INSTANCE.getNone();
    }

    @Override
    public <R> Option<R> map(final Function<? super T, ? extends R> mapper) {
        return OptionNoneHolder.INSTANCE.getNone();
    }

    @Override
    public <R> Option<R> flatMap(final Function<? super T, ? extends MonadicValue<? extends R>> mapper) {
        return OptionNoneHolder.INSTANCE.getNone();

    }

    @Override
    public Option<T> filter(final Predicate<? super T> test) {
        return OptionNoneHolder.INSTANCE.getNone();
    }


    @Override
    public Option<T> recover(final T value) {
        return Option.of(value);
    }

    @Override
    public Option<T> recover(final Supplier<? extends T> value) {
        return Option.of(value.get());
    }

    @Override
    public Option<T> recoverWith(Supplier<? extends Option<T>> fn) {
        return fn.get();
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
        return value.get();
    }

    @Override
    public <R> None<R> concatMap(final Function<? super T, ? extends Iterable<? extends R>> mapper) {
        return OptionNoneHolder.INSTANCE.getNone();
    }

    @Override
    public <R> None<R> mergeMap(final Function<? super T, ? extends Publisher<? extends R>> mapper) {
        return OptionNoneHolder.INSTANCE.getNone();
    }

    @Override
    public void forEach(Consumer<? super T> action) {

    }

    @Override
    public <R> R fold(Function<? super T, ? extends R> fn1,
                      Function<? super None<T>, ? extends R> fn2) {
        return fn2.apply(this);
    }
}

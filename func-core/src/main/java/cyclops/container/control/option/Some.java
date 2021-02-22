package cyclops.container.control.option;

import static java.util.Objects.requireNonNull;

import cyclops.container.MonadicValue;
import cyclops.container.control.Maybe;
import cyclops.container.foldable.Present;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * @author smccarron
 * @created 2021-02-21
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
final class Some<T> implements Option<T>, Present<T> {

    private static final long serialVersionUID = -6676951947890006283L;
    private final T value;

    public T get() {
        return value;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public Option<T> recover(Supplier<? extends T> value) {
        return this;
    }

    @Override
    public Option<T> recover(T value) {
        return this;
    }

    @Override
    public Option<T> recoverWith(Supplier<? extends Option<T>> fn) {
        return this;
    }

    @Override
    public <R> Option<R> map(Function<? super T, ? extends R> mapper) {
        if (value == null) {
            return Option.none();
        }
        return new Some<>(requireNonNull(mapper,
                                         () -> "mapper").apply(value));
    }

    @Override
    public <R> Option<R> flatMap(Function<? super T, ? extends MonadicValue<? extends R>> mapper) {
        return Option.narrow(requireNonNull(mapper,
                                            () -> "mapper").apply(value)
                                                           .toOption());
    }

    @Override
    public <R> R fold(Function<? super T, ? extends R> some,
                      Supplier<? extends R> none) {
        if (value == null) {
            return requireNonNull(none,
                                  () -> "none").get();
        }
        return requireNonNull(some,
                              () -> "some").apply(value);
    }

    @Override
    public Option<T> filter(Predicate<? super T> predicate) {
        if (value == null) {
            return Option.none();
        }
        return requireNonNull(predicate,
                              () -> "predicate").test(value) ? this : Option.none();
    }

    @Override
    public String toString() {
        return mkString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Some) {
            Some s = (Some) obj;
            return Objects.equals(value,
                                  s.value);
        }
        if (obj instanceof Present) {
            return Objects.equals(value,
                                  ((Maybe) obj).orElse(null));
        } else if (obj instanceof Option) {
            Option<T> opt = (Option<T>) obj;
            if (opt.isPresent()) {
                return Objects.equals(value,
                                      opt.orElse(null));
            }

        }
        return false;
    }

    @Override
    public <R> R fold(Function<? super T, ? extends R> fn1,
                      Function<? super None<T>, ? extends R> fn2) {
        if (value == null) {
            return requireNonNull(fn2.apply(None.none()));
        }
        return requireNonNull(fn1,
                              () -> "fn1").apply(value);
    }

    @Override
    public T orElse(T alt) {
        return value;
    }

}

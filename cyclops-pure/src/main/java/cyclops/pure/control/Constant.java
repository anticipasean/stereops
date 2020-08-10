package cyclops.pure.control;


import cyclops.container.foldable.Deconstructable.Deconstructable1;
import cyclops.function.higherkinded.DataWitness.constant;
import cyclops.function.higherkinded.Higher;
import cyclops.function.higherkinded.Higher2;
import cyclops.container.immutable.tuple.Tuple;
import cyclops.container.immutable.tuple.Tuple1;
import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * @param <T> Value type
 * @param <P> Phantom type
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constant<T, P> implements Higher2<constant, T, P>, Supplier<T>, Deconstructable1<T>, Serializable {

    private static final long serialVersionUID = 1L;

    public final T value;

    public static <T, P> Constant<T, P> of(T value) {
        return new Constant<>(value);
    }

    public static <T, P> Constant<T, P> narrowK2(Higher2<constant, T, P> constant) {
        return (Constant<T, P>) constant;
    }

    public static <T, P> Constant<T, P> narrowK(Higher<Higher<constant, T>, P> constant) {
        return (Constant<T, P>) constant;
    }

    public <R> Constant<T, R> map(Function<? super P, ? extends R> fn) {
        return of(value);
    }

    public T get() {
        return value;
    }

    @Override
    public Tuple1<T> unapply() {
        return Tuple.tuple(value);
    }


}

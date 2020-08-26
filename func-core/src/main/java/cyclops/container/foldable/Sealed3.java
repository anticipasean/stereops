package cyclops.container.foldable;


import java.util.function.Function;

public interface Sealed3<T1, T2, T3> {

    <R> R fold(Function<? super T1, ? extends R> fn1,
               Function<? super T2, ? extends R> fn2,
               Function<? super T3, ? extends R> fn3);

}

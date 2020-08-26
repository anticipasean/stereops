package cyclops.container.foldable;


import java.util.function.Function;
import java.util.function.Supplier;

public interface SealedOr<T1> {

    <R> R fold(Function<? super T1, ? extends R> fn1,
               Supplier<? extends R> s);

}

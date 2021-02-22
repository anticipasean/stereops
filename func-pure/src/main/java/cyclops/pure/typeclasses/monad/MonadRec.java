package cyclops.pure.typeclasses.monad;


import cyclops.function.higherkinded.Higher;
import cyclops.container.control.eager.either.Either;
import java.util.function.Function;

// ref http://functorial.com/stack-safety-for-free/index.pdf Stack Safety for Free

public interface MonadRec<W> {

    <T, R> Higher<W, R> tailRec(T initial,
                                Function<? super T, ? extends Higher<W, ? extends Either<T, R>>> fn);
}

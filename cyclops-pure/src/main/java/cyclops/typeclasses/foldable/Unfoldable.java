package cyclops.typeclasses.foldable;

import static cyclops.container.immutable.tuple.Tuple.tuple;

import cyclops.function.higherkinded.Higher;
import cyclops.container.control.Option;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.typeclasses.Pure;
import cyclops.typeclasses.monad.Applicative;
import cyclops.typeclasses.monad.Traverse;
import java.util.function.Function;


public interface Unfoldable<W> {

    <R, T> Higher<W, R> unfold(T b,
                               Function<? super T, Option<Tuple2<R, T>>> fn);

    default <T> Higher<W, T> replicate(long n,
                                       T value) {
        return unfold(n,
                      i -> i > 0 ? Option.of(tuple(value,
                                                   i < Long.MAX_VALUE ? i - 1 : i)) : Option.none());
    }

    default <W2, T> Higher<W2, Higher<W, T>> replicateA(long n,
                                                        Higher<W2, T> ds,
                                                        Applicative<W2> applicative,
                                                        Traverse<W> traversable) {
        return traversable.sequenceA(applicative,
                                     replicate(n,
                                               ds));
    }

    default <T, R> Higher<W, R> none() {
        return unfold((T) null,
                      t -> Option.none());
    }

    default <T> Higher<W, T> one(T a) {
        return replicate(1,
                         a);
    }


    class UnsafeValueUnfoldable<W> implements Unfoldable<W> {

        Pure<W> pure;

        @Override
        public <R, T> Higher<W, R> unfold(T b,
                                          Function<? super T, Option<Tuple2<R, T>>> fn) {
            Option<Tuple2<R, T>> x = fn.apply(b);
            R r = x.map(t -> t._1())
                   .orElse(null);
            return pure.unit(r);
        }
    }
}

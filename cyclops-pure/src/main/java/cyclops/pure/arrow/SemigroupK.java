package cyclops.pure.arrow;


import cyclops.function.higherkinded.Higher;
import cyclops.function.combiner.Semigroup;

@FunctionalInterface
public interface SemigroupK<W> {

    <T> Higher<W, T> apply(Higher<W, T> t1,
                           Higher<W, T> t2);

    default <T> Semigroup<Higher<W, T>> asSemigroup() {
        return (a, b) -> this.apply(a,
                                    b);
    }

}


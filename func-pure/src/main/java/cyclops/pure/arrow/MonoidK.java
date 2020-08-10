package cyclops.pure.arrow;


import cyclops.function.higherkinded.Higher;
import cyclops.function.combiner.Monoid;

public interface MonoidK<W> extends SemigroupK<W> {

    <T> Higher<W, T> zero();

    default <T> Monoid<Higher<W, T>> asMonoid() {
        return Monoid.of(zero(),
                         (a, b) -> this.apply(a,
                                              b));
    }

}

package cyclops.stream.push;

import cyclops.container.traversable.IterableX;
import cyclops.container.control.eager.option.Option;
import cyclops.container.basetests.AbstractIterableXTest;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.reactive.companion.Spouts;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class SpoutsIterableXTest extends AbstractIterableXTest {

    @Override
    public <T> IterableX<T> empty() {
        return Spouts.empty();
    }

    @Override
    public <T> IterableX<T> of(T... values) {
        return Spouts.of(values);
    }

    @Override
    public IterableX<Integer> range(int start,
                                    int end) {
        return Spouts.range(start,
                            end);
    }

    @Override
    public IterableX<Long> rangeLong(long start,
                                     long end) {
        return Spouts.rangeLong(start,
                                end);
    }

    @Override
    public <T> IterableX<T> iterate(int times,
                                    T seed,
                                    UnaryOperator<T> fn) {
        return Spouts.iterate(seed,
                              fn)
                     .take(times);
    }

    @Override
    public <T> IterableX<T> generate(int times,
                                     Supplier<T> fn) {
        return Spouts.generate(fn)
                     .take(times);
    }

    @Override
    public <U, T> IterableX<T> unfold(U seed,
                                      Function<? super U, Option<Tuple2<T, U>>> unfolder) {
        return Spouts.unfold(seed,
                             unfolder);
    }
}

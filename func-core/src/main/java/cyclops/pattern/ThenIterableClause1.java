package cyclops.pattern;


import cyclops.container.control.Option;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.reactive.ReactiveSeq;
import cyclops.stream.type.Streamable;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author smccarron
 */
public interface ThenIterableClause1<V, I> extends Clause<Tuple2<V, Option<Iterable<I>>>> {

    static <V, I, O> ThenIterableClause1<V, I> of(Supplier<Tuple2<V, Option<Iterable<I>>>> valueSupplier) {
        return new ThenIterableClause1<V, I>() {
            @Override
            public Tuple2<V, Option<Iterable<I>>> get() {
                return valueSupplier.get();
            }
        };
    }

    default <O> OrMatchClause1<V, I, O> then(Function<ReactiveSeq<I>, O> mapper) {
        return OrMatchClause1.of(() -> MatchResult1.of(subject().map2(inputTypeAsOpt -> inputTypeAsOpt.map(iter -> ReactiveSeq.fromIterable(iter))
                                                                                                      .map(mapper))
                                                                ._2()
                                                                .toEither(Tuple2.of(subject()._1(),
                                                                                    Option.none()))));
    }

}

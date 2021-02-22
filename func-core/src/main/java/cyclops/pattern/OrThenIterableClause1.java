package cyclops.pattern;


import cyclops.container.control.eager.either.Either;
import cyclops.container.control.eager.option.Option;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.reactive.ReactiveSeq;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author smccarron
 */
public interface OrThenIterableClause1<V, I, O> extends Clause<MatchResult1<V, Iterable<I>, O>> {

    static <V, I, O> OrThenIterableClause1<V, I, O> of(Supplier<MatchResult1<V, Iterable<I>, O>> supplier) {
        return new OrThenIterableClause1<V, I, O>() {
            @Override
            public MatchResult1<V, Iterable<I>, O> get() {
                return supplier.get();
            }
        };
    }

    default OrMatchClause1<V, I, O> then(Function<ReactiveSeq<I>, O> mapper) {
        return OrMatchClause1.of(() -> MatchResult1.of(subject().either()
                                                                .mapLeft(Tuple2::_2)
                                                                .mapLeft(iIterOpt -> iIterOpt.map(ReactiveSeq::fromIterable)
                                                                                             .map(mapper)).<Either<Tuple2<V, Option<I>>, O>>fold(outputOpt -> outputOpt.map(Either::<Tuple2<V, Option<I>>, O>right)
                                                                                                                                                                       .orElse(Either.<Tuple2<V, Option<I>>, O>left(Tuple2.of(subject().unapply()
                                                                                                                                                                                                                                       ._1(),
                                                                                                                                                                                                                              Option.none()))),
                                                                                                                                                 existingOutput -> Either.right(existingOutput))));
    }

}

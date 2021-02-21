package cyclops.pattern;

import cyclops.container.control.Either;
import cyclops.container.control.option.Option;
import cyclops.container.immutable.tuple.Tuple2;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author smccarron
 */
public interface OrThenOptionClause1<V, I, O> extends Clause<MatchResult1<V, Option<I>, O>> {

    static <V, I, O> OrThenOptionClause1<V, I, O> of(Supplier<MatchResult1<V, Option<I>, O>> matchResult1Supplier) {
        return new OrThenOptionClause1<V, I, O>() {
            @Override
            public MatchResult1<V, Option<I>, O> get() {
                return matchResult1Supplier.get();
            }
        };
    }

    default OrMatchClause1<V, I, O> then(Function<Option<I>, O> mapper) {
        return OrMatchClause1.of(() -> MatchResult1.of(subject().either()
                                                                .mapLeft(Tuple2::_2)
                                                                .mapLeft(optOpt -> optOpt.map(mapper))
                                                                .fold(oOpt -> oOpt.toEither(Tuple2.of(subject().unapply()
                                                                                                               ._1(),
                                                                                                      Option.none())),
                                                                      Either::right)));
    }
}

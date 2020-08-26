package cyclops.pattern;

import cyclops.container.control.Option;
import cyclops.container.immutable.tuple.Tuple2;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author smccarron
 */
public interface OrThenClause2<K, V, KI, VI, KO, VO> extends Clause<MatchResult2<K, V, KI, VI, KO, VO>> {

    static <K, V, KI, VI, KO, VO> OrThenClause2<K, V, KI, VI, KO, VO> of(Supplier<MatchResult2<K, V, KI, VI, KO, VO>> keyValueMatchResultsSupplier) {
        return new OrThenClause2<K, V, KI, VI, KO, VO>() {
            @Override
            public MatchResult2<K, V, KI, VI, KO, VO> get() {
                return keyValueMatchResultsSupplier.get();
            }
        };
    }

    default OrMatchClause2<K, V, KI, VI, KO, VO> then(BiFunction<KI, VI, Tuple2<KO, VO>> biMapper) {
        return OrMatchClause2.of(() -> MatchResult2.of(subject().either()
                                                                .mapLeft(Tuple2::_2)
                                                                .mapLeft(kiviOptTuple -> kiviOptTuple.map(kiviTuple -> biMapper.apply(kiviTuple._1(),
                                                                                                                                      kiviTuple._2())))
                                                                .flatMapLeft(kovoTupleOpt -> kovoTupleOpt.toEither(Tuple2.of(subject().unapply()
                                                                                                                                      ._1(),
                                                                                                                             Option.none())))));
    }

    default OrMatchClause2<K, V, KI, VI, KO, VO> then(Function<KI, KO> keyMapper,
                                                      Function<VI, VO> valueMapper) {
        return OrMatchClause2.of(() -> MatchResult2.of(subject().either()
                                                                .mapLeft(Tuple2::_2)
                                                                .mapLeft(kiviOptTuple -> kiviOptTuple.map(kiviTuple -> kiviTuple.bimap(keyMapper,
                                                                                                                                       valueMapper)))
                                                                .flatMapLeft(kiviOptTuple -> kiviOptTuple.toEither(Tuple2.of(subject().unapply()
                                                                                                                                      ._1(),
                                                                                                                             Option.none())))));
    }

    default OrMatchClause2<K, V, KI, VI, KO, VO> then(Function<Tuple2<KI, VI>, Tuple2<KO, VO>> mapper) {
        return OrMatchClause2.of(() -> MatchResult2.of(subject().either()
                                                                .mapLeft(Tuple2::_2)
                                                                .mapLeft(kiviOptTuple -> kiviOptTuple.map(mapper))
                                                                .flatMapLeft(kiviOptTuple -> kiviOptTuple.toEither(Tuple2.of(subject().unapply()
                                                                                                                                      ._1(),
                                                                                                                             Option.none())))));
    }

    //    default OrMatchClause2<K, V, KI, VI, KO, VO> thenMapConcat(Function<Tuple2<KI, VI>, ? extends Iterable<Tuple2<KO, VO>>> mapper) {
    //            return OrMatchClause2.of(() -> MatchResult2.of(subject().either()
    //                                                                    .mapLeft(Tuple2::_2)
    //                                                                    .mapLeft(kiviOptTuple -> kiviOptTuple.map(mapper))
    //                                                                    .mapLeft(iterableOpt -> iterableOpt.map(ReactiveSeq::fromIterable).orElseGet(ReactiveSeq::<Tuple2<KO, VO>>empty))
    //                                                                    .flatMapLeft(kovoTupleSeq  -> Either.fromIterable(kovoTupleSeq)
    //                                                                                                        .toEither(Tuple2.of(subject().unapply()
    //                                                                                                                                     ._1(),
    //                                                                                                                            Option.<Tuple2<KI, VI>>none())))));
    //        }
    //
    //        default OrMatchClause2<K, V, KI, VI, KO, VO> thenMapConcat(BiFunction<KI, VI, ? extends Iterable<Tuple2<KO, VO>>> mapper) {
    //            return OrMatchClause2.of(() -> MatchResult2.of(subject().either()
    //                                                                    .mapLeft(Tuple2::_2)
    //                                                                    .mapLeft(kiviTupleOpt -> kiviTupleOpt.map(kiviTuple2 -> mapper.apply(kiviTuple2._1(),
    //                                                                                                                                         kiviTuple2._2())))
    //                                                                    .mapLeft(iterableOpt -> iterableOpt.map(ReactiveSeq::fromIterable).orElseGet(ReactiveSeq::<Tuple2<KO, VO>>empty))
    //                                                                    .flatMapLeft(kovoTupleSeq  -> Either.fromIterable(kovoTupleSeq)
    //                                                                                                      .toEither(Tuple2.of(subject().unapply()
    //                                                                                                                                   ._1(),
    //                                                                                                                          Option.<Tuple2<KI, VI>>none())))));
    //        }
}

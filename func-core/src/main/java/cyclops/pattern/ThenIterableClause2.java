package cyclops.pattern;


import cyclops.container.control.eager.option.Option;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.reactive.ReactiveSeq;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author smccarron
 */
public interface ThenIterableClause2<K, V, KI, VI> extends Clause<Tuple2<Tuple2<K, V>, Option<Tuple2<KI, Iterable<VI>>>>> {

    static <K, V, KI, VI> ThenIterableClause2<K, V, KI, VI> of(Supplier<Tuple2<Tuple2<K, V>, Option<Tuple2<KI, Iterable<VI>>>>> valueSupplier) {
        return new ThenIterableClause2<K, V, KI, VI>() {
            @Override
            public Tuple2<Tuple2<K, V>, Option<Tuple2<KI, Iterable<VI>>>> get() {
                return valueSupplier.get();
            }
        };
    }

    default <KO, VO> OrMatchClause2<K, V, KI, VI, KO, VO> then(Function<Tuple2<KI, ReactiveSeq<VI>>, Tuple2<KO, VO>> mapper) {
        return OrMatchClause2.of(() -> MatchResult2.of(subject().map2(kiviTupleOpt -> kiviTupleOpt.map(kiIterableTuple2 -> kiIterableTuple2.map2(ReactiveSeq::fromIterable))
                                                                                                  .map(mapper))
                                                                .fold((kvTuple2, kovoTuple2) -> kovoTuple2.toEither(Tuple2.of(kvTuple2,
                                                                                                                              Option.none())))));
    }

    default <KO, VO> OrMatchClause2<K, V, KI, VI, KO, VO> then(BiFunction<KI, ReactiveSeq<VI>, Tuple2<KO, VO>> mapper) {
        return OrMatchClause2.of(() -> MatchResult2.of(subject().map2(kiviTupleOpt -> kiviTupleOpt.map(kiviTuple2 -> mapper.apply(kiviTuple2._1(),
                                                                                                                                  ReactiveSeq.fromIterable(kiviTuple2._2()))))
                                                                .fold((kvTuple2, kovoTuple2) -> kovoTuple2.toEither(Tuple2.of(kvTuple2,
                                                                                                                              Option.none())))));
    }

    default <KO, VO> OrMatchClause2<K, V, KI, VI, KO, VO> then(Function<KI, KO> keyMapper,
                                                               Function<ReactiveSeq<VI>, VO> valueMapper) {
        return OrMatchClause2.of(() -> MatchResult2.of(subject().map2(kiviTupleOpt -> kiviTupleOpt.map(kiviTuple2 -> kiviTuple2.map1(keyMapper)
                                                                                                                               .map2(valueMapper.compose(ReactiveSeq::fromIterable))))
                                                                .fold((kvTuple2, kovoTuple2) -> kovoTuple2.toEither(Tuple2.of(kvTuple2,
                                                                                                                              Option.none())))));
    }

}

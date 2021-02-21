package io.github.anticipasean.ent.pattern.pair;

import cyclops.container.control.Either;
import cyclops.container.control.option.Option;
import cyclops.container.foldable.Deconstructable.Deconstructable3;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.container.immutable.tuple.Tuple3;


public interface MatchResult2<K, V, KI, VI, KO, VO> extends
                                                    Deconstructable3<Tuple2<K, V>, Option<Tuple2<KI, VI>>, Tuple2<KO, VO>> {


    static <K, V, KI, VI, KO, VO> MatchResult2<K, V, KI, VI, KO, VO> of(Either<Tuple2<Tuple2<K, V>, Option<Tuple2<KI, VI>>>, Tuple2<KO, VO>> inputValuesOrResultOutputEither) {
        return new MatchResult2<K, V, KI, VI, KO, VO>() {
            @Override
            public Either<Tuple2<Tuple2<K, V>, Option<Tuple2<KI, VI>>>, Tuple2<KO, VO>> either() {
                return inputValuesOrResultOutputEither;
            }

            @Override
            public String toString() {
                final StringBuilder sb = new StringBuilder("MatchResult2{");
                sb.append(either());
                sb.append('}');
                return sb.toString();
            }
        };
    }

    Either<Tuple2<Tuple2<K, V>, Option<Tuple2<KI, VI>>>, Tuple2<KO, VO>> either();

    @Override
    default Tuple3<Tuple2<K, V>, Option<Tuple2<KI, VI>>, Tuple2<KO, VO>> unapply() {
        return Tuple3.of(either().leftOrElse(null)
                                 ._1(),
                         either().leftOrElse(null)
                                 ._2(),
                         either().orElse(null));
    }

}

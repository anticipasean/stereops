package cyclops.pattern;

import cyclops.container.immutable.tuple.Tuple2;
import cyclops.function.enhanced.Function1;
import cyclops.function.enhanced.Function2;
import cyclops.pattern.Matcher.Matcher2;


/**
 * @author smccarron
 */
public interface Pattern2<K, V, KO, VO> extends Function1<Matcher2<K, V>, Tuple2<KO, VO>> {

    static <K, V, KO, VO> Function1<Tuple2<K, V>, Tuple2<KO, VO>> asMapper(Function1<Matcher2<K, V>, Tuple2<KO, VO>> pattern2) {
        return pattern2.compose(Matcher::of);
    }

    static <K, V, KO, VO> Function2<K, V, Tuple2<KO, VO>> asBiMapper(Function1<Matcher2<K, V>, Tuple2<KO, VO>> pattern2) {
        return (k, v) -> pattern2.apply(Matcher.of(k,
                                                   v));
    }

    @Override
    Tuple2<KO, VO> apply(Matcher2<K, V> matcher);
}

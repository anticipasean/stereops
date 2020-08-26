package cyclops.pattern;


import cyclops.function.enhanced.Function1;
import cyclops.pattern.Matcher.Matcher1;


/**
 * @author smccarron
 */
public interface Pattern1<V, O> extends Function1<Matcher1<V>, O> {

    static <V, O> Function1<V, O> asMapper(Function1<Matcher1<V>, O> pattern1) {
        return pattern1.compose(Matcher::of);
    }

    @Override
    O apply(Matcher1<V> matcher);
}

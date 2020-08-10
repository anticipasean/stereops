package cyclops.companion;

import cyclops.stream.async.EagerFutureStreamFunctions;
import cyclops.stream.async.SimpleReactStream;
import cyclops.function.combiner.Semigroup;
import cyclops.futurestream.FutureStream;

/**
 * A static class with a large number of SemigroupK  or Combiners.
 * <p>
 * A semigroup is an Object that can be used to combine objects of the same type.
 * <p>
 * Using raw Semigroups with container types
 * <pre>
 *     {@code
 *       Semigroup<Maybe<Integer>> m = Semigroups.combineZippables(Semigroups.intMax);
 *       Semigroup<ReactiveSeq<Integer>> m = Semigroups.combineZippables(Semigroups.intSum);
 *     }
 * </pre>
 *
 * @author johnmcclean
 */
public interface FutureStreamSemigroups {


    static <T> Semigroup<SimpleReactStream<T>> firstOfSimpleReact() {
        return (a, b) -> EagerFutureStreamFunctions.firstOf(a,
                                                            b);
    }

    /**
     * @return Combination of two LazyFutureStreams Streams b is appended to a
     */
    static <T> Semigroup<FutureStream<T>> combineFutureStream() {
        return (a, b) -> a.appendStream(b);
    }


}

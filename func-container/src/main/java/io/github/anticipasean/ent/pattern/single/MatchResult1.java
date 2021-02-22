package io.github.anticipasean.ent.pattern.single;

import cyclops.container.foldable.Deconstructable.Deconstructable3;
import cyclops.container.control.eager.either.Either;
import cyclops.container.control.eager.option.Option;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.container.immutable.tuple.Tuple3;

public interface MatchResult1<V, I, O> extends Deconstructable3<V, I, O> {

    static <V, I, O> MatchResult1<V, I, O> of(Either<Tuple2<V, Option<I>>, O> valueInputTupleOrResultObject) {
        return new MatchResult1<V, I, O>() {
            @Override
            public Either<Tuple2<V, Option<I>>, O> either() {
                return valueInputTupleOrResultObject;
            }
        };
    }

    Either<Tuple2<V, Option<I>>, O> either();

    @Override
    default Tuple3<V, I, O> unapply() {
        return Tuple3.of(either().leftOrElse(null)
                                 ._1(),
                         either().leftOrElse(null)
                                 ._2()
                                 .orElse(null),
                         either().orElse(null));
    }
}

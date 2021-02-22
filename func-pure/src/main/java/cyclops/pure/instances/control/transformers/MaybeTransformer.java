package cyclops.pure.instances.control.transformers;

import cyclops.function.higherkinded.DataWitness.option;
import cyclops.function.higherkinded.Higher;
import cyclops.container.control.lazy.maybe.Maybe;
import cyclops.pure.container.functional.Nested;
import cyclops.pure.transformers.Transformer;
import cyclops.pure.transformers.TransformerFactory;
import cyclops.pure.typeclasses.monad.Monad;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MaybeTransformer<W1, T> implements Transformer<W1, option, T> {

    private final Nested<W1, option, T> nested;
    private final Monad<W1> monad1;

    private final static <W1> TransformerFactory<W1, option> factory() {
        return MaybeTransformer::maybeT;
    }

    public static <W1, T> MaybeTransformer<W1, T> maybeT(Nested<W1, option, T> nested) {
        return new MaybeTransformer<W1, T>(nested,
                                           nested.def1.monad());
    }

    @Override
    public <R> Nested<W1, option, R> flatMap(Function<? super T, ? extends Nested<W1, option, R>> fn) {
        Higher<W1, Higher<option, R>> r = monad1.flatMap(m -> Maybe.narrowK(m)
                                                                   .fold(t -> fn.apply(t).nested,
                                                                         () -> monad1.unit(Maybe.nothing())),
                                                         nested.nested);

        return Nested.of(r,
                         nested.def1,
                         nested.def2);


    }

    @Override
    public <R> Nested<W1, option, R> flatMapK(Function<? super T, ? extends Higher<W1, Higher<option, R>>> fn) {
        return flatMap(fn.andThen(x -> Nested.of(x,
                                                 nested.def1,
                                                 nested.def2)));
    }


}

package cyclops.instances.control.transformers;


import cyclops.function.higherkinded.DataWitness.tryType;
import cyclops.function.higherkinded.Higher;
import cyclops.container.control.Try;
import cyclops.function.higherkinded.Nested;
import cyclops.transformers.Transformer;
import cyclops.transformers.TransformerFactory;
import cyclops.typeclasses.monad.Monad;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TryTransformer<W1, X extends Throwable, T> implements Transformer<W1, Higher<tryType, X>, T> {

    private final Nested<W1, Higher<tryType, X>, T> nested;
    private final Monad<W1> monad1;

    private final static <W1, X extends Throwable, T> TransformerFactory<W1, Higher<tryType, X>> factory() {
        return TryTransformer::tryT;
    }

    public static <W1, X extends Throwable, T> TryTransformer<W1, X, T> tryT(Nested<W1, Higher<tryType, X>, T> nested) {
        return new TryTransformer<>(nested,
                                    nested.def1.monad());
    }


    @Override
    public <R1> Nested<W1, Higher<tryType, X>, R1> flatMap(Function<? super T, ? extends Nested<W1, Higher<tryType, X>, R1>> fn) {
        Higher<W1, Higher<Higher<tryType, X>, R1>> res = monad1.flatMap(m -> Try.narrowK(m)
                                                                                .fold(r -> fn.apply(r).nested,
                                                                                      l -> monad1.unit(Try.failure(l))),
                                                                        nested.nested);

        return Nested.of(res,
                         nested.def1,
                         nested.def2);
    }

    @Override
    public <R> Nested<W1, Higher<tryType, X>, R> flatMapK(Function<? super T, ? extends Higher<W1, Higher<Higher<tryType, X>, R>>> fn) {
        return flatMap(fn.andThen(x -> Nested.of(x,
                                                 nested.def1,
                                                 nested.def2)));
    }


}

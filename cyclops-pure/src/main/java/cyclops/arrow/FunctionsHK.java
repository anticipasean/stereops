package cyclops.arrow;

import cyclops.function.higherkinded.Higher;
import cyclops.function.enhanced.Function1;
import cyclops.typeclasses.functor.Functor;
import cyclops.typeclasses.monad.Monad;
import java.util.function.Function;

public interface FunctionsHK {

    static <W1, W2, T, R> Function1<Higher<W1, T>, Higher<W2, R>> liftNT(Function<? super T, ? extends R> fn,
                                                                         Function<? super Higher<W1, T>, ? extends Higher<W2, T>> hktTransform,
                                                                         Functor<W2> functor) {
        return (T1) -> functor.map(fn,
                                   hktTransform.apply(T1));
    }

    static <T, CRE> Function1<? super T, ? extends Higher<CRE, T>> arrow(Monad<CRE> monad) {
        return t -> monad.unit(t);
    }
}

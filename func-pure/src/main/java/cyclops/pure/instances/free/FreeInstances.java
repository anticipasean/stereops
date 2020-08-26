package cyclops.pure.instances.free;

import cyclops.function.higherkinded.DataWitness.free;
import cyclops.function.higherkinded.Higher;
import cyclops.pure.free.Free;
import cyclops.pure.typeclasses.Pure;
import cyclops.pure.typeclasses.functor.Functor;
import cyclops.pure.typeclasses.monad.Applicative;
import cyclops.pure.typeclasses.monad.Monad;
import java.util.function.Function;
import lombok.AllArgsConstructor;

public class FreeInstances {

    public static <F> Pure<Higher<free, F>> pure(Pure<F> pure,
                                                 Functor<F> functor) {
        return new FreeMonad<F>(pure,
                                functor);
    }

    public static <F> Functor<Higher<free, F>> functor() {
        return new Functor<Higher<free, F>>() {
            @Override
            public <T, R> Higher<Higher<free, F>, R> map(Function<? super T, ? extends R> fn,
                                                         Higher<Higher<free, F>, T> ds) {
                return Free.narrowK(ds)
                           .map(fn);
            }
        };
    }

    public static <F> Applicative<Higher<free, F>> applicative(Pure<F> pure,
                                                               Functor<F> functor) {
        return new FreeMonad<F>(pure,
                                functor);
    }

    public static <F> FreeMonad<F> monad(Pure<F> pure,
                                         Functor<F> functor) {
        return new FreeMonad<F>(pure,
                                functor);
    }

    @AllArgsConstructor
    public static class FreeMonad<F> implements Monad<Higher<free, F>> {

        private final Pure<F> pure;
        private final Functor<F> functor;

        @Override
        public <T, R> Higher<Higher<free, F>, R> flatMap(Function<? super T, ? extends Higher<Higher<free, F>, R>> fn,
                                                         Higher<Higher<free, F>, T> ds) {
            return Free.narrowK(ds)
                       .flatMap(f -> Free.narrowK(fn.apply(f)));
        }

        @Override
        public <T, R> Higher<Higher<free, F>, R> ap(Higher<Higher<free, F>, ? extends Function<T, R>> fn,
                                                    Higher<Higher<free, F>, T> apply) {
            Free<F, ? extends Function<T, R>> f = Free.narrowK(fn);
            Free<F, T> a = Free.narrowK(apply);
            return f.flatMap(x -> a.map(t -> x.apply(t)));
        }

        @Override
        public <T> Higher<Higher<free, F>, T> unit(T value) {
            return Free.liftF(pure.unit(value),
                              functor);
        }

        @Override
        public <T, R> Higher<Higher<free, F>, R> map(Function<? super T, ? extends R> fn,
                                                     Higher<Higher<free, F>, T> ds) {
            return FreeInstances.<F>functor().map(fn,
                                                  ds);
        }

    }
}

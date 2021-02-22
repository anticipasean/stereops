package cyclops.pure.instances.control;

import static cyclops.container.control.lazy.either.LazyEither4.narrowK;

import cyclops.function.higherkinded.DataWitness.lazyEither4;
import cyclops.function.higherkinded.Higher;
import cyclops.pure.arrow.Cokleisli;
import cyclops.pure.arrow.Kleisli;
import cyclops.pure.arrow.MonoidK;
import cyclops.container.control.eager.either.Either;
import cyclops.container.control.lazy.either.LazyEither4;
import cyclops.container.control.eager.option.Option;
import cyclops.function.combiner.Monoid;
import cyclops.pure.container.functional.Active;
import cyclops.pure.container.functional.Nested;
import cyclops.pure.typeclasses.InstanceDefinitions;
import cyclops.pure.typeclasses.Pure;
import cyclops.pure.typeclasses.comonad.Comonad;
import cyclops.pure.typeclasses.foldable.Foldable;
import cyclops.pure.typeclasses.foldable.Unfoldable;
import cyclops.pure.typeclasses.functor.Functor;
import cyclops.pure.typeclasses.monad.Applicative;
import cyclops.pure.typeclasses.monad.Monad;
import cyclops.pure.typeclasses.monad.MonadPlus;
import cyclops.pure.typeclasses.monad.MonadRec;
import cyclops.pure.typeclasses.monad.MonadZero;
import cyclops.pure.typeclasses.monad.Traverse;
import java.util.function.Function;

public class LazyEither4Instances {

    final static LazyEither4Typeclasses INSTANCE = new LazyEither4Typeclasses<>();

    public static <LT1, LT2, LT3, RT> Active<Higher<Higher<Higher<lazyEither4, LT1>, LT2>, LT3>, RT> allTypeclasses(LazyEither4<LT1, LT2, LT3, RT> l4) {
        return Active.of(l4,
                         LazyEither4Instances.definitions());
    }

    public static <W2, LT1, LT2, LT3, RT, R> Nested<Higher<Higher<Higher<lazyEither4, LT1>, LT2>, LT3>, W2, R> mapM(LazyEither4<LT1, LT2, LT3, RT> l4,
                                                                                                                    Function<? super RT, ? extends Higher<W2, R>> fn,
                                                                                                                    InstanceDefinitions<W2> defs) {
        return Nested.of(l4.map(fn),
                         LazyEither4Instances.definitions(),
                         defs);
    }

    public static <LT1, LT2, LT3, T> Kleisli<Higher<Higher<Higher<lazyEither4, LT1>, LT2>, LT3>, LazyEither4<LT1, LT2, LT3, T>, T> kindKleisli() {
        return Kleisli.of(LazyEither4Instances.monad(),
                          LazyEither4::widen);
    }

    public static <LT1, LT2, LT3, T> Cokleisli<Higher<Higher<Higher<lazyEither4, LT1>, LT2>, LT3>, T, LazyEither4<LT1, LT2, LT3, T>> kindCokleisli() {
        return Cokleisli.of(LazyEither4::narrowK);
    }

    public static <L1, L2, L3> InstanceDefinitions<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> definitions() {
        return new InstanceDefinitions<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>>() {


            @Override
            public <T, R> Functor<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> functor() {
                return LazyEither4Instances.functor();
            }

            @Override
            public <T> Pure<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> unit() {
                return LazyEither4Instances.unit();
            }

            @Override
            public <T, R> Applicative<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> applicative() {
                return LazyEither4Instances.applicative();
            }

            @Override
            public <T, R> Monad<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> monad() {
                return LazyEither4Instances.monad();
            }

            @Override
            public <T, R> Option<MonadZero<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>>> monadZero() {
                return Option.none();
            }

            @Override
            public <T> Option<MonadPlus<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>>> monadPlus() {
                return Option.none();
            }

            @Override
            public <T> MonadRec<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> monadRec() {
                return LazyEither4Instances.monadRec();
            }

            @Override
            public <T> Option<MonadPlus<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>>> monadPlus(MonoidK<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> m) {
                return Option.none();
            }

            @Override
            public <C2, T> Traverse<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> traverse() {
                return LazyEither4Instances.traverse();
            }

            @Override
            public <T> Foldable<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> foldable() {
                return LazyEither4Instances.foldable();
            }

            @Override
            public <T> Option<Comonad<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>>> comonad() {
                return Option.none();
            }

            @Override
            public <T> Option<Unfoldable<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>>> unfoldable() {
                return Option.none();
            }
        };

    }

    public static <L1, L2, L3> Functor<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> functor() {
        return INSTANCE;
    }

    public static <L1, L2, L3> Pure<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> unit() {
        return INSTANCE;

    }

    public static <L1, L2, L3> Applicative<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> applicative() {
        return INSTANCE;
    }

    public static <L1, L2, L3> Monad<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> monad() {
        return INSTANCE;
    }

    public static <L1, L2, L3, T, R> MonadRec<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> monadRec() {
        return INSTANCE;
    }

    public static <L1, L2, L3> Traverse<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> traverse() {
        return INSTANCE;
    }

    public static <L1, L2, L3> Foldable<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> foldable() {
        return INSTANCE;
    }


    public static class LazyEither4Typeclasses<L1, L2, L3> implements Monad<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>>,
                                                                      MonadRec<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>>,
                                                                      Traverse<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>>,
                                                                      Foldable<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>> {

        public LazyEither4Typeclasses() {

        }

        @Override
        public <T, R> R foldMap(Monoid<R> mb,
                                Function<? super T, ? extends R> fn,
                                Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, T> nestedA) {
            return foldLeft(mb,
                            narrowK(nestedA).map(fn));
        }

        @Override
        public <T> T foldRight(Monoid<T> monoid,
                               Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, T> ds) {
            return narrowK(ds).fold(monoid);
        }

        @Override
        public <T> T foldLeft(Monoid<T> monoid,
                              Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, T> ds) {
            return narrowK(ds).fold(monoid);
        }

        @Override
        public <T, R> Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, R> flatMap(Function<? super T, ? extends Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, R>> fn,
                                                                                         Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, T> ds) {
            return narrowK(ds).flatMap(fn.andThen(m -> narrowK(m)));
        }

        @Override
        public <C2, T, R> Higher<C2, Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, R>> traverseA(Applicative<C2> applicative,
                                                                                                           Function<? super T, ? extends Higher<C2, R>> fn,
                                                                                                           Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, T> ds) {
            LazyEither4<L1, L2, L3, T> maybe = narrowK(ds);
            return maybe.fold(left -> applicative.unit(LazyEither4.left1(left)),
                              middle1 -> applicative.unit(LazyEither4.left2(middle1)),
                              middle2 -> applicative.unit(LazyEither4.left3(middle2)),
                              right -> applicative.map(m -> LazyEither4.right(m),
                                                       fn.apply(right)));
        }

        @Override
        public <C2, T> Higher<C2, Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, T>> sequenceA(Applicative<C2> applicative,
                                                                                                        Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, Higher<C2, T>> ds) {
            return null;
        }

        @Override
        public <T, R> Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, R> ap(Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, ? extends Function<T, R>> fn,
                                                                                    Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, T> apply) {
            return narrowK(fn).flatMap(x -> narrowK(apply).map(x));
        }

        @Override
        public <T> Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, T> unit(T value) {
            return LazyEither4.right(value);
        }

        @Override
        public <T, R> Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, R> map(Function<? super T, ? extends R> fn,
                                                                                     Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, T> ds) {
            return narrowK(ds).map(fn);
        }

        @Override
        public <T, R> Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, R> tailRec(T initial,
                                                                                         Function<? super T, ? extends Higher<Higher<Higher<Higher<lazyEither4, L1>, L2>, L3>, ? extends Either<T, R>>> fn) {
            return narrowK(fn.apply(initial)).flatMap(eval -> eval.fold(s -> narrowK(tailRec(s,
                                                                                             fn)),
                                                                        p -> LazyEither4.right(p)));
        }
    }


}

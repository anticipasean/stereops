package cyclops.pure.instances.reactive.collections.immutable;

import static cyclops.reactive.collection.function.higherkinded.ReactiveWitness.persistentSetX;
import static cyclops.reactive.collection.container.immutable.PersistentSetX.narrowK;

import cyclops.function.higherkinded.Higher;
import cyclops.pure.arrow.Cokleisli;
import cyclops.pure.arrow.Kleisli;
import cyclops.pure.arrow.MonoidK;
import cyclops.pure.arrow.MonoidKs;
import cyclops.container.control.eager.either.Either;
import cyclops.container.control.lazy.maybe.Maybe;
import cyclops.container.control.eager.option.Option;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.function.combiner.Monoid;
import cyclops.pure.container.functional.Active;
import cyclops.pure.container.functional.Coproduct;
import cyclops.pure.container.functional.Nested;
import cyclops.pure.container.functional.Product;
import cyclops.reactive.collection.container.immutable.PersistentSetX;
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
import cyclops.pure.typeclasses.monad.TraverseByTraverse;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PersistentSetXInstances {

    private final static PersistentSetXTypeClasses INSTANCE = new PersistentSetXTypeClasses();

    public static <T> Kleisli<persistentSetX, PersistentSetX<T>, T> kindKleisli() {
        return Kleisli.of(PersistentSetXInstances.monad(),
                          PersistentSetX::widen);
    }

    public static <T> Cokleisli<persistentSetX, T, PersistentSetX<T>> kindCokleisli() {
        return Cokleisli.of(PersistentSetX::narrowK);
    }

    public static <W1, T> Nested<persistentSetX, W1, T> nested(PersistentSetX<Higher<W1, T>> nested,
                                                               InstanceDefinitions<W1> def2) {
        return Nested.of(nested,
                         PersistentSetXInstances.definitions(),
                         def2);
    }

    public static <W1, T> Product<persistentSetX, W1, T> product(PersistentSetX<T> s,
                                                                 Active<W1, T> active) {
        return Product.of(allTypeclasses(s),
                          active);
    }

    public static <W1, T> Coproduct<W1, persistentSetX, T> coproduct(PersistentSetX<T> s,
                                                                     InstanceDefinitions<W1> def2) {
        return Coproduct.right(s,
                               def2,
                               PersistentSetXInstances.definitions());
    }

    public static <T> Active<persistentSetX, T> allTypeclasses(PersistentSetX<T> s) {
        return Active.of(s,
                         PersistentSetXInstances.definitions());
    }

    public static <W2, R, T> Nested<persistentSetX, W2, R> mapM(PersistentSetX<T> s,
                                                                Function<? super T, ? extends Higher<W2, R>> fn,
                                                                InstanceDefinitions<W2> defs) {
        return Nested.of(s.map(fn),
                         PersistentSetXInstances.definitions(),
                         defs);
    }

    public static InstanceDefinitions<persistentSetX> definitions() {
        return new InstanceDefinitions<persistentSetX>() {
            @Override
            public <T, R> Functor<persistentSetX> functor() {
                return PersistentSetXInstances.functor();
            }

            @Override
            public <T> Pure<persistentSetX> unit() {
                return PersistentSetXInstances.unit();
            }

            @Override
            public <T, R> Applicative<persistentSetX> applicative() {
                return PersistentSetXInstances.zippingApplicative();
            }

            @Override
            public <T, R> Monad<persistentSetX> monad() {
                return PersistentSetXInstances.monad();
            }

            @Override
            public <T, R> Option<MonadZero<persistentSetX>> monadZero() {
                return Option.some(PersistentSetXInstances.monadZero());
            }

            @Override
            public <T> Option<MonadPlus<persistentSetX>> monadPlus() {
                return Option.some(PersistentSetXInstances.monadPlus());
            }

            @Override
            public <T> MonadRec<persistentSetX> monadRec() {
                return PersistentSetXInstances.monadRec();
            }

            @Override
            public <T> Option<MonadPlus<persistentSetX>> monadPlus(MonoidK<persistentSetX> m) {
                return Option.some(PersistentSetXInstances.monadPlus(m));
            }

            @Override
            public <C2, T> Traverse<persistentSetX> traverse() {
                return PersistentSetXInstances.traverse();
            }

            @Override
            public <T> Foldable<persistentSetX> foldable() {
                return PersistentSetXInstances.foldable();
            }

            @Override
            public <T> Option<Comonad<persistentSetX>> comonad() {
                return Maybe.nothing();
            }

            @Override
            public <T> Option<Unfoldable<persistentSetX>> unfoldable() {
                return Option.some(PersistentSetXInstances.unfoldable());
            }
        };

    }

    public static Pure<persistentSetX> unit() {
        return INSTANCE;
    }

    public static Unfoldable<persistentSetX> unfoldable() {

        return INSTANCE;
    }

    public static MonadPlus<persistentSetX> monadPlus(MonoidK<persistentSetX> m) {

        return INSTANCE.withMonoidK(m);
    }

    public static <T, R> Applicative<persistentSetX> zippingApplicative() {
        return INSTANCE;
    }

    public static <T, R> Functor<persistentSetX> functor() {
        return INSTANCE;
    }

    public static <T, R> Monad<persistentSetX> monad() {
        return INSTANCE;
    }

    public static <T, R> MonadZero<persistentSetX> monadZero() {

        return INSTANCE;
    }

    public static <T> MonadPlus<persistentSetX> monadPlus() {

        return INSTANCE;
    }

    public static <T, R> MonadRec<persistentSetX> monadRec() {

        return INSTANCE;
    }

    public static <C2, T> Traverse<persistentSetX> traverse() {
        return INSTANCE;
    }

    public static <T, R> Foldable<persistentSetX> foldable() {
        return INSTANCE;
    }

    @AllArgsConstructor
    @lombok.With
    public static class PersistentSetXTypeClasses implements MonadPlus<persistentSetX>, MonadRec<persistentSetX>,
                                                             TraverseByTraverse<persistentSetX>, Foldable<persistentSetX>,
                                                             Unfoldable<persistentSetX> {

        private final MonoidK<persistentSetX> monoidK;

        public PersistentSetXTypeClasses() {
            monoidK = MonoidKs.persistentSetXConcat();
        }

        @Override
        public <T> Higher<persistentSetX, T> filter(Predicate<? super T> predicate,
                                                    Higher<persistentSetX, T> ds) {
            return narrowK(ds).filter(predicate);
        }

        @Override
        public <T, R> Higher<persistentSetX, Tuple2<T, R>> zip(Higher<persistentSetX, T> fa,
                                                               Higher<persistentSetX, R> fb) {
            return narrowK(fa).zip(narrowK(fb));
        }

        @Override
        public <T1, T2, R> Higher<persistentSetX, R> zip(Higher<persistentSetX, T1> fa,
                                                         Higher<persistentSetX, T2> fb,
                                                         BiFunction<? super T1, ? super T2, ? extends R> f) {
            return narrowK(fa).zip(narrowK(fb),
                                   f);
        }

        @Override
        public <T> MonoidK<persistentSetX> monoid() {
            return monoidK;
        }

        @Override
        public <T, R> Higher<persistentSetX, R> flatMap(Function<? super T, ? extends Higher<persistentSetX, R>> fn,
                                                        Higher<persistentSetX, T> ds) {
            return narrowK(ds).concatMap(i -> narrowK(fn.apply(i)));
        }

        @Override
        public <T, R> Higher<persistentSetX, R> ap(Higher<persistentSetX, ? extends Function<T, R>> fn,
                                                   Higher<persistentSetX, T> apply) {
            return narrowK(apply).zip(narrowK(fn),
                                      (a, b) -> b.apply(a));
        }

        @Override
        public <T> Higher<persistentSetX, T> unit(T value) {
            return PersistentSetX.of(value);
        }

        @Override
        public <T, R> Higher<persistentSetX, R> map(Function<? super T, ? extends R> fn,
                                                    Higher<persistentSetX, T> ds) {
            return narrowK(ds).map(fn);
        }


        @Override
        public <T, R> Higher<persistentSetX, R> tailRec(T initial,
                                                        Function<? super T, ? extends Higher<persistentSetX, ? extends Either<T, R>>> fn) {
            return PersistentSetX.tailRec(initial,
                                          i -> narrowK(fn.apply(i)));
        }

        @Override
        public <C2, T, R> Higher<C2, Higher<persistentSetX, R>> traverseA(Applicative<C2> ap,
                                                                          Function<? super T, ? extends Higher<C2, R>> fn,
                                                                          Higher<persistentSetX, T> ds) {
            PersistentSetX<T> v = narrowK(ds);
            return v.foldLeft(ap.unit(PersistentSetX.empty()),
                              (a, b) -> ap.zip(fn.apply(b),
                                                                                      a,
                                                                                      (sn, vec) -> narrowK(vec).plus(sn)));


        }

        @Override
        public <T, R> R foldMap(Monoid<R> mb,
                                Function<? super T, ? extends R> fn,
                                Higher<persistentSetX, T> ds) {
            PersistentSetX<T> x = narrowK(ds);
            return x.foldLeft(mb.zero(),
                              (a, b) -> mb.apply(a,
                                                 fn.apply(b)));
        }

        @Override
        public <T, R> Higher<persistentSetX, Tuple2<T, Long>> zipWithIndex(Higher<persistentSetX, T> ds) {
            return narrowK(ds).zipWithIndex();
        }

        @Override
        public <T> T foldRight(Monoid<T> monoid,
                               Higher<persistentSetX, T> ds) {
            return narrowK(ds).foldRight(monoid);
        }


        @Override
        public <T> T foldLeft(Monoid<T> monoid,
                              Higher<persistentSetX, T> ds) {
            return narrowK(ds).foldLeft(monoid);
        }


        @Override
        public <R, T> Higher<persistentSetX, R> unfold(T b,
                                                       Function<? super T, Option<Tuple2<R, T>>> fn) {
            return PersistentSetX.unfold(b,
                                         fn);
        }


    }

}

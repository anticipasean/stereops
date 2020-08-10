package cyclops.pure.instances.data.tuple;

import static cyclops.container.immutable.tuple.Tuple1.narrowK;

import cyclops.function.higherkinded.DataWitness.tuple1;
import cyclops.function.higherkinded.Higher;
import cyclops.pure.arrow.Cokleisli;
import cyclops.pure.arrow.Kleisli;
import cyclops.pure.arrow.MonoidK;
import cyclops.container.control.Either;
import cyclops.pure.control.Identity;
import cyclops.container.control.Option;
import cyclops.container.immutable.tuple.Tuple1;
import cyclops.function.combiner.Monoid;
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
import java.util.function.Function;
import lombok.AllArgsConstructor;

public class Tuple1Instances {

    private final static Tuple1Typeclasses INSTANCE = new Tuple1Typeclasses();

    public static <T> Identity<T> toIdentity(Tuple1<T> t1) {
        return Identity.of(t1._1());
    }

    public static InstanceDefinitions<tuple1> definitions() {
        return new InstanceDefinitions<tuple1>() {
            @Override
            public <T, R> Functor<tuple1> functor() {
                return Tuple1Instances.functor();
            }

            @Override
            public <T> Pure<tuple1> unit() {
                return Tuple1Instances.unit();
            }

            @Override
            public <T, R> Applicative<tuple1> applicative() {
                return Tuple1Instances.applicative();
            }

            @Override
            public <T, R> Monad<tuple1> monad() {
                return Tuple1Instances.monad();
            }

            @Override
            public <T, R> Option<MonadZero<tuple1>> monadZero() {
                return Option.none();
            }

            @Override
            public <T> Option<MonadPlus<tuple1>> monadPlus() {
                return Option.none();
            }

            @Override
            public <T> MonadRec<tuple1> monadRec() {
                return Tuple1Instances.monadRec();
            }

            @Override
            public <T> Option<MonadPlus<tuple1>> monadPlus(MonoidK<tuple1> m) {
                return Option.none();
            }

            @Override
            public <C2, T> Traverse<tuple1> traverse() {
                return Tuple1Instances.traverse();
            }

            @Override
            public <T> Foldable<tuple1> foldable() {
                return Tuple1Instances.foldable();
            }

            @Override
            public <T> Option<Comonad<tuple1>> comonad() {
                return Option.some(Tuple1Instances.comonad());
            }

            @Override
            public <T> Option<Unfoldable<tuple1>> unfoldable() {
                return Option.none();
            }
        };
    }

    public static <T, R> Functor<tuple1> functor() {
        return INSTANCE;
    }

    public static <T> Pure<tuple1> unit() {
        return INSTANCE;
    }

    public static <T, R> Applicative<tuple1> applicative() {
        return INSTANCE;
    }

    public static <T, R> Monad<tuple1> monad() {
        return INSTANCE;
    }

    public static <T, R> Comonad<tuple1> comonad() {
        return INSTANCE;
    }

    public static <T, R> MonadRec<tuple1> monadRec() {

        return INSTANCE;
    }

    public static <C2, T> Traverse<tuple1> traverse() {
        return INSTANCE;
    }

    public static <T, R> Foldable<tuple1> foldable() {
        return INSTANCE;
    }

    public static <T> Kleisli<tuple1, Tuple1<T>, T> kindKleisli() {
        return Kleisli.of(Tuple1Instances.monad(),
                          Tuple1Instances::widen);
    }

    public static <T> Higher<tuple1, T> widen(Tuple1<T> narrow) {
        return narrow;
    }

    public static <T> Cokleisli<tuple1, T, Tuple1<T>> kindCokleisli() {
        return Cokleisli.of(Tuple1::narrowK);
    }

    @AllArgsConstructor
    public static class Tuple1Typeclasses implements Monad<tuple1>, MonadRec<tuple1>, TraverseByTraverse<tuple1>,
                                                     Foldable<tuple1>, Comonad<tuple1> {

        @Override
        public <T> T foldRight(Monoid<T> monoid,
                               Higher<tuple1, T> ds) {
            return monoid.apply(narrowK(ds)._1(),
                                monoid.zero());
        }


        @Override
        public <T> T foldLeft(Monoid<T> monoid,
                              Higher<tuple1, T> ds) {
            return monoid.apply(monoid.zero(),
                                narrowK(ds)._1());
        }


        @Override
        public <T, R> Higher<tuple1, R> flatMap(Function<? super T, ? extends Higher<tuple1, R>> fn,
                                                Higher<tuple1, T> ds) {
            return Tuple1.narrowK(ds)
                         .flatMap(t -> Tuple1.narrowK(fn.apply(t)));
        }

        @Override
        public <C2, T, R> Higher<C2, Higher<tuple1, R>> traverseA(Applicative<C2> applicative,
                                                                  Function<? super T, ? extends Higher<C2, R>> fn,
                                                                  Higher<tuple1, T> ds) {
            Tuple1<T> tuple1 = Tuple1.narrowK(ds);
            return applicative.map(Tuple1::of,
                                   fn.apply(tuple1._1()));
        }

        @Override
        public <T, R> R foldMap(Monoid<R> mb,
                                Function<? super T, ? extends R> fn,
                                Higher<tuple1, T> ds) {
            Tuple1<R> opt = Tuple1.narrowK(ds)
                                  .map(fn);
            return foldLeft(mb,
                            opt);
        }

        @Override
        public <T, R> Higher<tuple1, R> ap(Higher<tuple1, ? extends Function<T, R>> fn,
                                           Higher<tuple1, T> apply) {
            return Tuple1.narrowK(apply)
                         .zip(Tuple1.narrowK(fn),
                              (a, b) -> b.apply(a));
        }

        @Override
        public <T> Higher<tuple1, T> unit(T value) {
            return Tuple1.of(value);
        }

        @Override
        public <T, R> Higher<tuple1, R> map(Function<? super T, ? extends R> fn,
                                            Higher<tuple1, T> ds) {
            return Tuple1.narrowK(ds)
                         .map(fn);
        }

        @Override
        public <T, R> Higher<tuple1, R> tailRec(T initial,
                                                Function<? super T, ? extends Higher<tuple1, ? extends Either<T, R>>> fn) {
            return Tuple1.tailRec(initial,
                                  t -> Tuple1.narrowK(fn.apply(t)));
        }

        @Override
        public <T> Higher<tuple1, Higher<tuple1, T>> nest(Higher<tuple1, T> ds) {
            return Tuple1.of(ds);
        }

        @Override
        public <T, R> Higher<tuple1, R> coflatMap(Function<? super Higher<tuple1, T>, R> mapper,
                                                  Higher<tuple1, T> ds) {
            return Tuple1.of(mapper.apply(ds));
        }

        @Override
        public <T> T extract(Higher<tuple1, T> ds) {
            return Tuple1.narrowK(ds)
                         ._1();
        }
    }
}

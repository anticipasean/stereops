package cyclops.pure.instances.jdk;

import static cyclops.pure.kinds.CompletableFutureKind.narrowK;

import cyclops.function.higherkinded.DataWitness.completableFuture;
import cyclops.function.higherkinded.DataWitness.future;
import cyclops.function.higherkinded.Higher;
import cyclops.pure.arrow.MonoidK;
import cyclops.pure.arrow.MonoidKs;
import cyclops.container.control.eager.either.Either;
import cyclops.async.Future;
import cyclops.container.control.eager.option.Option;
import cyclops.function.combiner.Monoid;
import cyclops.pure.instances.control.FutureInstances;
import cyclops.pure.kinds.CompletableFutureKind;
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
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;

/**
 * Companion class for creating Type Class instances for working with CompletableFutures
 *
 * @author johnmcclean
 */
@UtilityClass
public class CompletableFutureInstances {

    private final CompletableFutureTypeclasses INSTANCE = new CompletableFutureTypeclasses();

    public static InstanceDefinitions<completableFuture> definitions() {
        return new InstanceDefinitions<completableFuture>() {
            @Override
            public <T, R> Functor<completableFuture> functor() {
                return CompletableFutureInstances.functor();
            }

            @Override
            public <T> Pure<completableFuture> unit() {
                return CompletableFutureInstances.unit();
            }

            @Override
            public <T, R> Applicative<completableFuture> applicative() {
                return CompletableFutureInstances.applicative();
            }

            @Override
            public <T, R> Monad<completableFuture> monad() {
                return CompletableFutureInstances.monad();
            }

            @Override
            public <T, R> Option<MonadZero<completableFuture>> monadZero() {
                return Option.some(CompletableFutureInstances.monadZero());
            }

            @Override
            public <T> Option<MonadPlus<completableFuture>> monadPlus() {
                return Option.some(CompletableFutureInstances.monadPlus());
            }

            @Override
            public <T> MonadRec<completableFuture> monadRec() {
                return CompletableFutureInstances.monadRec();
            }

            @Override
            public <T> Option<MonadPlus<completableFuture>> monadPlus(MonoidK<completableFuture> m) {
                return Option.some(CompletableFutureInstances.monadPlus(m));
            }

            @Override
            public <C2, T> Traverse<completableFuture> traverse() {
                return CompletableFutureInstances.traverse();
            }

            @Override
            public <T> Foldable<completableFuture> foldable() {
                return CompletableFutureInstances.foldable();
            }

            @Override
            public <T> Option<Comonad<completableFuture>> comonad() {
                return Option.none();
            }

            @Override
            public <T> Option<Unfoldable<completableFuture>> unfoldable() {
                return Option.none();
            }
        };
    }

    public static <T, R> Functor<completableFuture> functor() {
        return INSTANCE;
    }

    public static <T> Pure<completableFuture> unit() {
        return INSTANCE;
    }

    public static <T, R> Applicative<completableFuture> applicative() {
        return INSTANCE;
    }

    public static <T, R> Monad<completableFuture> monad() {
        return INSTANCE;
    }

    public static <T, R> MonadZero<completableFuture> monadZero() {
        return INSTANCE;
    }

    public static <T, R> MonadRec<completableFuture> monadRec() {
        return INSTANCE;
    }

    public static <T> MonadPlus<completableFuture> monadPlus() {
        return INSTANCE;
    }

    public static <T> MonadPlus<completableFuture> monadPlus(MonoidK<completableFuture> m) {
        return INSTANCE;
    }

    public static <L> Traverse<completableFuture> traverse() {
        return INSTANCE;
    }

    public static <L> Foldable<completableFuture> foldable() {
        return INSTANCE;
    }

    @AllArgsConstructor
    @lombok.With
    public static class CompletableFutureTypeclasses implements MonadPlus<completableFuture>, MonadRec<completableFuture>,
                                                                TraverseByTraverse<completableFuture>,
                                                                Foldable<completableFuture> {

        private final MonoidK<completableFuture> monoidK;

        public CompletableFutureTypeclasses() {
            monoidK = MonoidKs.firstCompleteCompletableFuture();
        }

        @Override
        public <T> T foldRight(Monoid<T> monoid,
                               Higher<completableFuture, T> ds) {
            return Future.of(narrowK(ds))
                         .fold(monoid);
        }


        @Override
        public <T> T foldLeft(Monoid<T> monoid,
                              Higher<completableFuture, T> ds) {
            return Future.of(narrowK(ds))
                         .fold(monoid);
        }


        @Override
        public <T, R> Higher<completableFuture, R> flatMap(Function<? super T, ? extends Higher<completableFuture, R>> fn,
                                                           Higher<completableFuture, T> ds) {
            return CompletableFutureKind.widen(CompletableFutureKind.narrow(ds)
                                                                    .thenCompose(fn.andThen(CompletableFutureKind::narrowK)));
        }

        @Override
        public <C2, T, R> Higher<C2, Higher<completableFuture, R>> traverseA(Applicative<C2> applicative,
                                                                             Function<? super T, ? extends Higher<C2, R>> fn,
                                                                             Higher<completableFuture, T> ds) {
            CompletableFuture<T> future = narrowK(ds);
            return applicative.map(CompletableFutureKind::completedFuture,
                                   fn.apply(future.join()));
        }

        @Override
        public <T, R> R foldMap(Monoid<R> mb,
                                Function<? super T, ? extends R> fn,
                                Higher<completableFuture, T> ds) {
            CompletableFuture<R> opt = narrowK(ds).thenApply(fn);
            return Future.of(opt)
                         .fold(mb);
        }

        @Override
        public <T, R> Higher<completableFuture, R> ap(Higher<completableFuture, ? extends Function<T, R>> fn,
                                                      Higher<completableFuture, T> apply) {

            return CompletableFutureKind.widen(narrowK(fn).thenCombine(narrowK(apply),
                                                                       (a, b) -> a.apply(b)));
        }

        @Override
        public <T> Higher<completableFuture, T> unit(T value) {
            return CompletableFutureKind.widen(CompletableFuture.completedFuture(value));
        }

        @Override
        public <T, R> Higher<completableFuture, R> map(Function<? super T, ? extends R> fn,
                                                       Higher<completableFuture, T> ds) {
            return CompletableFutureKind.widen(narrowK(ds).thenApply(fn));
        }

        @Override
        public <T, R> Higher<completableFuture, R> tailRec(T initial,
                                                           Function<? super T, ? extends Higher<completableFuture, ? extends Either<T, R>>> fn) {
            Higher<future, R> x = FutureInstances.monadRec()
                                                 .tailRec(initial,
                                                          fn.andThen(CompletableFutureKind::narrowK)
                                                            .andThen(Future::of));
            return CompletableFutureKind.narrowFuture(x);
        }


        @Override
        public <T> MonoidK<completableFuture> monoid() {
            return monoidK;
        }
    }

}

package cyclops.pure.instances.control;

import static cyclops.pure.control.Writer.narrowK;
import static cyclops.pure.control.Writer.widen;

import cyclops.function.higherkinded.DataWitness.writer;
import cyclops.function.higherkinded.Higher;
import cyclops.pure.arrow.Cokleisli;
import cyclops.pure.arrow.Kleisli;
import cyclops.pure.arrow.MonoidK;
import cyclops.container.control.eager.either.Either;
import cyclops.container.control.eager.option.Option;
import cyclops.pure.control.Writer;
import cyclops.function.combiner.Monoid;
import cyclops.pure.container.functional.Active;
import cyclops.pure.container.functional.Coproduct;
import cyclops.pure.container.functional.Nested;
import cyclops.pure.container.functional.Product;
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
import lombok.experimental.UtilityClass;


@UtilityClass
public class WriterInstances {

    public static <W, W1, T> Nested<Higher<writer, W>, W1, T> nested(Writer<W, Higher<W1, T>> nested,
                                                                     Monoid<W> monoid,
                                                                     InstanceDefinitions<W1> def2) {
        return Nested.of(nested,
                         WriterInstances.definitions(monoid),
                         def2);
    }

    public static <W1, W, T> Product<Higher<writer, W>, W1, T> product(Writer<W, T> w,
                                                                       Monoid<W> monoid,
                                                                       Active<W1, T> active) {
        return Product.of(allTypeclasses(w,
                                         monoid),
                          active);
    }

    public static <W1, W, T> Coproduct<W1, Higher<writer, W>, T> coproduct(Writer<W, T> w,
                                                                           Monoid<W> monoid,
                                                                           InstanceDefinitions<W1> def2) {
        return Coproduct.right(w,
                               def2,
                               WriterInstances.definitions(monoid));
    }

    public static <W, T> Kleisli<Higher<writer, W>, Writer<W, T>, T> kindKleisli(Monoid<W> m) {
        return Kleisli.of(WriterInstances.monad(m),
                          Writer::widen);
    }

    public static <W, T> Cokleisli<Higher<writer, W>, T, Writer<W, T>> kindCokleisli() {
        return Cokleisli.of(Writer::narrowK);
    }

    public static <W, T> Active<Higher<writer, W>, T> allTypeclasses(Writer<W, T> w,
                                                                     Monoid<W> monoid) {
        return Active.of(w,
                         WriterInstances.definitions(monoid));
    }

    public static <W2, R, W, T> Nested<Higher<writer, W>, W2, R> mapM(Writer<W, T> w,
                                                                      Monoid<W> monoid,
                                                                      Function<? super T, ? extends Higher<W2, R>> fn,
                                                                      InstanceDefinitions<W2> defs) {
        return Nested.of(w.map(fn),
                         WriterInstances.definitions(monoid),
                         defs);
    }

    public static <W> InstanceDefinitions<Higher<writer, W>> definitions(Monoid<W> monoid) {
        return new InstanceDefinitions<Higher<writer, W>>() {

            @Override
            public <T, R> Functor<Higher<writer, W>> functor() {
                return WriterInstances.functor();
            }

            @Override
            public <T> Pure<Higher<writer, W>> unit() {
                return WriterInstances.unit(monoid);
            }

            @Override
            public <T, R> Applicative<Higher<writer, W>> applicative() {
                return WriterInstances.applicative(monoid);
            }

            @Override
            public <T, R> Monad<Higher<writer, W>> monad() {
                return WriterInstances.monad(monoid);
            }

            @Override
            public <T, R> Option<MonadZero<Higher<writer, W>>> monadZero() {
                return Option.none();
            }

            @Override
            public <T> Option<MonadPlus<Higher<writer, W>>> monadPlus() {
                return Option.none();
            }

            @Override
            public <T> MonadRec<Higher<writer, W>> monadRec() {
                return WriterInstances.monadRec(monoid);
            }

            @Override
            public <T> Option<MonadPlus<Higher<writer, W>>> monadPlus(MonoidK<Higher<writer, W>> m) {
                return Option.none();
            }

            @Override
            public <C2, T> Traverse<Higher<writer, W>> traverse() {
                return WriterInstances.traverse(monoid);
            }

            @Override
            public <T> Foldable<Higher<writer, W>> foldable() {
                return WriterInstances.foldable();
            }

            @Override
            public <T> Option<Comonad<Higher<writer, W>>> comonad() {
                return Option.none();
            }

            @Override
            public <T> Option<Unfoldable<Higher<writer, W>>> unfoldable() {
                return Option.none();
            }
        };
    }

    public static <W> Functor<Higher<writer, W>> functor() {
        return new Functor<Higher<writer, W>>() {
            @Override
            public <T, R> Higher<Higher<writer, W>, R> map(Function<? super T, ? extends R> fn,
                                                           Higher<Higher<writer, W>, T> ds) {
                return narrowK(ds).map(fn);
            }
        };
    }

    public static <W> Pure<Higher<writer, W>> unit(Monoid<W> monoid) {
        return new WriterTypeclasses<>(monoid);
    }

    public static <W> Applicative<Higher<writer, W>> applicative(Monoid<W> monoid) {
        return new WriterTypeclasses<>(monoid);
    }

    public static <W> Monad<Higher<writer, W>> monad(Monoid<W> monoid) {
        return new WriterTypeclasses<>(monoid);
    }

    public static <W> Traverse<Higher<writer, W>> traverse(Monoid<W> monoid) {
        return new WriterTypeclasses<>(monoid);
    }

    public static <W> Foldable<Higher<writer, W>> foldable() {
        return new Foldable<Higher<writer, W>>() {


            @Override
            public <T> T foldRight(Monoid<T> monoid,
                                   Higher<Higher<writer, W>, T> ds) {
                return monoid.fold(narrowK(ds).getValue()
                                              ._1());

            }

            @Override
            public <T> T foldLeft(Monoid<T> monoid,
                                  Higher<Higher<writer, W>, T> ds) {
                return monoid.fold(narrowK(ds).getValue()
                                              ._1());
            }

            @Override
            public <T, R> R foldMap(Monoid<R> mb,
                                    Function<? super T, ? extends R> fn,
                                    Higher<Higher<writer, W>, T> nestedA) {
                return foldLeft(mb,
                                narrowK(nestedA).map(fn));
            }
        };
    }

    public static <W> MonadRec<Higher<writer, W>> monadRec(Monoid<W> monoid) {
        return new MonadRec<Higher<writer, W>>() {
            @Override
            public <T, R> Higher<Higher<writer, W>, R> tailRec(T initial,
                                                               Function<? super T, ? extends Higher<Higher<writer, W>, ? extends Either<T, R>>> fn) {
                Writer<W, ? extends Either<T, R>>[] next = new Writer[1];
                next[0] = Writer.writer(Either.left(initial),
                                        monoid);

                boolean cont = true;
                do {
                    cont = next[0].fold((p, __) -> p._1()
                                                    .fold(s -> {
                                                              next[0] = narrowK(fn.apply(s));
                                                              return true;
                                                          },
                                                          pr -> false));
                } while (cont);
                return next[0].map(x -> x.orElse(null));
            }
        };
    }

    @AllArgsConstructor
    public static class WriterTypeclasses<W> implements Monad<Higher<writer, W>>, TraverseByTraverse<Higher<writer, W>>,
                                                        MonadRec<Higher<writer, W>> {

        private final Monoid<W> monoid;

        @Override
        public <T, R> Higher<Higher<writer, W>, R> flatMap(Function<? super T, ? extends Higher<Higher<writer, W>, R>> fn,
                                                           Higher<Higher<writer, W>, T> ds) {
            return narrowK(ds).flatMap(fn.andThen(h -> narrowK(h)));
        }

        @Override
        public <T, R> Higher<Higher<writer, W>, R> tailRec(T initial,
                                                           Function<? super T, ? extends Higher<Higher<writer, W>, ? extends Either<T, R>>> fn) {
            Writer<W, ? extends Either<T, R>>[] next = new Writer[1];
            next[0] = Writer.writer(Either.left(initial),
                                    monoid);

            boolean cont = true;
            do {
                cont = next[0].fold((p, __) -> p._1()
                                                .fold(s -> {
                                                          next[0] = narrowK(fn.apply(s));
                                                          return true;
                                                      },
                                                      pr -> false));
            } while (cont);
            return next[0].map(x -> x.orElse(null));
        }

        @Override
        public <C2, T, R> Higher<C2, Higher<Higher<writer, W>, R>> traverseA(Applicative<C2> applicative,
                                                                             Function<? super T, ? extends Higher<C2, R>> fn,
                                                                             Higher<Higher<writer, W>, T> ds) {
            Writer<W, T> w = narrowK(ds);
            Higher<C2, R> r = w.fold((t, m) -> fn.apply(t._1()));
            Higher<C2, Higher<Higher<writer, W>, R>> x = applicative.map_(r,
                                                                          t -> widen(Writer.writer(t,
                                                                                                   monoid)));
            return x;
        }

        @Override
        public <T, R> Higher<Higher<writer, W>, R> ap(Higher<Higher<writer, W>, ? extends Function<T, R>> fn,
                                                      Higher<Higher<writer, W>, T> apply) {
            Writer<W, ? extends Function<T, R>> f = narrowK(fn);
            Writer<W, T> ap = narrowK(apply);
            return f.flatMap(fn1 -> ap.map(a -> fn1.apply(a)));
        }

        @Override
        public <T> Higher<Higher<writer, W>, T> unit(T value) {
            return Writer.writer(value,
                                 monoid);
        }

        @Override
        public <T, R> Higher<Higher<writer, W>, R> map(Function<? super T, ? extends R> fn,
                                                       Higher<Higher<writer, W>, T> ds) {
            return narrowK(ds).map(fn);
        }
    }


}

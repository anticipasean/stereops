package cyclops.container.control;

import cyclops.container.Value;
import cyclops.container.foldable.OrElseValue;
import cyclops.container.foldable.Sealed2;
import cyclops.container.immutable.impl.NonEmptyList;
import cyclops.container.immutable.impl.Seq;
import cyclops.container.transformable.Transformable;
import cyclops.function.combiner.Monoid;
import cyclops.function.combiner.Semigroup;
import cyclops.function.companion.Semigroups;
import cyclops.function.higherkinded.DataWitness.validated;
import cyclops.function.higherkinded.Higher;
import cyclops.reactive.ReactiveSeq;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;

public interface Validated<E, T> extends Sealed2<NonEmptyList<E>, T>, Transformable<T>, Iterable<T>,
                                         OrElseValue<T, Validated<E, T>>, Higher<validated, T>, Value<T>, Serializable {


    static <E, T> Validated<E, T> valid(T t) {
        return new Valid<>(Either.right(t));
    }

    static <E, T> Validated<E, T> invalid(E e) {
        return new Invalid<>(Either.left(NonEmptyList.of(e)));
    }

    static <E, T> Validated<E, T> invalid(NonEmptyList<E> nel) {
        return new Invalid<>(Either.left(nel));
    }

    static <T> Validated<Throwable, T> fromPublisher(Publisher<T> pub) {
        return new Async<>(LazyEither.fromPublisher(pub)
                                     .mapLeft(NonEmptyList::of));
    }

    <R> Validated<E, R> map(Function<? super T, ? extends R> f);

    <RE, R> Validated<RE, R> bimap(Function<? super E, ? extends RE> e,
                                   Function<? super T, ? extends R> f);

    boolean isValid();

    default boolean isInvalid() {
        return !isValid();
    }

    @Override
    default Validated<E, T> peek(final Consumer<? super T> c) {
        return map(input -> {
            c.accept(input);
            return input;
        });
    }

    default Validated<E, T> combine(Semigroup<T> st,
                                    Validated<E, T> b) {
        return fold(iv -> {
                        return b.fold(biv -> {
                                          return Validated.invalid(Semigroups.<E>nonEmptyListConcat().apply(iv,
                                                                                                            biv));
                                      },
                                      bv -> {
                                          return Validated.invalid(iv);
                                      });
                    },
                    v -> {
                        return b.fold(biv -> {
                                          return Validated.invalid(biv);
                                      },
                                      bv -> {
                                          return Validated.valid(st.apply(v,
                                                                          bv));
                                      });
                    });
    }

    default Validated<E, Seq<T>> sequence(Iterable<Validated<E, T>> seq) {

        return ReactiveSeq.fromIterable(seq)
                          .prepend(this)
                          .foldLeft(Validated.valid(Seq.empty()),
                                    (a, b) -> a.combine(Semigroups.<T>seqConcat(),
                                                        b.map(Seq::of)));
    }

    default <R> Validated<E, Seq<R>> traverse(Iterable<Validated<E, T>> seq,
                                              Function<? super T, ? extends R> fn) {
        return ReactiveSeq.fromIterable(seq)
                          .prepend(this)
                          .foldLeft(Validated.valid(Seq.empty()),
                                    (a, b) -> a.combine(Semigroups.<R>seqConcat(),
                                                        b.map(v -> Seq.of(fn.apply(v)))));
    }

    default NonEmptyList<E> orElseInvalid(E alt) {
        return fold(t -> t,
                    a -> NonEmptyList.of(alt));
    }

    default Validated<E, T> orElseUseAccumulating(Supplier<Validated<E, T>> alt) {
        return fold(e -> {
                        return alt.get()
                                  .fold(ee -> Validated.invalid(Semigroups.<E>nonEmptyListConcat().apply(e,
                                                                                                         ee)),
                                        it -> valid(it));
                    },
                    it -> valid(it));
    }

    @Override
    default Validated<E, T> orElseUse(Supplier<Validated<E, T>> alt) {
        return fold(e -> alt.get(),
                    it -> valid(it));
    }

    @Override
    default Validated<E, T> recoverWith(Supplier<? extends Validated<E, T>> supplier) {
        return fold(e -> supplier.get(),
                    it -> valid(it));
    }

    Either<NonEmptyList<E>, T> toEither();

    default <R> R foldInvalidLeft(R zero,
                                  BiFunction<R, ? super E, R> fold) {
        return toEither().mapLeft(l -> l.foldLeft(zero,
                                                  fold))
                         .swap()
                         .orElse(zero);
    }

    default E foldInvalidLeft(Monoid<E> reducer) {
        return toEither().mapLeft(l -> l.foldLeft(reducer.zero(),
                                                  reducer))
                         .swap()
                         .orElse(reducer.zero());
    }


    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    final class Async<E, T> implements Validated<E, T> {

        private final LazyEither<NonEmptyList<E>, T> either;


        @Override
        public <R> Validated<E, R> map(Function<? super T, ? extends R> f) {
            return new Async<>(either.map(f));
        }

        @Override
        public <RE, R> Validated<RE, R> bimap(Function<? super E, ? extends RE> e,
                                              Function<? super T, ? extends R> f) {
            return new Async<>(either.bimap(nel -> nel.map(e),
                                            f));
        }

        @Override
        public boolean isValid() {
            return either.isRight();
        }

        @Override
        public Either<NonEmptyList<E>, T> toEither() {
            return either;
        }


        @Override
        public <R> R fold(Function<? super NonEmptyList<E>, ? extends R> fn1,
                          Function<? super T, ? extends R> fn2) {
            return either.fold(fn1,
                               fn2);
        }

        @Override
        public <R> R fold(Function<? super T, ? extends R> present,
                          Supplier<? extends R> absent) {
            return either.fold(present,
                               absent);
        }

        public String toString() {
            return either.fold(nl -> Validated.invalid(nl)
                                              .toString(),
                               i -> Validated.valid(i)
                                             .toString());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null) {
                return false;
            }
            Validated<?, ?> async = (Validated<?, ?>) o;
            return Objects.equals(either,
                                  async.toEither());
        }

        @Override
        public int hashCode() {

            return Objects.hash(either);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    final class Valid<E, T> implements Validated<E, T> {

        private final Either<NonEmptyList<E>, T> either;


        @Override
        public <R> Validated<E, R> map(Function<? super T, ? extends R> f) {
            return new Valid<>(either.map(f));
        }


        @Override
        public <RE, R> Validated<RE, R> bimap(Function<? super E, ? extends RE> e,
                                              Function<? super T, ? extends R> f) {
            return new Valid<>(either.bimap(nel -> nel.map(e),
                                            f));
        }

        @Override
        public final boolean isValid() {
            return true;
        }

        @Override
        public Either<NonEmptyList<E>, T> toEither() {
            return either;
        }

        @Override
        public <R> R fold(Function<? super NonEmptyList<E>, ? extends R> fn1,
                          Function<? super T, ? extends R> fn2) {
            return either.fold(fn1,
                               fn2);
        }

        @Override
        public <R> R fold(Function<? super T, ? extends R> present,
                          Supplier<? extends R> absent) {
            return either.fold(present,
                               absent);
        }

        public String toString() {
            return "Valid[" + either.orElse(null) + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null) {
                return false;
            }
            Validated<?, ?> valid = (Validated<?, ?>) o;
            return Objects.equals(either,
                                  valid.toEither());
        }

        @Override
        public int hashCode() {

            return Objects.hash(either);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    final class Invalid<E, T> implements Validated<E, T> {

        private final Either<NonEmptyList<E>, T> either;


        @Override
        public <R> Validated<E, R> map(Function<? super T, ? extends R> f) {
            return new Invalid<>(either.map(f));
        }

        @Override
        public <RE, R> Validated<RE, R> bimap(Function<? super E, ? extends RE> e,
                                              Function<? super T, ? extends R> f) {
            return new Invalid<>(either.bimap(nel -> nel.map(e),
                                              f));
        }

        @Override
        public boolean isValid() {
            return false;
        }

        @Override
        public Either<NonEmptyList<E>, T> toEither() {
            return either;
        }

        @Override
        public <R> R fold(Function<? super NonEmptyList<E>, ? extends R> fn1,
                          Function<? super T, ? extends R> fn2) {
            return either.fold(fn1,
                               fn2);
        }

        @Override
        public <R> R fold(Function<? super T, ? extends R> present,
                          Supplier<? extends R> absent) {
            return either.fold(present,
                               absent);
        }

        public String toString() {
            String str = either.mapLeft(l -> l.join(","))
                               .swap()
                               .orElse("");
            return "Invalid[" + str + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null) {
                return false;
            }
            Validated<?, ?> invalid = (Validated<?, ?>) o;
            return Objects.equals(either,
                                  invalid.toEither());
        }

        @Override
        public int hashCode() {

            return Objects.hash(either);
        }
    }
}

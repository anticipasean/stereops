package cyclops.pure.control;

import cyclops.container.control.Either;
import cyclops.container.control.Maybe;
import cyclops.function.higherkinded.DataWitness.state;
import cyclops.function.higherkinded.DataWitness.supplier;
import cyclops.function.higherkinded.Higher;
import cyclops.function.higherkinded.Higher2;
import cyclops.container.control.Maybe.Nothing;
import cyclops.container.immutable.tuple.Tuple;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.pure.free.Free;
import cyclops.function.enhanced.Function1;
import cyclops.function.enhanced.Function3;
import cyclops.function.enhanced.Function4;
import cyclops.pure.kinds.SupplierKind;
import java.util.function.BiFunction;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class State<S, T> implements Higher2<state, S, T> {

    private final Function1<S, Free<supplier, Tuple2<S, T>>> runState;

    public static <S> State<S, S> get() {
        return state(s -> Tuple.tuple(s,
                                      s));
    }

    public static <S> State<S, Nothing> transition(Function<? super S, ? extends S> f) {
        return state(s -> Tuple.tuple(f.apply(s),
                                      (Nothing) Maybe.nothing()));
    }

    public static <S, T> State<S, T> transition(Function<? super S, ? extends S> f,
                                                T value) {
        return state(s -> Tuple.tuple(f.apply(s),
                                      value));
    }

    private static <S, T> State<S, T> suspended(Function1<? super S, Free<supplier, Tuple2<S, T>>> runF) {
        return new State<>(s -> SupplierKind.suspend(SupplierKind.λK(() -> runF.apply(s))));
    }

    public static <S, T> State<S, T> constant(T constant) {
        return state(s -> Tuple.tuple(s,
                                      constant));
    }

    public static <S, T, R> State<S, R> tailRec(T initial,
                                                Function<? super T, ? extends State<S, ? extends Either<T, R>>> fn) {
        return fn.apply(initial)
                 .flatMap(eval -> eval.fold(s -> narrowK(tailRec(s,
                                                                 fn)),
                                            p -> State.constant(p)));


    }

    public static <S, T> State<S, T> state(Function<? super S, ? extends Tuple2<S, T>> runF) {

        return new State<>(s -> Free.done(runF.apply(s)));
    }

    public static <S> State<S, Nothing> of(S s) {
        return state(__ -> Tuple.tuple(s,
                                       (Nothing) Maybe.nothing()));
    }

    public static <S> State<S, Nothing> put(S s) {
        return of(s);
    }

    public static <S, T> State<S, T> narrowK2(final Higher2<state, S, T> t) {
        return (State<S, T>) t;
    }

    public static <S, T> State<S, T> narrowK(final Higher<Higher<state, S>, T> t) {
        return (State) t;
    }

    public static <S, T> Higher<Higher<state, S>, T> widen(State<S, T> narrow) {
        return narrow;
    }

    public Tuple2<S, T> run(S s) {
        return SupplierKind.run(runState.apply(s));
    }

    public T eval(S s) {
        return SupplierKind.run(runState.apply(s))
                           ._2();
    }

    public <T2, R> State<S, R> combine(State<S, T2> combine,
                                       BiFunction<? super T, ? super T2, ? extends R> combiner) {
        return flatMap(a -> combine.map(b -> combiner.apply(a,
                                                            b)));
    }

    public <R> State<S, R> map(Function<? super T, ? extends R> mapper) {
        return mapState(t -> Tuple.tuple(t._1(),
                                         mapper.apply(t._2())));
    }

    public <R> State<S, R> mapState(Function<Tuple2<S, T>, Tuple2<S, R>> fn) {
        return suspended(s -> runState.apply(s)
                                      .map(t -> fn.apply(t)));
    }

    public <R> State<S, R> flatMap(Function<? super T, ? extends State<S, R>> f) {
        return suspended(s -> runState.apply(s)
                                      .flatMap(t -> Free.done(f.apply(t._2())
                                                               .run(t._1()))));
    }

    /*
  * Perform a For Comprehension over a State, accepting 3 generating function.
          * This results in a four level nested internal iteration over the provided States.
   *
           *  <pre>
   * {@code
   *
   *   import static com.oath.cyclops.reactor.States.forEach4;
   *
      forEach4(State.just(1),
              a-> State.just(a+1),
              (a,b) -> State.<Integer>just(a+b),
              a                  (a,b,c) -> State.<Integer>just(a+b+c),
              Tuple::tuple)
   *
   * }
   * </pre>
          *
          * @param value1 top level State
   * @param value2 Nested State
   * @param value3 Nested State
   * @param value4 Nested State
   * @param yieldingFunction Generates a result per combination
   * @return State with a combined value generated by the yielding function
   */
    public <R1, R2, R3, R4> State<S, R4> forEach4(Function<? super T, ? extends State<S, R1>> value2,
                                                  BiFunction<? super T, ? super R1, ? extends State<S, R2>> value3,
                                                  Function3<? super T, ? super R1, ? super R2, ? extends State<S, R3>> value4,
                                                  Function4<? super T, ? super R1, ? super R2, ? super R3, ? extends R4> yieldingFunction) {

        return this.flatMap(in -> {

            State<S, R1> a = value2.apply(in);
            return a.flatMap(ina -> {
                State<S, R2> b = value3.apply(in,
                                              ina);
                return b.flatMap(inb -> {

                    State<S, R3> c = value4.apply(in,
                                                  ina,
                                                  inb);

                    return c.map(in2 -> {

                        return yieldingFunction.apply(in,
                                                      ina,
                                                      inb,
                                                      in2);

                    });

                });


            });


        });

    }

    /**
     * Perform a For Comprehension over a State, accepting 2 generating function. This results in a three level nested internal
     * iteration over the provided States.
     *
     * <pre>
     * {@code
     *
     *   import static com.oath.cyclops.reactor.States.forEach3;
     *
     * forEach3(State.just(1),
     * a-> State.just(a+1),
     * (a,b) -> State.<Integer>just(a+b),
     * Tuple::tuple)
     *
     * }
     * </pre>
     *
     * @param value2           Nested State
     * @param value3           Nested State
     * @param yieldingFunction Generates a result per combination
     * @return State with a combined value generated by the yielding function
     */
    public <R1, R2, R4> State<S, R4> forEach3(Function<? super T, ? extends State<S, R1>> value2,
                                              BiFunction<? super T, ? super R1, ? extends State<S, R2>> value3,
                                              Function3<? super T, ? super R1, ? super R2, ? extends R4> yieldingFunction) {

        return this.flatMap(in -> {

            State<S, R1> a = value2.apply(in);
            return a.flatMap(ina -> {
                State<S, R2> b = value3.apply(in,
                                              ina);
                return b.map(in2 -> {
                    return yieldingFunction.apply(in,
                                                  ina,
                                                  in2);

                });


            });

        });

    }

    /**
     * Perform a For Comprehension over a State, accepting a generating function. This results in a two level nested internal
     * iteration over the provided States.
     *
     * <pre>
     * {@code
     *
     *   import static com.oath.cyclops.reactor.States.forEach;
     *
     * forEach(State.just(1),
     * a-> State.just(a+1),
     * Tuple::tuple)
     *
     * }
     * </pre>
     *
     * @param value2           Nested State
     * @param yieldingFunction Generates a result per combination
     * @return State with a combined value generated by the yielding function
     */
    public <R1, R4> State<S, R4> forEach2(Function<? super T, State<S, R1>> value2,
                                          BiFunction<? super T, ? super R1, ? extends R4> yieldingFunction) {

        return this.flatMap(in -> {

            State<S, R1> a = value2.apply(in);
            return a.map(in2 -> {
                return yieldingFunction.apply(in,
                                              in2);

            });

        });

    }
}




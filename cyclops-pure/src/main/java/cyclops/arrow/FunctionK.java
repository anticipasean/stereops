package cyclops.arrow;

import cyclops.function.higherkinded.DataWitness.option;
import cyclops.function.higherkinded.DataWitness.reactiveSeq;
import cyclops.function.higherkinded.DataWitness.seq;
import cyclops.function.higherkinded.Higher;
import cyclops.container.immutable.impl.Seq;
import cyclops.function.enhanced.Function1;
import cyclops.function.higherkinded.NaturalTransformation;
import cyclops.instances.control.MaybeInstances;
import cyclops.instances.reactive.PublisherInstances;
import cyclops.reactive.ReactiveSeq;
import cyclops.typeclasses.InstanceDefinitions;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class FunctionK<W1, W2, T> implements Function1<Higher<W1, T>, Higher<W2, T>> {

    Function1<Higher<W1, T>, Higher<W2, T>> fn1;
    InstanceDefinitions<W2> def2;

    public static <W1, W2, T> FunctionK<W1, W2, T> of(Function1<Higher<W1, T>, Higher<W2, T>> fn1,
                                                      InstanceDefinitions<W2> def2) {
        return new FunctionK<>(fn1,
                               def2);
    }

    public static <W1, W2, T> FunctionK<W1, W2, T> fromNaturalTransformation(NaturalTransformation<W1, W2> nt,
                                                                             InstanceDefinitions<W2> def2) {
        return FunctionK.of(in -> nt.apply(in),
                            def2);
    }

    public static <W1, T> FunctionK<W1, W1, T> identity(InstanceDefinitions<W1> defs) {
        return of(i -> i,
                  defs);
    }

    static <T> FunctionK<reactiveSeq, option, T> streamMaybe() {
        return of(i -> ReactiveSeq.narrowK(i)
                                  .headOption(),
                  MaybeInstances.definitions());
    }

    static <T> FunctionK<seq, reactiveSeq, T> listStream() {
        return of(i -> Seq.narrowK(i)
                          .stream(),
                  PublisherInstances.definitions());
    }

    static <T> FunctionK<seq, option, T> listMaybe() {
        return of(i -> Seq.narrowK(i)
                          .headOption(),
                  MaybeInstances.definitions());
    }

    @Override
    public Higher<W2, T> apply(Higher<W1, T> a) {
        return fn1.apply(a);
    }

    public InstanceDefinitions<W2> definitions() {
        return def2;
    }

    public NaturalTransformation<W1, W2> asNaturalTransformationUnsafe() {
        return new NaturalTransformation<W1, W2>() {
            @Override
            public <T2> Higher<W2, T2> apply(Higher<W1, T2> a) {
                return FunctionK.this.apply((Higher) a);
            }
        };
    }

}

package cyclops.container.immutable.impl;


import cyclops.container.foldable.Deconstructable.Deconstructable2;
import cyclops.container.foldable.SealedOr;
import cyclops.container.immutable.tuple.Tuple;
import cyclops.container.immutable.tuple.Tuple2;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;


//https://apocalisp.wordpress.com/2008/10/23/heterogeneous-lists-and-the-limits-of-the-java-type-system/
//inspired / influenced by Functional Java's HList
public interface HList<T1 extends HList<T1>> extends SealedOr<HList<T1>> {

    static <T, HL extends HList<HL>> HCons<T, HL> cons(final T value,
                                                       final HL l) {
        return new HCons<>(value,
                           l);
    }

    static <T> HCons<T, HNil> of(final T value) {
        return new HCons<>(value,
                           empty());
    }

    static HList<HNil> empty() {
        return HNil.Instance;
    }


    <TB> HCons<TB, T1> prepend(TB value);


    @EqualsAndHashCode
    class HCons<T1, T2 extends HList<T2>> implements Deconstructable2<T1, HList<T2>>, HList<HCons<T1, T2>> {

        @Include
        public final T1 head;
        @Include
        public final HList<T2> tail;

        private HCons(T1 head,
                      HList<T2> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public Tuple2<T1, HList<T2>> unapply() {
            return Tuple.tuple(head,
                               tail);
        }

        @Override
        public <R> R fold(Function<? super HList<HCons<T1, T2>>, ? extends R> fn1,
                          Supplier<? extends R> s) {
            return fn1.apply(this);
        }

        @Override
        public <TB> HCons<TB, HCons<T1, T2>> prepend(TB value) {
            return cons(value,
                        this);
        }
    }

    class HNil implements HList<HNil> {

        final static HNil Instance = new HNil();

        private HNil() {

        }

        @Override
        public <R> R fold(Function<? super HList<HNil>, ? extends R> fn1,
                          Supplier<? extends R> s) {
            return s.get();
        }

        @Override
        public <TB> HCons<TB, HNil> prepend(TB value) {
            return cons(value,
                        this);
        }
    }

}

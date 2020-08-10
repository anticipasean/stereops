package cyclops.container.foldable;

import cyclops.container.immutable.tuple.Tuple1;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.container.immutable.tuple.Tuple3;
import cyclops.container.immutable.tuple.Tuple4;
import cyclops.container.immutable.tuple.Tuple5;
import cyclops.function.enhanced.Function3;
import cyclops.function.enhanced.Function4;
import cyclops.function.enhanced.Function5;
import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface Deconstructable<T> {

    T unapply();


    interface Deconstructable1<T1> extends Deconstructable<Tuple1<T1>> {

        default <R> R fold(Function<? super T1, ? extends R> match) {

            return match.apply(unapply()._1());
        }
    }

    interface Deconstructable2<T1, T2> extends Deconstructable<Tuple2<T1, T2>> {

        default <R> R fold(BiFunction<? super T1, ? super T2, ? extends R> match) {
            Tuple2<T1, T2> t2 = unapply();
            return match.apply(t2._1(),
                               t2._2());
        }
    }

    interface Deconstructable3<T1, T2, T3> extends Deconstructable<Tuple3<T1, T2, T3>> {

        default <R> R fold(Function3<? super T1, ? super T2, ? super T3, ? extends R> match) {
            Tuple3<T1, T2, T3> t = unapply();
            return match.apply(t._1(),
                               t._2(),
                               t._3());
        }
    }

    interface Deconstructable4<T1, T2, T3, T4> extends Deconstructable<Tuple4<T1, T2, T3, T4>> {

        default <R> R fold(Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> match) {
            Tuple4<T1, T2, T3, T4> t = unapply();
            return match.apply(t._1(),
                               t._2(),
                               t._3(),
                               t._4());
        }

    }

    interface Deconstructable5<T1, T2, T3, T4, T5> extends Deconstructable<Tuple5<T1, T2, T3, T4, T5>> {

        default <R> R fold(Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> match) {
            Tuple5<T1, T2, T3, T4, T5> t = unapply();
            return match.apply(t._1(),
                               t._2(),
                               t._3(),
                               t._4(),
                               t._5());
        }
    }

}

package cyclops.matching;

import cyclops.container.control.Option;
import cyclops.container.foldable.Sealed2;
import cyclops.container.foldable.Sealed3;
import cyclops.container.foldable.Sealed4;
import cyclops.container.foldable.Sealed5;
import cyclops.container.foldable.SealedOr;
import cyclops.container.immutable.tuple.Tuple;
import cyclops.container.immutable.tuple.Tuple0;
import cyclops.matching.Case.Any;
import cyclops.reactive.ReactiveSeq;
import java.util.Optional;


public interface Matching {


    final class PatternMatching<T> implements Matching {

        private final T value;

        public PatternMatching(T value) {
            this.value = value;
        }

        @SafeVarargs
        public final <R> Option<R> with(Case<T, R>... cases) {
            return ReactiveSeq.of(cases)
                              .foldLeft(Case::or)
                              .flatMap(c -> c.test(value));
        }

        public <R> R with(Case<T, R> case1,
                          Any<T, R> any) {
            return case1.test(value)
                        .orElseGet(() -> any.apply(value));
        }

        public <R> R with(Case<T, R> case1,
                          Case<T, R> case2,
                          Any<T, R> any) {
            return case1.or(case2)
                        .test(value)
                        .orElseGet(() -> any.apply(value));
        }

        public <R> R with(Case<T, R> case1,
                          Case<T, R> case2,
                          Case<T, R> case3,
                          Any<T, R> any) {
            return with(case1,
                        case2,
                        case3).orElseGet(() -> any.apply(value));
        }

        public <R> R with(Case<T, R> case1,
                          Case<T, R> case2,
                          Case<T, R> case3,
                          Case<T, R> case4,
                          Any<T, R> any) {
            return with(case1,
                        case2,
                        case3,
                        case4).orElseGet(() -> any.apply(value));
        }

        public <R> R with(Case<T, R> case1,
                          Case<T, R> case2,
                          Case<T, R> case3,
                          Case<T, R> case4,
                          Case<T, R> case5,
                          Any<T, R> any) {
            return with(case1,
                        case2,
                        case3,
                        case4,
                        case5).orElseGet(() -> any.apply(value));
        }

        public <R> R with(Case<T, R> case1,
                          Case<T, R> case2,
                          Case<T, R> case3,
                          Case<T, R> case4,
                          Case<T, R> case5,
                          Case<T, R> case6,
                          Any<T, R> any) {
            return with(case1,
                        case2,
                        case3,
                        case4,
                        case5,
                        case6).orElseGet(() -> any.apply(value));
        }

        public <R> R with(Case<T, R> case1,
                          Case<T, R> case2,
                          Case<T, R> case3,
                          Case<T, R> case4,
                          Case<T, R> case5,
                          Case<T, R> case6,
                          Case<T, R> case7,
                          Any<T, R> any) {
            return with(case1,
                        case2,
                        case3,
                        case4,
                        case5,
                        case6,
                        case7).orElseGet(() -> any.apply(value));
        }

        public <R> R with(Case<T, R> case1,
                          Case<T, R> case2,
                          Case<T, R> case3,
                          Case<T, R> case4,
                          Case<T, R> case5,
                          Case<T, R> case6,
                          Case<T, R> case7,
                          Case<T, R> case8,
                          Any<T, R> any) {
            return with(case1,
                        case2,
                        case3,
                        case4,
                        case5,
                        case6,
                        case7,
                        case8).orElseGet(() -> any.apply(value));
        }

    }


    final class PatternMatching2<T1, T2> implements Matching {

        private final Sealed2<T1, T2> value;

        public PatternMatching2(Sealed2<T1, T2> value) {
            this.value = value;
        }

        public <R> R with(Case<T1, R> case1,
                          Case<T2, R> case2) {
            return value.fold(a -> case1.test(a),
                              b -> case2.test(b))
                        .orElse(null);
        }

    }


    final class PatternMatching3<T1, T2, T3> implements Matching {

        private final Sealed3<T1, T2, T3> value;

        public PatternMatching3(Sealed3<T1, T2, T3> value) {
            this.value = value;
        }

        public <R> R with(Case<T1, R> case1,
                          Case<T2, R> case2,
                          Case<T3, R> case3) {
            return value.fold(a -> case1.test(a),
                              b -> case2.test(b),
                              c -> case3.test(c))
                        .orElse(null);
        }

    }


    final class PatternMatching4<T1, T2, T3, T4> implements Matching {

        private final Sealed4<T1, T2, T3, T4> value;

        public PatternMatching4(Sealed4<T1, T2, T3, T4> value) {
            this.value = value;
        }

        public <R> R with(Case<T1, R> case1,
                          Case<T2, R> case2,
                          Case<T3, R> case3,
                          Case<T4, R> case4) {
            return value.fold(a -> case1.test(a),
                              b -> case2.test(b),
                              c -> case3.test(c),
                              d -> case4.test(d))
                        .orElse(null);
        }

    }


    final class PatternMatching5<T1, T2, T3, T4, T5> implements Matching {

        private final Sealed5<T1, T2, T3, T4, T5> value;

        public PatternMatching5(Sealed5<T1, T2, T3, T4, T5> value) {
            this.value = value;
        }

        public <R> R with(Case<T1, R> case1,
                          Case<T2, R> case2,
                          Case<T3, R> case3,
                          Case<T4, R> case4,
                          Case<T5, R> case5) {
            return value.fold(a -> case1.test(a),
                              b -> case2.test(b),
                              c -> case3.test(c),
                              d -> case4.test(d),
                              e -> case5.test(e))
                        .orElse(null);
        }

    }


    final class PatternMatchingOrNone<T1> implements Matching {

        private final SealedOr<T1> value;

        public PatternMatchingOrNone(SealedOr<T1> value) {
            this.value = value;
        }

        public <R> R with(Case<T1, R> case1,
                          Case<Tuple0, R> case2) {
            return value.fold(a -> case1.test(a),
                              () -> case2.test(Tuple.empty()))
                        .orElse(null);
        }

    }


    final class OptionalMatching<T> implements Matching {

        private final Optional<T> value;

        public OptionalMatching(Optional<T> value) {
            this.value = value;
        }

        public <R> R of(Case.CaseOptional<T, R> caseOptional) {
            return caseOptional.test(value)
                               .orElse(null);
        }

    }

}

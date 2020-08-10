package cyclops.kinds;

import cyclops.function.higherkinded.DataWitness.predicate;
import cyclops.function.higherkinded.Higher;
import java.util.function.Predicate;

public interface PredicateKind<T> extends Higher<predicate, T>, Predicate<T> {

    static <T> PredicateKind<T> narrow(Higher<predicate, T> ds) {
        return (PredicateKind<T>) ds;
    }
}

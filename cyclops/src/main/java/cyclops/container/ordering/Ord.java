package cyclops.container.ordering;

import static cyclops.container.ordering.Ord.Ordering.EQUAL;
import static cyclops.container.ordering.Ord.Ordering.LESS;
import static cyclops.container.ordering.Ord.Ordering.MORE;

import cyclops.function.higherkinded.Higher;
import java.util.Comparator;
import lombok.AllArgsConstructor;


public interface Ord<W, T> {

    static <W, T> Ord<W, T> ord(Comparator<Higher<W, T>> comp) {
        return new OrdByComparotor(comp);
    }

    Ordering compare(Higher<W, T> first,
                     Higher<W, T> second);

    enum Ordering {
        LESS,
        EQUAL,
        MORE
    }

    @AllArgsConstructor
    class OrdByComparotor<W, T> implements Ord<W, T> {

        private final Comparator<Higher<W, T>> comp;


        @Override
        public Ordering compare(Higher<W, T> first,
                                Higher<W, T> second) {
            int pos = comp.compare(first,
                                   second);
            if (pos < 0) {
                return LESS;
            }
            if (pos > 0) {
                return MORE;
            }
            return EQUAL;
        }
    }
}

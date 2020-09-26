package cyclops.pattern;

import cyclops.reactive.ReactiveSeq;
import java.util.Iterator;

/**
 * @author smccarron
 */
public interface TypeMatchingIterable<E> extends Iterable<E> {

    @SuppressWarnings("unchecked")
    static <E> Iterable<E> of(Iterator iteratorOfUnknownType,
                              Class<E> elementType) {
        return (Iterable<E>) ReactiveSeq.fromIterator(iteratorOfUnknownType)
                                        .ofType(elementType);
    }

}

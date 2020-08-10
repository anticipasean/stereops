package cyclops.reactive.collection.function.combiner;

import cyclops.function.combiner.Semigroup;
import cyclops.reactive.collection.container.fluent.FluentCollectionX;
import cyclops.reactive.collection.container.immutable.BagX;
import cyclops.reactive.collection.container.immutable.LinkedListX;
import cyclops.reactive.collection.container.immutable.OrderedSetX;
import cyclops.reactive.collection.container.immutable.PersistentQueueX;
import cyclops.reactive.collection.container.immutable.PersistentSetX;
import cyclops.reactive.collection.container.immutable.VectorX;
import cyclops.reactive.collection.container.mutable.DequeX;
import cyclops.reactive.collection.container.mutable.ListX;
import cyclops.reactive.collection.container.mutable.QueueX;
import cyclops.reactive.collection.container.mutable.SetX;
import cyclops.reactive.collection.container.mutable.SortedSetX;

/**
 * A static class with a large number of SemigroupK  or Combiners.
 * <p>
 * A semigroup is an Object that can be used to combine objects of the same type.
 * <p>
 * Using raw Semigroups with container types
 * <pre>
 *     {@code
 *       Semigroup<Maybe<Integer>> m = Semigroups.combineZippables(Semigroups.intMax);
 *       Semigroup<ReactiveSeq<Integer>> m = Semigroups.combineZippables(Semigroups.intSum);
 *     }
 * </pre>
 *
 * @author johnmcclean
 */
public interface ReactiveSemigroups {


    /**
     * To manage javac type inference first assign the semigroup
     * <pre>
     * {@code
     *
     *    Semigroup<ListX<Integer>> listX = Semigroups.collectionXConcat();
     *    Semigroup<SetX<Integer>> setX = Semigroups.collectionXConcat();
     *
     *
     *
     * }
     * </pre>
     *
     * @return A Semigroup that can combine any cyclops2-react extended Collection type
     */
    static <T, C extends FluentCollectionX<T>> Semigroup<C> collectionXConcat() {

        return (a, b) -> (C) a.plusAll(b);
    }


    /**
     * @return A combiner for ListX (concatenates two ListX into a single ListX)
     */
    static <T> Semigroup<ListX<T>> listXConcat() {
        return ReactiveSemigroups.collectionXConcat();
    }

    /**
     * @return A combiner for SetX (concatenates two SetX into a single SetX)
     */
    static <T> Semigroup<SetX<T>> setXConcat() {
        return ReactiveSemigroups.collectionXConcat();
    }

    /**
     * @return A combiner for SortedSetX (concatenates two SortedSetX into a single SortedSetX)
     */
    static <T> Semigroup<SortedSetX<T>> sortedSetXConcat() {
        return ReactiveSemigroups.collectionXConcat();
    }

    /**
     * @return A combiner for QueueX (concatenates two QueueX into a single QueueX)
     */
    static <T> Semigroup<QueueX<T>> queueXConcat() {
        return ReactiveSemigroups.collectionXConcat();
    }

    /**
     * @return A combiner for DequeX (concatenates two DequeX into a single DequeX)
     */
    static <T> Semigroup<DequeX<T>> dequeXConcat() {
        return ReactiveSemigroups.collectionXConcat();
    }

    /**
     * @return A combiner for LinkedListX (concatenates two LinkedListX into a single LinkedListX)
     */
    static <T> Semigroup<LinkedListX<T>> linkedListXConcat() {
        return ReactiveSemigroups.collectionXConcat();
    }

    /**
     * @return A combiner for VectorX (concatenates two VectorX into a single VectorX)
     */
    static <T> Semigroup<VectorX<T>> vectorXConcat() {
        return ReactiveSemigroups.collectionXConcat();
    }

    /**
     * @return A combiner for PersistentSetX (concatenates two PersistentSetX into a single PersistentSetX)
     */
    static <T> Semigroup<PersistentSetX<T>> persistentSetXConcat() {
        return ReactiveSemigroups.collectionXConcat();
    }

    /**
     * @return A combiner for OrderedSetX (concatenates two OrderedSetX into a single OrderedSetX)
     */
    static <T> Semigroup<OrderedSetX<T>> orderedSetXConcat() {
        return ReactiveSemigroups.collectionXConcat();
    }

    /**
     * @return A combiner for PersistentQueueX (concatenates two PersistentQueueX into a single PersistentQueueX)
     */
    static <T> Semigroup<PersistentQueueX<T>> persistentQueueXConcat() {
        return ReactiveSemigroups.collectionXConcat();
    }

    /**
     * @return A combiner for BagX (concatenates two BagX into a single BagX)
     */
    static <T> Semigroup<BagX<T>> bagXConcat() {
        return ReactiveSemigroups.collectionXConcat();
    }


}

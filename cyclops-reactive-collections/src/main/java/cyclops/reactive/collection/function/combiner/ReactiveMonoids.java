package cyclops.reactive.collection.function.combiner;

import cyclops.function.combiner.Monoid;
import cyclops.function.companion.Comparators;
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
 * A static class with a large number of Monoids  or Combiners with identity elements.
 * <p>
 * A Monoid is an Object that can be used to combine objects of the same type inconjunction with it's identity element which
 * leaves any element it is combined with unchanged.
 *
 * @author johnmcclean
 */
public interface ReactiveMonoids {

    /**
     * To manage javac type inference first assign the monoid
     * <pre>
     * {@code
     *
     *    Monoid<ListX<Integer>> listX = Monoid.of(identity,Semigroups.collectionXConcat(ListX.zero());
     *    Monoid<SetX<Integer>> setX = Monoid.of(identity,Semigroups.collectionXConcat(SetX.zero());
     *
     *
     *
     * }
     * </pre>
     *
     * @return A Monoid that can combine any cyclops2-react extended Collection type
     */
    static <T, C extends FluentCollectionX<T>> Monoid<C> collectionXConcat(C identity) {
        return Monoid.of(identity,
                         ReactiveSemigroups.collectionXConcat());
    }


    /**
     * @return A combiner for ListX (concatenates two ListX into a single ListX)
     */
    static <T> Monoid<ListX<T>> listXConcat() {
        return Monoid.of(ListX.empty(),
                         ReactiveSemigroups.collectionXConcat());
    }

    /**
     * @return A combiner for SetX (concatenates two SetX into a single SetX)
     */
    static <T> Monoid<SetX<T>> setXConcat() {
        return Monoid.of(SetX.empty(),
                         ReactiveSemigroups.collectionXConcat());
    }

    /**
     * @return A combiner for SortedSetX (concatenates two SortedSetX into a single SortedSetX)
     */
    static <T> Monoid<SortedSetX<T>> sortedSetXConcat() {
        return Monoid.of(SortedSetX.empty(),
                         ReactiveSemigroups.collectionXConcat());
    }

    /**
     * @return A combiner for QueueX (concatenates two QueueX into a single QueueX)
     */
    static <T> Monoid<QueueX<T>> queueXConcat() {
        return Monoid.of(QueueX.empty(),
                         ReactiveSemigroups.collectionXConcat());
    }

    /**
     * @return A combiner for DequeX (concatenates two DequeX into a single DequeX)
     */
    static <T> Monoid<DequeX<T>> dequeXConcat() {
        return Monoid.of(DequeX.empty(),
                         ReactiveSemigroups.collectionXConcat());
    }


    /**
     * @return A combiner for LinkedListX (concatenates two LinkedListX into a single LinkedListX)
     */
    static <T> Monoid<LinkedListX<T>> linkedListXConcat() {
        return Monoid.of(LinkedListX.empty(),
                         ReactiveSemigroups.linkedListXConcat());
    }

    /**
     * @return A combiner for VectorX (concatenates two VectorX into a single VectorX)
     */
    static <T> Monoid<VectorX<T>> vectorXConcat() {
        return Monoid.of(VectorX.empty(),
                         ReactiveSemigroups.collectionXConcat());
    }

    /**
     * @return A combiner for PersistentSetX (concatenates two PersistentSetX into a single PersistentSetX)
     */
    static <T> Monoid<PersistentSetX<T>> persistentSetXConcat() {
        return Monoid.of(PersistentSetX.empty(),
                         ReactiveSemigroups.collectionXConcat());
    }

    /**
     * @return A combiner for OrderedSetX (concatenates two OrderedSetX into a single OrderedSetX)
     */
    static <T> Monoid<OrderedSetX<T>> orderedSetXConcat() {
        return Monoid.of(OrderedSetX.empty(Comparators.naturalOrderIdentityComparator()),
                         ReactiveSemigroups.collectionXConcat());
    }

    /**
     * @return A combiner for PersistentQueueX (concatenates two PersistentQueueX into a single PersistentQueueX)
     */
    static <T> Monoid<PersistentQueueX<T>> persistentQueueXConcat() {
        return Monoid.of(PersistentQueueX.empty(),
                         ReactiveSemigroups.collectionXConcat());
    }

    /**
     * @return A combiner for BagX (concatenates two BagX into a single BagX)
     */
    static <T> Monoid<BagX<T>> bagXConcat() {
        return Monoid.of(BagX.empty(),
                         ReactiveSemigroups.collectionXConcat());
    }


}

package cyclops.reactive.collection.companion;

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
import cyclops.reactive.companion.Spouts;
import org.reactivestreams.Publisher;

public class ReactiveCollections {

    static <T> LinkedListX<T> linkedListX(Publisher<T> flowable) {
        return LinkedListX.linkedListX(Spouts.from(flowable))
                          .materialize();
    }

    static <T> VectorX<T> vectorX(Publisher<T> flowable) {
        return VectorX.vectorX(Spouts.from(flowable))
                      .materialize();
    }

    static <T> PersistentSetX<T> persistentSetX(Publisher<T> flowable) {
        return PersistentSetX.persistentSetX(Spouts.from(flowable))
                             .materialize();
    }

    static <T> OrderedSetX<T> orderedSetX(Publisher<T> flowable) {
        return OrderedSetX.orderedSetX(Spouts.from(flowable))
                          .materialize();
    }

    static <T> BagX<T> bagX(Publisher<T> flowable) {
        return BagX.bagX(Spouts.from(flowable))
                   .materialize();
    }

    static <T> PersistentQueueX<T> persistentQueueX(Publisher<T> flowable) {
        return PersistentQueueX.persistentQueueX(Spouts.from(flowable))
                               .materialize();
    }

    static <T> ListX<T> listX(Publisher<T> flowable) {
        return ListX.listX(Spouts.from(flowable))
                    .materialize();
    }

    static <T> DequeX<T> dequeX(Publisher<T> flowable) {
        return DequeX.dequeX(Spouts.from(flowable))
                     .materialize();
    }

    static <T> SetX<T> setX(Publisher<T> flowable) {
        return SetX.setX(Spouts.from(flowable))
                   .materialize();
    }

    static <T> SortedSetX<T> sortedSetX(Publisher<T> flowable) {
        return SortedSetX.sortedSetX(Spouts.from(flowable))
                         .materialize();
    }

    static <T> QueueX<T> queueX(Publisher<T> flowable) {
        return QueueX.queueX(Spouts.from(flowable))
                     .materialize();
    }
}

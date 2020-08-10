package cyclops.pure.reactive;

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
import io.reactivex.Observable;

/*
    Factory methods for asynchronously populating Reactive Collections via Observables
 */
@Deprecated
public interface ObservableCollections {

    static <T> LinkedListX<T> linkedListX(Observable<T> observable) {
        return LinkedListX.linkedListX(ObservableReactiveSeq.reactiveSeq(observable))
                          .materialize();
    }

    static <T> VectorX<T> vectorX(Observable<T> observable) {
        return VectorX.vectorX(ObservableReactiveSeq.reactiveSeq(observable))
                      .materialize();
    }

    static <T> PersistentSetX<T> persistentSetX(Observable<T> observable) {
        return PersistentSetX.persistentSetX(ObservableReactiveSeq.reactiveSeq(observable))
                             .materialize();
    }

    static <T> OrderedSetX<T> orderedSetX(Observable<T> observable) {
        return OrderedSetX.orderedSetX(ObservableReactiveSeq.reactiveSeq(observable))
                          .materialize();
    }

    static <T> BagX<T> bagX(Observable<T> observable) {
        return BagX.bagX(ObservableReactiveSeq.reactiveSeq(observable))
                   .materialize();
    }

    static <T> PersistentQueueX<T> persistentQueueX(Observable<T> observable) {
        return PersistentQueueX.persistentQueueX(ObservableReactiveSeq.reactiveSeq(observable))
                               .materialize();
    }

    static <T> ListX<T> listX(Observable<T> observable) {
        return ListX.listX(ObservableReactiveSeq.reactiveSeq(observable))
                    .materialize();
    }

    static <T> DequeX<T> dequeX(Observable<T> observable) {
        return DequeX.dequeX(ObservableReactiveSeq.reactiveSeq(observable))
                     .materialize();
    }

    static <T> SetX<T> setX(Observable<T> observable) {
        return SetX.setX(ObservableReactiveSeq.reactiveSeq(observable))
                   .materialize();
    }

    static <T> SortedSetX<T> sortedSetX(Observable<T> observable) {
        return SortedSetX.sortedSetX(ObservableReactiveSeq.reactiveSeq(observable))
                         .materialize();
    }

    static <T> QueueX<T> queueX(Observable<T> observable) {
        return QueueX.queueX(ObservableReactiveSeq.reactiveSeq(observable))
                     .materialize();
    }
}

package cyclops.rxjava2.io;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


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
import cyclops.rxjava2.adapter.ObservableCollections;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Before;
import org.junit.Test;

public class ObservableCollectionsTest {


    AtomicBoolean complete;
    Observable<Integer> async;

    @Before
    public void setup() {
        complete = new AtomicBoolean(false);
        async = Observable.interval(1,
                                    TimeUnit.SECONDS,
                                    Schedulers.io())
                          .take(2)
                          .map(i -> {
                              Thread.sleep(500);
                              return i.intValue();
                          })
                          .doOnComplete(() -> complete.set(true));
    }

    @Test
    public void listX() {

        System.out.println("Initializing!");
        ListX<Integer> asyncList = ObservableCollections.listX(async)
                                                        .map(i -> i + 1);

        boolean blocked = complete.get();
        System.out.println("Blocked? " + blocked);
        assertFalse(complete.get());

        int value = asyncList.get(0);

        System.out.println("First value is " + value);
        assertThat(value,
                   equalTo(1));

        System.out.println("Blocked? " + complete.get());
        assertTrue(complete.get());
    }

    @Test
    public void queueX() {

        System.out.println("Initializing!");
        QueueX<Integer> asyncList = ObservableCollections.queueX(async)
                                                         .map(i -> i + 1);

        boolean blocked = complete.get();
        System.out.println("Blocked? " + blocked);
        assertFalse(complete.get());

        int value = asyncList.firstValue(-1);

        System.out.println("First value is " + value);
        assertThat(value,
                   equalTo(1));

        System.out.println("Blocked? " + complete.get());
        assertTrue(complete.get());
    }

    @Test
    public void setX() {

        System.out.println("Initializing!");
        SetX<Integer> asyncList = ObservableCollections.setX(async)
                                                       .map(i -> i + 1);

        boolean blocked = complete.get();
        System.out.println("Blocked? " + blocked);
        assertFalse(complete.get());

        int value = asyncList.firstValue(-1);

        System.out.println("First value is " + value);
        assertThat(value,
                   equalTo(1));

        System.out.println("Blocked? " + complete.get());
        assertTrue(complete.get());
    }

    @Test
    public void sortedSetX() {

        System.out.println("Initializing!");
        SortedSetX<Integer> asyncList = ObservableCollections.sortedSetX(async)
                                                             .map(i -> i + 1);

        boolean blocked = complete.get();
        System.out.println("Blocked? " + blocked);
        assertFalse(complete.get());

        int value = asyncList.firstValue(-1);

        System.out.println("First value is " + value);
        assertThat(value,
                   equalTo(1));

        System.out.println("Blocked? " + complete.get());
        assertTrue(complete.get());
    }

    @Test
    public void dequeX() {

        System.out.println("Initializing!");
        DequeX<Integer> asyncList = ObservableCollections.dequeX(async)
                                                         .map(i -> i + 1);

        boolean blocked = complete.get();
        System.out.println("Blocked? " + blocked);
        assertFalse(complete.get());

        int value = asyncList.firstValue(-1);

        System.out.println("First value is " + value);
        assertThat(value,
                   equalTo(1));

        System.out.println("Blocked? " + complete.get());
        assertTrue(complete.get());
    }

    @Test
    public void linkedListX() {

        System.out.println("Initializing!");
        LinkedListX<Integer> asyncList = ObservableCollections.linkedListX(async)
                                                              .map(i -> i + 1);

        boolean blocked = complete.get();
        System.out.println("Blocked? " + blocked);
        assertFalse(complete.get());

        int value = asyncList.getOrElse(0,
                                        -1);

        System.out.println("First value is " + value);
        assertThat(value,
                   equalTo(1));

        System.out.println("Blocked? " + complete.get());
        assertTrue(complete.get());
    }

    @Test
    public void vectorX() {

        System.out.println("Initializing!");
        VectorX<Integer> asyncList = ObservableCollections.vectorX(async)
                                                          .map(i -> i + 1);

        boolean blocked = complete.get();
        System.out.println("Blocked? " + blocked);
        assertFalse(complete.get());

        int value = asyncList.getOrElse(0,
                                        -1);

        System.out.println("First value is " + value);
        assertThat(value,
                   equalTo(1));

        System.out.println("Blocked? " + complete.get());
        assertTrue(complete.get());
    }

    @Test
    public void persistentQueueX() {

        System.out.println("Initializing!");
        PersistentQueueX<Integer> asyncList = ObservableCollections.persistentQueueX(async)
                                                                   .map(i -> i + 1);

        boolean blocked = complete.get();
        System.out.println("Blocked? " + blocked);
        assertFalse(complete.get());

        int value = asyncList.firstValue(-1);

        System.out.println("First value is " + value);
        assertThat(value,
                   equalTo(1));

        System.out.println("Blocked? " + complete.get());
        assertTrue(complete.get());
    }

    @Test
    public void persistentSetX() {

        System.out.println("Initializing!");
        PersistentSetX<Integer> asyncList = ObservableCollections.persistentSetX(async)
                                                                 .map(i -> i + 1);

        boolean blocked = complete.get();
        System.out.println("Blocked? " + blocked);
        assertFalse(complete.get());

        int value = asyncList.firstValue(-1);

        System.out.println("First value is " + value);
        assertThat(value,
                   equalTo(1));

        System.out.println("Blocked? " + complete.get());
        assertTrue(complete.get());
    }

    @Test
    public void orderedSetX() {

        System.out.println("Initializing!");
        OrderedSetX<Integer> asyncList = ObservableCollections.orderedSetX(async)
                                                              .map(i -> i + 1);

        boolean blocked = complete.get();
        System.out.println("Blocked? " + blocked);
        assertFalse(complete.get());

        int value = asyncList.firstValue(-1);

        System.out.println("First value is " + value);
        assertThat(value,
                   equalTo(1));

        System.out.println("Blocked? " + complete.get());
        assertTrue(complete.get());
    }

    @Test
    public void bagX() {

        System.out.println("Initializing!");
        BagX<Integer> asyncList = ObservableCollections.bagX(async)
                                                       .map(i -> i + 1);

        boolean blocked = complete.get();
        System.out.println("Blocked? " + blocked);
        assertFalse(complete.get());

        int value = asyncList.firstValue(-1);

        System.out.println("First value is " + value);
        assertThat(value,
                   equalTo(1));

        System.out.println("Blocked? " + complete.get());
        assertTrue(complete.get());
    }
}

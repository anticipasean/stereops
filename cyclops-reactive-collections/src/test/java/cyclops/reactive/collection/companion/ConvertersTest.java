package cyclops.reactive.collection.companion;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import cyclops.reactive.collection.container.ReactiveConvertableSequence;
import cyclops.container.persistent.PersistentBag;
import cyclops.container.persistent.PersistentList;
import cyclops.container.persistent.PersistentQueue;
import cyclops.container.persistent.PersistentSet;
import cyclops.container.persistent.PersistentSortedSet;
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
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 * Created by johnmcclean on 17/05/2017.
 */
public class ConvertersTest {

    @Test
    public void convert() {

        LinkedList<Integer> list1 = ListX.of(1,
                                             2,
                                             3)
                                         .to(Converters::LinkedList);
        ArrayList<Integer> list2 = ListX.of(1,
                                            2,
                                            3)
                                        .to(Converters::ArrayList);

        assertThat(list1,
                   equalTo(list2));
        assertThat(list1,
                   equalTo(Arrays.asList(1,
                                         2,
                                         3)));

        PersistentList<Integer> pstack = LinkedListX.of(1,
                                                        2,
                                                        3)
                                                    .to(Converters::PStack);
        PersistentList<Integer> pvector = VectorX.of(1,
                                                     2,
                                                     3)
                                                 .to(Converters::PVector);
        PersistentSet<Integer> pset = PersistentSetX.of(1,
                                                        2,
                                                        3)
                                                    .to(Converters::PSet);
        PersistentSortedSet<Integer> pOrderedSet = OrderedSetX.of(1,
                                                                  2,
                                                                  3)
                                                              .to(Converters::POrderedSet);
        PersistentBag<Integer> pBag = BagX.of(1,
                                              2,
                                              3)
                                          .to(Converters::PBag);
        PersistentQueue<Integer> pQueue = PersistentQueueX.of(1,
                                                              2,
                                                              3)
                                                          .to(Converters::PQueue);

        HashSet<Integer> set = SetX.of(1,
                                       2,
                                       3)
                                   .to(Converters::HashSet);
        ArrayDeque<Integer> deque = DequeX.of(1,
                                              2,
                                              3)
                                          .to(Converters::ArrayDeque);
        ArrayBlockingQueue<Integer> queue = QueueX.of(1,
                                                      2,
                                                      3)
                                                  .to(Converters::ArrayBlockingQueue);
        TreeSet<Integer> tset = SortedSetX.of(1,
                                              2,
                                              3)
                                          .to(Converters::TreeSet);
        HashMap<Integer, Integer> map = MapXs.of(1,
                                                 2)
                                             .to(Converters::HashMap);

        assertThat(pstack,
                   equalTo(pvector));
        assertThat(pset,
                   equalTo(pOrderedSet));
        assertThat(pset,
                   equalTo(pBag.stream()
                               .to(ReactiveConvertableSequence::converter)
                               .persistentSetX()));
        assertThat(pQueue.stream()
                         .collect(Collectors.toList()),
                   equalTo(list1));

        assertThat(set,
                   equalTo(tset));
        assertThat(deque.stream()
                        .collect(Collectors.toList()),
                   equalTo(list1));
        assertThat(queue.stream()
                        .collect(Collectors.toList()),
                   equalTo(list1));

    }
}

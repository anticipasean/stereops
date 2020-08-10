package cyclops.reactive.companion;

import com.oath.cyclops.data.collections.extensions.CollectionX;
import cyclops.container.transformable.To;
import cyclops.container.persistent.PersistentBag;
import cyclops.container.persistent.PersistentList;
import cyclops.container.persistent.PersistentQueue;
import cyclops.container.persistent.PersistentSet;
import cyclops.container.persistent.PersistentSortedSet;
import cyclops.function.companion.Reducers;
import cyclops.reactive.collections.mutable.MapX;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class for holding conversion methods between types Use in conjunction with {@link To#to(Function)} for fluent conversions
 *
 * <pre>
 *     {@code
 *      LinkedList<Integer> list1 = Seq.of(1,2,3)
 *                                      .to(Converters::LinkedList);
 * ArrayList<Integer> list2 = Seq.of(1,2,3)
 * .to(Converters::ArrayList);
 *     }
 *
 * </pre>
 */
public interface Converters {

    static <K, V> HashMap<K, V> HashMap(MapX<K, V> vec) {
        return vec.unwrapIfInstance(HashMap.class,
                                    () -> vec.collect(Collectors.toMap(k -> k._1(),
                                                                       v -> v._2(),
                                                                       (a, b) -> a,
                                                                       () -> new HashMap<K, V>())));
    }

    static <K, V> LinkedHashMap<K, V> LinkedHashMap(MapX<K, V> vec) {
        return vec.unwrapIfInstance(LinkedHashMap.class,
                                    () -> vec.collect(Collectors.toMap(k -> k._1(),
                                                                       v -> v._2(),
                                                                       (a, b) -> a,
                                                                       () -> new LinkedHashMap<K, V>())));
    }

    static <K, V> ConcurrentHashMap<K, V> ConcurrentHashMap(MapX<K, V> vec) {
        return vec.unwrapIfInstance(ConcurrentHashMap.class,
                                    () -> vec.collect(Collectors.toMap(k -> k._1(),
                                                                       v -> v._2(),
                                                                       (a, b) -> a,
                                                                       () -> new ConcurrentHashMap<K, V>())));
    }

    static <K extends Enum<K>, V> EnumMap<K, V> EnumHashMap(MapX<K, V> vec) {
        return vec.unwrapIfInstance(EnumMap.class,
                                    () -> new EnumMap<K, V>((Map<K, V>) vec.unwrap()));
    }

    static <T> PersistentBag<T> PBag(CollectionX<T> vec) {
        return vec.unwrapIfInstance(PersistentBag.class,
                                    () -> Reducers.<T>toPersistentBag().foldMap(vec.stream()));
    }

    static <T> PersistentSortedSet<T> POrderedSet(CollectionX<T> vec) {
        return vec.unwrapIfInstance(PersistentList.class,
                                    () -> Reducers.<T>toPersistentSortedSet().foldMap(vec.stream()));
    }

    static <T> PersistentQueue<T> PQueue(CollectionX<T> vec) {
        return vec.unwrapIfInstance(PersistentList.class,
                                    () -> Reducers.<T>toPersistentQueue().foldMap(vec.stream()));
    }

    static <T> PersistentSet<T> PSet(CollectionX<T> vec) {
        return vec.unwrapIfInstance(PersistentList.class,
                                    () -> Reducers.<T>toPersistentSet().foldMap(vec.stream()));
    }

    static <T> PersistentList<T> PStack(CollectionX<T> vec) {
        return vec.unwrapIfInstance(PersistentList.class,
                                    () -> Reducers.<T>toPersistentList().foldMap(vec.stream()));
    }

    static <T> PersistentList<T> PVector(CollectionX<T> vec) {
        return vec.unwrapIfInstance(PersistentList.class,
                                    () -> Reducers.<T>toPersistentList().foldMap(vec.stream()));
    }

    static <T> LinkedList<T> LinkedList(CollectionX<T> vec) {

        return vec.unwrapIfInstance(LinkedList.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new LinkedList<T>())));
    }

    static <T> ArrayDeque<T> ArrayDeque(CollectionX<T> vec) {

        return vec.unwrapIfInstance(ArrayDeque.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new ArrayDeque<T>())));
    }

    static <T> ConcurrentLinkedDeque<T> ConcurrentLinkedDeque(CollectionX<T> vec) {

        return vec.unwrapIfInstance(ConcurrentLinkedDeque.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new ConcurrentLinkedDeque<T>())));
    }

    static <T> LinkedBlockingDeque<T> LinkedBlockingDeque(CollectionX<T> vec) {

        return vec.unwrapIfInstance(LinkedBlockingDeque.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new LinkedBlockingDeque<T>())));
    }

    static <T> ArrayList<T> ArrayList(CollectionX<T> vec) {

        return vec.unwrapIfInstance(ArrayList.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new ArrayList<T>())));
    }

    static <T> CopyOnWriteArrayList<T> CopyOnWriteArrayList(CollectionX<T> vec) {

        return vec.unwrapIfInstance(CopyOnWriteArrayList.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new CopyOnWriteArrayList<T>())));
    }

    static <T extends Delayed> DelayQueue<T> toDelayQueue(CollectionX<T> vec) {

        return vec.unwrapIfInstance(DelayQueue.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new DelayQueue<T>())));
    }

    static <T> PriorityBlockingQueue<T> PriorityBlockingQueue(CollectionX<T> vec) {

        return vec.unwrapIfInstance(PriorityBlockingQueue.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new PriorityBlockingQueue<T>())));
    }

    static <T> PriorityQueue<T> PriorityQueue(CollectionX<T> vec) {

        return vec.unwrapIfInstance(PriorityQueue.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new PriorityQueue<T>())));
    }

    static <T> LinkedTransferQueue<T> LinkedTransferQueue(CollectionX<T> vec) {

        return vec.unwrapIfInstance(LinkedTransferQueue.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new LinkedTransferQueue<T>())));
    }

    static <T> LinkedBlockingQueue<T> LinkedBlockingQueue(CollectionX<T> vec) {

        return vec.unwrapIfInstance(LinkedBlockingQueue.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new LinkedBlockingQueue<T>())));
    }

    static <T> ConcurrentLinkedQueue<T> ConcurrentLinkedQueue(CollectionX<T> vec) {

        return vec.unwrapIfInstance(ConcurrentLinkedQueue.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new ConcurrentLinkedQueue<T>())));
    }

    static <T> ArrayBlockingQueue<T> ArrayBlockingQueue(CollectionX<T> vec) {

        return vec.unwrapIfInstance(ArrayBlockingQueue.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new ArrayBlockingQueue<T>(vec.size()))));
    }

    static <T> HashSet<T> HashSet(CollectionX<T> vec) {
        return vec.unwrapIfInstance(HashSet.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new HashSet<T>())));
    }

    static <T> LinkedHashSet<T> LinkedHashSet(CollectionX<T> vec) {
        return vec.unwrapIfInstance(LinkedHashSet.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new LinkedHashSet<T>())));
    }

    static <T> TreeSet<T> TreeSet(CollectionX<T> vec) {
        return vec.unwrapIfInstance(TreeSet.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new TreeSet<T>())));
    }

    static <T> ConcurrentSkipListSet<T> ConcurrentSkipListSet(CollectionX<T> vec) {
        return vec.unwrapIfInstance(ConcurrentSkipListSet.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new ConcurrentSkipListSet<T>())));
    }

    static <T> CopyOnWriteArraySet<T> CopyOnWriteArraySet(CollectionX<T> vec) {
        return vec.unwrapIfInstance(CopyOnWriteArraySet.class,
                                    () -> vec.collect(Collectors.toCollection(() -> new CopyOnWriteArraySet<T>())));
    }

}

package cyclops.container.immutable.impl;


import cyclops.container.control.Option;
import cyclops.container.immutable.ImmutableMap;
import cyclops.container.immutable.tuple.Tuple;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.container.persistent.PersistentMap;
import cyclops.function.enhanced.Function3;
import cyclops.function.enhanced.Function4;
import cyclops.function.higherkinded.DataWitness.linkedHashMap;
import cyclops.function.higherkinded.Higher;
import cyclops.function.higherkinded.Higher2;
import cyclops.reactive.ReactiveSeq;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class LinkedMap<K, V> implements ImmutableMap<K, V>, Higher2<linkedHashMap, K, V> {

    private final ImmutableMap<K, V> map;
    private final Vector<Tuple2<K, V>> order;

    public static <K, V> LinkedMap<K, V> empty() {
        return new LinkedMap<>(HashMap.empty(),
                               Vector.empty());
    }

    public static <K, V> LinkedMap<K, V> of(K k,
                                            V v) {
        LinkedMap<K, V> res = empty();
        return res.put(k,
                       v);
    }

    public static <K, V> LinkedMap<K, V> of(K k1,
                                            V v1,
                                            K k2,
                                            V v2) {
        LinkedMap<K, V> res = empty();
        return res.put(k1,
                       v1)
                  .put(k2,
                       v2);
    }

    public static <K, V> LinkedMap<K, V> fromMap(java.util.Map<K, V> source) {
        LinkedMap<K, V> res = empty();
        for (Map.Entry<K, V> entry : source.entrySet()) {
            res = res.put(entry.getKey(),
                          entry.getValue());
        }
        return res;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <K, V> LinkedMap<K, V> fromMap(PersistentMap<K, V> map) {
        if (map instanceof LinkedMap) {
            return (LinkedMap) map;
        }
        LinkedMap<K, V> res = empty();
        for (Tuple2<K, V> next : map) {
            res = res.put(next._1(),
                          next._2());
        }
        return res;
    }

    public static <K, V> LinkedMap<K, V> fromStream(Stream<Tuple2<K, V>> stream) {
        return ReactiveSeq.fromStream(stream)
                          .foldLeft(empty(),
                                    (m, t2) -> m.put(t2._1(),
                                                     t2._2()));
    }

    @SuppressWarnings("unchecked")
    public static <K, V> LinkedMap<K, V> narrow(LinkedMap<? extends K, ? extends V> map) {
        return (LinkedMap<K, V>) map;
    }

    public static <K, V> LinkedMap<K, V> narrowK(Higher<Higher<linkedHashMap, K>, V> container) {
        return (LinkedMap<K, V>) container;
    }

    public static <K, V> LinkedMap<K, V> narrowK2(Higher2<linkedHashMap, K, V> container) {
        return (LinkedMap<K, V>) container;
    }

    public Option<V> get(K key) {
        return map.get(key);
    }

    @Override
    public V getOrElse(K key,
                       V alt) {
        return map.getOrElse(key,
                             alt);
    }

    @Override
    public V getOrElseGet(K key,
                          Supplier<? extends V> alt) {
        return map.getOrElseGet(key,
                                alt);
    }

    @Override
    public int size() {
        return order.size();
    }

    @Override
    public <K2, V2> DMap.Two<K, V, K2, V2> merge(ImmutableMap<K2, V2> one) {
        return DMap.two(this,
                        one);
    }

    @Override
    public <K2, V2, K3, V3> DMap.Three<K, V, K2, V2, K3, V3> merge(DMap.Two<K2, V2, K3, V3> two) {
        return DMap.three(this,
                          two.map1(),
                          two.map2());
    }

    @Override
    public ReactiveSeq<Tuple2<K, V>> stream() {
        return order.stream();
    }

    @Override
    public <R> LinkedMap<K, R> mapValues(Function<? super V, ? extends R> map) {
        return fromStream(stream().map(t -> t.map2(map)));
    }

    @Override
    public <R> LinkedMap<R, V> mapKeys(Function<? super K, ? extends R> map) {
        return fromStream(stream().map(t -> t.map1(map)));
    }

    @Override
    public <R1, R2> LinkedMap<R1, R2> bimap(BiFunction<? super K, ? super V, ? extends Tuple2<R1, R2>> map) {
        return fromStream(stream().map(t -> t.transform(map)));
    }

    @Override
    public <K2, V2> LinkedMap<K2, V2> flatMap(BiFunction<? super K, ? super V, ? extends ImmutableMap<K2, V2>> mapper) {
        return fromStream(stream().concatMap(t -> t.transform(mapper)));
    }

    @Override
    public <K2, V2> LinkedMap<K2, V2> concatMap(BiFunction<? super K, ? super V, ? extends Iterable<Tuple2<K2, V2>>> mapper) {
        return fromStream(stream().concatMap(t -> t.transform(mapper)));
    }

    @Override
    public LinkedMap<K, V> filter(Predicate<? super Tuple2<K, V>> predicate) {
        return fromStream(stream().filter(predicate));
    }

    @Override
    public LinkedMap<K, V> filterKeys(Predicate<? super K> predicate) {
        return fromStream(stream().filter(t -> predicate.test(t._1())));
    }

    @Override
    public LinkedMap<K, V> filterValues(Predicate<? super V> predicate) {
        return fromStream(stream().filter(t -> predicate.test(t._2())));
    }

    @Override
    public <R> LinkedMap<K, R> map(Function<? super V, ? extends R> fn) {
        return fromStream(stream().map(t -> Tuple.tuple(t._1(),
                                                        fn.apply(t._2()))));
    }

    @Override
    public <R1, R2> LinkedMap<R1, R2> bimap(Function<? super K, ? extends R1> fn1,
                                               Function<? super V, ? extends R2> fn2) {
        return fromStream(stream().map(t -> Tuple.tuple(fn1.apply(t._1()),
                                                        fn2.apply(t._2()))));
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    @Override
    public boolean contains(Tuple2<K, V> t) {
        return map.contains(t);
    }

    public LinkedMap<K, V> put(K key,
                               V value) {
        Vector<Tuple2<K, V>> newOrder = get(key).map(v -> order.replaceFirst(Tuple.tuple(key,
                                                                                         v),
                                                                             Tuple.tuple(key,
                                                                                         value)))
                                                .orElseGet(() -> order.plus(Tuple.tuple(key,
                                                                                        value)));
        return new LinkedMap<>(map.put(key,
                                       value),
                               newOrder);

    }


    @Override
    public LinkedMap<K, V> put(Tuple2<K, V> keyAndValue) {
        return put(keyAndValue._1(),
                   keyAndValue._2());
    }

    @Override
    public LinkedMap<K, V> putAll(PersistentMap<? extends K, ? extends V> map) {
        PersistentMap<K, V> narrow = PersistentMap.narrow(map);
        ImmutableMap<K, V> res = HashMap.empty();
        Vector<Tuple2<K, V>> ordering = order;
        for (Tuple2<K, V> t : narrow) {
            if (containsKey(t._1())) {
                ordering = ordering.replaceFirst(Tuple.tuple(t._1(),
                                                             getOrElse(t._1(),
                                                                       null)),
                                                 t);
            } else {
                ordering = ordering.plus(t);
            }
            res = res.put(t);

        }
        return new LinkedMap<K, V>(res,
                                   ordering);
    }

    public LinkedMap<K, V> remove(K key) {
        return containsKey(key) ? new LinkedMap<K, V>(map.remove(key),
                                                      order.removeFirst(t -> Objects.equals(key,
                                                                                            t._1()))) : this;
    }

    @Override
    public LinkedMap<K, V> removeAll(K... keys) {
        LinkedMap<K, V> cur = this;
        for (K key : keys) {
            cur = cur.remove(key);
        }
        return cur;
    }


    @Override
    public String toString() {
        return mkString();
    }

    @Override
    public Iterator<Tuple2<K, V>> iterator() {
        return stream().iterator();
    }

    @Override
    public LinkedMap<K, V> filterNot(Predicate<? super Tuple2<K, V>> predicate) {
        return (LinkedMap<K, V>) ImmutableMap.super.filterNot(predicate);
    }

    @Override
    public LinkedMap<K, V> notNull() {
        return (LinkedMap<K, V>) ImmutableMap.super.notNull();
    }

    @Override
    public LinkedMap<K, V> peek(Consumer<? super V> c) {
        return (LinkedMap<K, V>) ImmutableMap.super.peek(c);
    }


    @Override
    public LinkedMap<K, V> bipeek(Consumer<? super K> c1,
                                  Consumer<? super V> c2) {
        return (LinkedMap<K, V>) ImmutableMap.super.bipeek(c1,
                                                           c2);
    }


    @Override
    public LinkedMap<K, V> onEmpty(Tuple2<K, V> value) {
        return (LinkedMap<K, V>) ImmutableMap.super.onEmpty(value);
    }

    @Override
    public LinkedMap<K, V> onEmptyGet(Supplier<? extends Tuple2<K, V>> supplier) {
        return (LinkedMap<K, V>) ImmutableMap.super.onEmptyGet(supplier);
    }


    @Override
    public LinkedMap<K, V> onEmptySwitch(Supplier<? extends ImmutableMap<K, V>> supplier) {
        return (LinkedMap<K, V>) ImmutableMap.super.onEmptySwitch(supplier);
    }

    @Override
    public <K1, K2, K3, K4, R1, R2, R3, R> LinkedMap<K4, R> forEach4(Function<? super Tuple2<K, V>, ? extends Iterable<Tuple2<K1, R1>>> iterable1,
                                                                     BiFunction<? super Tuple2<K, V>, ? super Tuple2<K1, R1>, ? extends Iterable<Tuple2<K2, R2>>> iterable2,
                                                                     Function3<? super Tuple2<K, V>, ? super Tuple2<K1, R1>, ? super Tuple2<K2, R2>, ? extends Iterable<Tuple2<K3, R3>>> iterable3,
                                                                     Function4<? super Tuple2<K, V>, ? super Tuple2<K1, R1>, ? super Tuple2<K2, R2>, ? super Tuple2<K3, R3>, ? extends Tuple2<K4, R>> yieldingFunction) {
        return (LinkedMap<K4, R>) ImmutableMap.super.forEach4(iterable1,
                                                              iterable2,
                                                              iterable3,
                                                              yieldingFunction);
    }

    @Override
    public <K1, K2, K3, K4, R1, R2, R3, R> LinkedMap<K4, R> forEach4(Function<? super Tuple2<K, V>, ? extends Iterable<Tuple2<K1, R1>>> iterable1,
                                                                     BiFunction<? super Tuple2<K, V>, ? super Tuple2<K1, R1>, ? extends Iterable<Tuple2<K2, R2>>> iterable2,
                                                                     Function3<? super Tuple2<K, V>, ? super Tuple2<K1, R1>, ? super Tuple2<K2, R2>, ? extends Iterable<Tuple2<K3, R3>>> iterable3,
                                                                     Function4<? super Tuple2<K, V>, ? super Tuple2<K1, R1>, ? super Tuple2<K2, R2>, ? super Tuple2<K3, R3>, Boolean> filterFunction,
                                                                     Function4<? super Tuple2<K, V>, ? super Tuple2<K1, R1>, ? super Tuple2<K2, R2>, ? super Tuple2<K3, R3>, ? extends Tuple2<K4, R>> yieldingFunction) {
        return (LinkedMap<K4, R>) ImmutableMap.super.forEach4(iterable1,
                                                              iterable2,
                                                              iterable3,
                                                              filterFunction,
                                                              yieldingFunction);
    }

    @Override
    public <K1, K2, K3, R1, R2, R> LinkedMap<K3, R> forEach3(Function<? super Tuple2<K, V>, ? extends Iterable<Tuple2<K1, R1>>> iterable1,
                                                             BiFunction<? super Tuple2<K, V>, ? super Tuple2<K1, R1>, ? extends Iterable<Tuple2<K2, R2>>> iterable2,
                                                             Function3<? super Tuple2<K, V>, ? super Tuple2<K1, R1>, ? super Tuple2<K2, R2>, ? extends Tuple2<K3, R>> yieldingFunction) {
        return (LinkedMap<K3, R>) ImmutableMap.super.forEach3(iterable1,
                                                              iterable2,
                                                              yieldingFunction);
    }

    @Override
    public <K1, K2, K3, R1, R2, R> LinkedMap<K3, R> forEach3(Function<? super Tuple2<K, V>, ? extends Iterable<Tuple2<K1, R1>>> iterable1,
                                                             BiFunction<? super Tuple2<K, V>, ? super Tuple2<K1, R1>, ? extends Iterable<Tuple2<K2, R2>>> iterable2,
                                                             Function3<? super Tuple2<K, V>, ? super Tuple2<K1, R1>, ? super Tuple2<K2, R2>, Boolean> filterFunction,
                                                             Function3<? super Tuple2<K, V>, ? super Tuple2<K1, R1>, ? super Tuple2<K2, R2>, ? extends Tuple2<K3, R>> yieldingFunction) {
        return (LinkedMap<K3, R>) ImmutableMap.super.forEach3(iterable1,
                                                              iterable2,
                                                              filterFunction,
                                                              yieldingFunction);

    }

    @Override
    public <K1, K2, R1, R> LinkedMap<K2, R> forEach2(Function<? super Tuple2<K, V>, ? extends Iterable<Tuple2<K1, R1>>> iterable1,
                                                     BiFunction<? super Tuple2<K, V>, ? super Tuple2<K1, R1>, ? extends Tuple2<K2, R>> yieldingFunction) {
        return (LinkedMap<K2, R>) ImmutableMap.super.forEach2(iterable1,
                                                              yieldingFunction);

    }

    @Override
    public <K1, K2, R1, R> LinkedMap<K2, R> forEach2(Function<? super Tuple2<K, V>, ? extends Iterable<Tuple2<K1, R1>>> iterable1,
                                                     BiFunction<? super Tuple2<K, V>, ? super Tuple2<K1, R1>, Boolean> filterFunction,
                                                     BiFunction<? super Tuple2<K, V>, ? super Tuple2<K1, R1>, ? extends Tuple2<K2, R>> yieldingFunction) {
        return (LinkedMap<K2, R>) ImmutableMap.super.forEach2(iterable1,
                                                              filterFunction,
                                                              yieldingFunction);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof PersistentMap) {
            PersistentMap<K, V> m = (PersistentMap<K, V>) o;
            return equalTo(m);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}

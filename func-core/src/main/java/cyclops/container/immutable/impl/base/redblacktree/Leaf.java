package cyclops.container.immutable.impl.base.redblacktree;

import cyclops.container.control.eager.option.Option;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.reactive.ReactiveSeq;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Leaf<K, V> implements Tree<K, V> {

    private static final long serialVersionUID = 1L;
    private final Comparator<? super K> comp;

    public Leaf(Comparator<? super K> comp) {
        this.comp = comp;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isBlack() {
        return true;
    }

    @Override
    public Option<V> get(K key) {
        return Option.none();
    }

    @Override
    public V getOrElse(K key,
                       V alt) {
        return alt;
    }

    @Override
    public V getOrElseGet(K key,
                          Supplier<? extends V> alt) {
        return alt.get();
    }

    @Override
    public Tree<K, V> plus(K key,
                           V value) {
        return new Node(false,
                        new Leaf<>(comp),
                        new Leaf<>(comp),
                        key,
                        value,
                        comp);
    }

    @Override
    public Tree<K, V> minus(K key) {
        return this;
    }

    @Override
    public Comparator<? super K> comparator() {
        return comp;
    }

    @Override
    public ReactiveSeq<Tuple2<K, V>> stream() {
        return ReactiveSeq.empty();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String tree() {
        return "{LEAF}";
    }

    @Override
    public <R> R fold(Function<? super Node<K, V>, ? extends R> fn1,
                      Function<? super Leaf<K, V>, ? extends R> fn2) {
        return fn2.apply(this);
    }

}

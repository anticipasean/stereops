package cyclops.container.immutable.impl;

import cyclops.container.foldable.Deconstructable.Deconstructable2;
import cyclops.container.immutable.tuple.Tuple;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.function.cacheable.Memoize;
import cyclops.function.higherkinded.DataWitness.tree;
import cyclops.function.higherkinded.Higher;
import cyclops.reactive.ReactiveSeq;
import java.util.function.Function;
import java.util.function.Supplier;


public class Tree<T> implements Deconstructable2<T, LazySeq<Tree<T>>>, Higher<tree, T> {

    public final T head;
    private final LazySeq<Tree<T>> subForest;
    private final Supplier<Integer> size;

    private Tree(T head,
                 LazySeq<Tree<T>> subForest) {
        this.head = head;
        this.subForest = subForest;
        this.size = Memoize.memoizeSupplier(() -> 1 + subForest.size());
    }

    public static <T> Tree<T> of(T head,
                                 LazySeq<Tree<T>> subForest) {
        return new Tree<>(head,
                          subForest);
    }

    public static <T> Tree<T> lazy(T head,
                                   Supplier<LazySeq<Tree<T>>> subForest) {
        return new Tree<T>(head,
                           null) {

            @Override
            public LazySeq<Tree<T>> subForest() {
                return subForest.get();
            }
        };
    }

    public static <T, R> Tree<T> unfold(Function<? super R, Tuple2<T, LazySeq<R>>> fn,
                                        R b) {
        Tuple2<T, LazySeq<R>> t2 = fn.apply(b);
        return of(t2._1(),
                  unfoldForest(fn,
                               t2._2()));
    }

    private static <T, R> LazySeq<Tree<T>> unfoldForest(Function<? super R, Tuple2<T, LazySeq<R>>> fn,
                                                        LazySeq<R> list) {
        return list.map(b -> unfold(fn,
                                    b));
    }

    public LazySeq<LazySeq<T>> levels() {
        ReactiveSeq<LazySeq<T>> res = ReactiveSeq.iterate(LazySeq.of(this),
                                                          sf -> sf.flatMap(a -> a.subForest()))
                                                 .takeWhile(l -> !l.isEmpty())
                                                 .map(xs -> xs.map(x -> x.head));
        return LazySeq.fromStream(res);
    }

    public LazySeq<T> flatten() {
        return LazySeq.cons(head,
                            () -> subForest().flatMap(t -> t.flatten()));
    }

    @Override
    public Tuple2<T, LazySeq<Tree<T>>> unapply() {
        return Tuple.tuple(head,
                           subForest());
    }

    public <R> Tree<R> map(Function<? super T, ? extends R> fn) {
        return of(fn.apply(head),
                  subForest().map(t -> t.map(fn)));
    }

    public <R> Tree<R> flatMap(Function<? super T, ? extends Tree<R>> fn) {
        Tree<R> applied = fn.apply(head);
        LazySeq<Tree<R>> children = subForest().map(child -> child.flatMap(fn));
        return of(applied.head,
                  children.prependAll(applied.subForest()));
    }

    public LazySeq<Tree<T>> subForest() {
        return subForest;
    }

    public T head() {
        return head;
    }

    public int size() {
        return size.get();
    }
}



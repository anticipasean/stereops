package cyclops.reactive.collection.container.immutable.lazy.impl;


import cyclops.container.control.eager.option.Option;
import cyclops.container.persistent.PersistentSet;
import cyclops.function.combiner.Reducer;
import cyclops.function.evaluation.Evaluation;
import cyclops.reactive.ReactiveSeq;
import cyclops.reactive.collection.container.immutable.PersistentSetX;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/**
 * An extended List type {@see java.util.List} Extended List operations execute lazily e.g.
 * <pre>
 * {@code
 *    StreamX<Integer> q = StreamX.of(1,2,3)
 *                                      .map(i->i*2);
 * }
 * </pre>
 * The map operation above is not executed immediately. It will only be executed when (if) the data inside the queue is accessed.
 * This allows lazy operations to be chained and executed more efficiently e.g.
 *
 * <pre>
 * {@code
 *    DequeX<Integer> q = DequeX.of(1,2,3)
 *                              .map(i->i*2);
 *                              .filter(i->i<5);
 * }
 * </pre>
 * <p>
 * The operation above is more efficient than the equivalent operation with a ListX.
 *
 * @param <T> the type of elements held in this toX
 * @author johnmcclean
 */
public class LazyPSetX<T> extends AbstractLazyPersistentCollection<T, PersistentSet<T>> implements PersistentSetX<T> {

    public LazyPSetX(PersistentSet<T> list,
                     ReactiveSeq<T> seq,
                     Reducer<PersistentSet<T>, T> reducer,
                     Evaluation strict) {
        super(list,
              seq,
              reducer,
              strict,
              asyncSet());


    }

    public static final <T> Function<ReactiveSeq<PersistentSet<T>>, PersistentSet<T>> asyncSet() {
        return r -> {
            CompletablePersistentSetX<T> res = new CompletablePersistentSetX<>();
            r.forEachAsync(l -> res.complete(l));
            return res.asPersistentSetX();
        };
    }

    //@Override
    public PersistentSetX<T> materialize() {
        get();
        return this;
    }


    @Override
    public PersistentSetX<T> type(Reducer<? extends PersistentSet<T>, T> reducer) {
        return new LazyPSetX<T>(list,
                                seq.get(),
                                Reducer.narrow(reducer),
                                evaluation());
    }

    //  @Override
    public <X> LazyPSetX<X> fromStream(ReactiveSeq<X> stream) {

        return new LazyPSetX<X>((PersistentSet) getList(),
                                ReactiveSeq.fromStream(stream),
                                (Reducer) this.getCollectorInternal(),
                                evaluation());
    }

    @Override
    public <T1> LazyPSetX<T1> from(Iterable<T1> c) {
        if (c instanceof PersistentSet) {
            return new LazyPSetX<T1>((PersistentSet) c,
                                     null,
                                     (Reducer) this.getCollectorInternal(),
                                     evaluation());
        }
        return fromStream(ReactiveSeq.fromIterable(c));
    }

    public <T1> LazyPSetX<T1> from(PersistentSet<T1> c) {

        return new LazyPSetX<T1>(c,
                                 null,
                                 (Reducer) this.getCollectorInternal(),
                                 evaluation());

    }

    @Override
    public PersistentSetX<T> lazy() {
        return new LazyPSetX<T>(list,
                                seq.get(),
                                getCollectorInternal(),
                                Evaluation.LAZY);
    }

    @Override
    public PersistentSetX<T> eager() {
        return new LazyPSetX<T>(list,
                                seq.get(),
                                getCollectorInternal(),
                                Evaluation.EAGER);
    }

    @Override
    public PersistentSetX<T> plus(T e) {
        return from(get().plus(e));
    }

    @Override
    public PersistentSetX<T> plusAll(Iterable<? extends T> list) {
        return from(get().plusAll(list));
    }


    @Override
    public PersistentSetX<T> removeAll(Iterable<? extends T> list) {
        return from(get().removeAll(list));
    }


    @Override
    public PersistentSetX<T> removeValue(T remove) {
        return from(get().removeValue(remove));
    }


    @Override
    public <U> LazyPSetX<U> unitIterable(Iterable<U> it) {
        return fromStream(ReactiveSeq.fromIterable(it));
    }


    @Override
    public <R> LazyPSetX<R> unit(Iterable<R> col) {
        return from(col);
    }

    @Override
    public PersistentSetX<T> plusLoop(int max,
                                      IntFunction<T> value) {
        return (PersistentSetX<T>) super.plusLoop(max,
                                                  value);
    }

    @Override
    public PersistentSetX<T> plusLoop(Supplier<Option<T>> supplier) {
        return (PersistentSetX<T>) super.plusLoop(supplier);
    }
}

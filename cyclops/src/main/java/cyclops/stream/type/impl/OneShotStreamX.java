package cyclops.stream.type.impl;

import static cyclops.reactive.ReactiveSeq.fromIterable;

import cyclops.container.control.Option;
import cyclops.container.immutable.impl.Seq;
import cyclops.container.immutable.tuple.Tuple;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.container.immutable.tuple.Tuple3;
import cyclops.container.immutable.tuple.Tuple4;
import cyclops.reactive.ReactiveSeq;
import cyclops.stream.companion.Streams;
import cyclops.stream.spliterator.ReversableSpliterator;
import cyclops.stream.type.Streamable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class OneShotStreamX<T> extends SpliteratorBasedStream<T> {

    public OneShotStreamX(Stream<T> stream) {
        super(stream);
    }

    public OneShotStreamX(Spliterator<T> stream,
                          Optional<ReversableSpliterator> rev) {
        super(stream,
              rev);
    }

    public OneShotStreamX(Stream<T> stream,
                          Optional<ReversableSpliterator> rev) {
        super(stream,
              rev);
    }

    @Override
    public ReactiveSeq<T> reverse() {
        if (reversible.isPresent()) {
            reversible.ifPresent(r -> r.invert());
            return this;
        }
        return createSeq(Streams.reverse(this),
                         reversible);
    }

    @Override
    public <U> ReactiveSeq<Tuple2<T, U>> crossJoin(ReactiveSeq<? extends U> other) {
        Streamable<? extends U> s = Streamable.fromStream(other);
        return forEach2(a -> fromIterable(s),
                        Tuple::tuple);
    }

    @Override
    public final ReactiveSeq<T> cycle() {
        return createSeq(Streams.cycle(unwrapStream()),
                         reversible);
    }

    @Override
    public final Tuple2<ReactiveSeq<T>, ReactiveSeq<T>> duplicate() {
        final Tuple2<Stream<T>, Stream<T>> tuple = Streams.duplicate(unwrapStream());
        return tuple.map1(s -> createSeq(s,
                                         reversible.map(r -> r.copy())))
                    .map2(s -> createSeq(s,
                                         reversible.map(r -> r.copy())));
    }

    @Override
    public final Tuple2<ReactiveSeq<T>, ReactiveSeq<T>> duplicate(Supplier<Deque<T>> bufferFactory) {
        final Tuple2<Stream<T>, Stream<T>> tuple = Streams.duplicate(unwrapStream(),
                                                                     bufferFactory);
        return tuple.map1(s -> createSeq(s,
                                         reversible.map(r -> r.copy())))
                    .map2(s -> createSeq(s,
                                         reversible.map(r -> r.copy())));
    }

    @Override
    @SuppressWarnings("unchecked")
    public final Tuple3<ReactiveSeq<T>, ReactiveSeq<T>, ReactiveSeq<T>> triplicate() {

        final Tuple3<Stream<T>, Stream<T>, Stream<T>> tuple = Streams.triplicate(unwrapStream());
        return tuple.map1(s -> createSeq(s,
                                         reversible.map(r -> r.copy())))
                    .map2(s -> createSeq(s,
                                         reversible.map(r -> r.copy())))
                    .map3(s -> createSeq(s,
                                         reversible.map(r -> r.copy())));

    }

    @Override
    @SuppressWarnings("unchecked")
    public final Tuple3<ReactiveSeq<T>, ReactiveSeq<T>, ReactiveSeq<T>> triplicate(Supplier<Deque<T>> bufferFactory) {

        final Tuple3<Stream<T>, Stream<T>, Stream<T>> tuple = Streams.triplicate(unwrapStream(),
                                                                                 bufferFactory);
        return tuple.map1(s -> createSeq(s,
                                         reversible.map(r -> r.copy())))
                    .map2(s -> createSeq(s,
                                         reversible.map(r -> r.copy())))
                    .map3(s -> createSeq(s,
                                         reversible.map(r -> r.copy())));

    }

    @Override
    @SuppressWarnings("unchecked")
    public final Tuple4<ReactiveSeq<T>, ReactiveSeq<T>, ReactiveSeq<T>, ReactiveSeq<T>> quadruplicate() {
        final Tuple4<Stream<T>, Stream<T>, Stream<T>, Stream<T>> tuple = Streams.quadruplicate(unwrapStream());
        return tuple.map1(s -> createSeq(s,
                                         reversible.map(r -> r.copy())))
                    .map2(s -> createSeq(s,
                                         reversible.map(r -> r.copy())))
                    .map3(s -> createSeq(s,
                                         reversible.map(r -> r.copy())))
                    .map4(s -> createSeq(s,
                                         reversible.map(r -> r.copy())));
    }

    @Override
    @SuppressWarnings("unchecked")
    public final Tuple4<ReactiveSeq<T>, ReactiveSeq<T>, ReactiveSeq<T>, ReactiveSeq<T>> quadruplicate(Supplier<Deque<T>> bufferFactory) {
        final Tuple4<Stream<T>, Stream<T>, Stream<T>, Stream<T>> tuple = Streams.quadruplicate(unwrapStream(),
                                                                                               bufferFactory);
        return tuple.map1(s -> createSeq(s,
                                         reversible.map(r -> r.copy())))
                    .map2(s -> createSeq(s,
                                         reversible.map(r -> r.copy())))
                    .map3(s -> createSeq(s,
                                         reversible.map(r -> r.copy())))
                    .map4(s -> createSeq(s,
                                         reversible.map(r -> r.copy())));
    }

    @Override
    public Seq<ReactiveSeq<T>> multicast(int num) {
        return Streams.toBufferingCopier(iterator(),
                                         num,
                                         () -> new ArrayDeque<T>(100))
                      .map(ReactiveSeq::fromIterator);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public final Tuple2<Option<T>, ReactiveSeq<T>> splitAtHead() {
        final Tuple2<ReactiveSeq<T>, ReactiveSeq<T>> t2 = splitAt(1);
        return Tuple2.of(Option.fromIterable(t2._1()
                                               .toList()),
                         t2._2());
    }

    @Override
    public final Tuple2<ReactiveSeq<T>, ReactiveSeq<T>> splitAt(final int where) {
        return Streams.splitAt(this,
                               where)
                      .bimap(s1 -> createSeq(s1,
                                             reversible.map(r -> r.copy())),
                             s2 -> createSeq(s2,
                                             reversible.map(r -> r.copy())));

    }

    @Override
    public final Tuple2<ReactiveSeq<T>, ReactiveSeq<T>> splitBy(final Predicate<T> splitter) {
        return Streams.splitBy(this,
                               splitter)
                      .map1(s -> createSeq(s,
                                           reversible.map(r -> r.copy())))
                      .map2(s -> createSeq(s,
                                           reversible.map(r -> r.copy())));
    }

    @Override
    public final Tuple2<ReactiveSeq<T>, ReactiveSeq<T>> partition(final Predicate<? super T> splitter) {
        return Streams.partition(this,
                                 splitter)
                      .map1(s -> createSeq(s,
                                           reversible.map(r -> r.copy())))
                      .map2(s -> createSeq(s,
                                           reversible.map(r -> r.copy())));
    }


    @Override
    public final ReactiveSeq<T> cycleWhile(final Predicate<? super T> predicate) {

        return createSeq(Streams.cycle(unwrapStream()),
                         reversible).takeWhile(predicate);
    }

    @Override
    public final ReactiveSeq<T> cycleUntil(final Predicate<? super T> predicate) {
        return createSeq(Streams.cycle(unwrapStream()),
                         reversible).takeWhile(predicate.negate());
    }


    @Override
    public ReactiveSeq<T> cycle(long times) {
        return createSeq(Streams.cycle(times,
                                       Streamable.fromStream(unwrapStream())),
                         reversible);
    }

    @Override
    <X> ReactiveSeq<X> createSeq(Stream<X> stream,
                                 Optional<ReversableSpliterator> reversible) {
        return new OneShotStreamX<X>(stream,
                                     reversible);
    }

    @Override
    <X> ReactiveSeq<X> createSeq(Spliterator<X> stream,
                                 Optional<ReversableSpliterator> reversible) {
        return new OneShotStreamX<X>(stream,
                                     reversible);
    }

    Spliterator<T> get() {
        return stream;
    }

    public <R> R fold(Function<? super ReactiveSeq<T>, ? extends R> sync,
                      Function<? super ReactiveSeq<T>, ? extends R> reactiveStreams,
                      Function<? super ReactiveSeq<T>, ? extends R> asyncNoBackPressure) {
        return sync.apply(this);
    }


}

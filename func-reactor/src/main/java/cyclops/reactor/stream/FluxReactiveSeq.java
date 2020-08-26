package cyclops.reactor.stream;

import cyclops.reactive.ReactiveSeq;
import cyclops.reactor.stream.impl.FluxReactiveSeqImpl;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.SynchronousSink;
import reactor.core.scheduler.Scheduler;

/*
 * Factory methods for creating ReactiveSeq instances that are backed by Flux
 */
public interface FluxReactiveSeq {

    static <T> ReactiveSeq<T> reactiveSeq(Flux<T> flux) {
        return new FluxReactiveSeqImpl<>(flux);
    }

    static <T> ReactiveSeq<T> reactiveSeq(Publisher<T> flux) {
        return new FluxReactiveSeqImpl<>(Flux.from(flux));
    }

    static ReactiveSeq<Integer> range(int start,
                                      int end) {
        return reactiveSeq(Flux.range(start,
                                      end));
    }

    static <T> ReactiveSeq<T> of(T... data) {
        return reactiveSeq(Flux.just(data));
    }

    static <T> ReactiveSeq<T> of(T value) {
        return reactiveSeq(Flux.just(value));
    }

    static <T> ReactiveSeq<T> ofNullable(T nullable) {
        if (nullable == null) {
            return empty();
        }
        return of(nullable);
    }

    static <T> ReactiveSeq<T> create(Consumer<? super FluxSink<T>> emitter) {
        return reactiveSeq(Flux.create(emitter));
    }


    static <T> ReactiveSeq<T> create(Consumer<? super FluxSink<T>> emitter,
                                     FluxSink.OverflowStrategy backpressure) {
        return reactiveSeq(Flux.create(emitter,
                                       backpressure));
    }


    static <T> ReactiveSeq<T> defer(Supplier<? extends Publisher<T>> supplier) {
        return reactiveSeq(Flux.defer(supplier));
    }

    static <T> ReactiveSeq<T> empty() {
        return reactiveSeq(Flux.empty());
    }


    static <T> ReactiveSeq<T> error(Throwable error) {
        return reactiveSeq(Flux.error(error));
    }


    static <O> ReactiveSeq<O> error(Throwable throwable,
                                    boolean whenRequested) {
        return reactiveSeq(Flux.error(throwable,
                                      whenRequested));
    }


    @SafeVarargs
    static <I> ReactiveSeq<I> first(Publisher<? extends I>... sources) {
        return reactiveSeq(Flux.first(sources));
    }


    static <I> ReactiveSeq<I> first(Iterable<? extends Publisher<? extends I>> sources) {
        return reactiveSeq(Flux.first(sources));
    }


    static <T> ReactiveSeq<T> from(Publisher<? extends T> source) {
        return reactiveSeq(Flux.from(source));
    }


    static <T> ReactiveSeq<T> fromIterable(Iterable<? extends T> it) {
        return reactiveSeq(Flux.fromIterable(it));
    }


    static <T> ReactiveSeq<T> fromStream(Stream<? extends T> s) {
        return reactiveSeq(Flux.fromStream(s));
    }


    static <T> ReactiveSeq<T> generate(Consumer<SynchronousSink<T>> generator) {
        return reactiveSeq(Flux.generate(generator));
    }


    static <T, S> ReactiveSeq<T> generate(Callable<S> stateSupplier,
                                          BiFunction<S, SynchronousSink<T>, S> generator) {
        return reactiveSeq(Flux.generate(stateSupplier,
                                         generator));
    }


    static <T, S> ReactiveSeq<T> generate(Callable<S> stateSupplier,
                                          BiFunction<S, SynchronousSink<T>, S> generator,
                                          Consumer<? super S> stateConsumer) {
        return reactiveSeq(Flux.generate(stateSupplier,
                                         generator,
                                         stateConsumer));
    }


    static ReactiveSeq<Long> interval(Duration period) {
        return reactiveSeq(Flux.interval(period));
    }


    static ReactiveSeq<Long> interval(Duration delay,
                                      Duration period) {
        return reactiveSeq(Flux.interval(delay,
                                         period));
    }

    static ReactiveSeq<Long> interval(Duration period,
                                      Scheduler timer) {
        return reactiveSeq(Flux.interval(period,
                                         timer));
    }

    static ReactiveSeq<Long> interval(Duration delay,
                                      Duration period,
                                      Scheduler timer) {
        return reactiveSeq(Flux.interval(delay,
                                         period,
                                         timer));
    }

    @SafeVarargs
    static <T> ReactiveSeq<T> just(T... data) {
        return reactiveSeq(Flux.just(data));
    }


    static <T> ReactiveSeq<T> just(T data) {
        return reactiveSeq(Flux.just(data));
    }


}

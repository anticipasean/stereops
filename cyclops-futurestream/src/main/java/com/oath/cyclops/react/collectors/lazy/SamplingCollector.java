package com.oath.cyclops.react.collectors.lazy;

import cyclops.internal.react.async.future.FastFuture;
import java.util.Collection;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.experimental.Builder;
import lombok.experimental.Wither;

/**
 * Class that allows client code to only collect a sample of results from an Infinite SimpleReact Stream
 * <p>
 * The SamplingCollector won't collect results itself, but hand of control to a consumer that can when Sampling triggered.
 *
 * @param <T> Result type
 * @author johnmcclean
 */
@AllArgsConstructor
@lombok.With
@Builder
public class SamplingCollector<T> implements LazyResultConsumer<T> {

    private final int sampleRate;
    private final LazyResultConsumer<T> consumer;
    private long count = 0;

    /**
     * @param sampleRate Modulus of sampleRate will determine result toX
     * @param consumer   SamplingCollector won't actually collect results, it passes control to another consumer when triggered.
     */
    public SamplingCollector(final int sampleRate,
                             final LazyResultConsumer<T> consumer) {
        this.sampleRate = sampleRate;
        this.consumer = consumer;
    }

    /* (non-Javadoc)
     * @see java.util.function.Consumer#accept(java.lang.Object)
     */
    @Override
    public void accept(final FastFuture<T> t) {
        if (count++ % sampleRate == 0) {
            consumer.accept(t);
        }

    }

    @Override
    public void block(final Function<FastFuture<T>, T> safeJoin) {
        consumer.block(safeJoin);
    }

    /* (non-Javadoc)
     * @see com.oath.cyclops.react.collectors.lazy.LazyResultConsumer#withResults(java.util.Collection)
     */
    @Override
    public LazyResultConsumer<T> withResults(final Collection<FastFuture<T>> t) {
        return this.withConsumer(consumer.withResults(t));
    }

    /* (non-Javadoc)
     * @see com.oath.cyclops.react.collectors.lazy.LazyResultConsumer#getResults()
     */
    @Override
    public Collection<FastFuture<T>> getResults() {
        return consumer.getResults();
    }

    @Override
    public Collection<FastFuture<T>> getAllResults() {
        return consumer.getResults();
    }

}

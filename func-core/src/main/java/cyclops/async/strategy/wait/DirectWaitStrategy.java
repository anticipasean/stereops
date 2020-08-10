package cyclops.async.strategy.wait;

/**
 * Will try to access the queue once, and return the result directly from the Queue
 * <p>
 * Effectively the same as calling queue.take() / queue.offer(T val)
 *
 * @param <T> Data type of elements in async.Queue
 * @author johnmcclean
 */
public class DirectWaitStrategy<T> implements WaitStrategy<T> {

    /* (non-Javadoc)
     * @see cyclops2.async.wait.WaitStrategy#take(cyclops2.async.wait.WaitStrategy.Takeable)
     */
    @Override
    public T take(final cyclops.async.strategy.wait.WaitStrategy.Takeable<T> t) throws InterruptedException {
        return t.take();
    }

    /* (non-Javadoc)
     * @see cyclops2.async.wait.WaitStrategy#offer(cyclops2.async.wait.WaitStrategy.Offerable)
     */
    @Override
    public boolean offer(final cyclops.async.strategy.wait.WaitStrategy.Offerable o) throws InterruptedException {
        return o.offer();
    }

}

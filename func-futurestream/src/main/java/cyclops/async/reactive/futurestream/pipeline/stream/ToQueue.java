package cyclops.async.reactive.futurestream.pipeline.stream;

import cyclops.async.queue.Queue;
import cyclops.async.queue.QueueFactory;
import java.util.Map;
import java.util.function.Function;

/**
 * interface that defines the conversion of an object to a queue
 *
 * @param <U> Data type
 * @author johnmcclean
 */
public interface ToQueue<U> {

    /**
     * @return Data in a queue
     */
    abstract Queue<U> toQueue();

    /**
     * Sharded data in queues
     *
     * @param shards  Map of Queues sharded by key K
     * @param sharder Sharder function
     */
    abstract <K> void toQueue(Map<K, Queue<U>> shards,
                              Function<? super U, ? extends K> sharder);

    /**
     * @return Factory for creating Queues to be populated
     */
    abstract QueueFactory<U> getQueueFactory();

    /**
     * Method to create a Queue that can be modified by supplied funciton
     *
     * @param modifier Function to modify default Queue
     * @return Populated Queue.
     */
    abstract Queue<U> toQueue(Function<Queue, Queue> modifier);

    void addToQueue(Queue queue);

}

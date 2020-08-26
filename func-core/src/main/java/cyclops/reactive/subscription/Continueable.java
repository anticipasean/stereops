package cyclops.reactive.subscription;

import cyclops.async.queue.Queue;

public interface Continueable {

    void closeQueueIfFinished(Queue queue);

    void addQueue(Queue queue);

    void registerSkip(long skip);

    void registerLimit(long limit);

    void closeAll(Queue q);

    boolean closed();

    void closeQueueIfFinishedStateless(Queue queue);

    void closeAll();

    long timeLimit();

    void registerTimeLimit(long nanos);
}

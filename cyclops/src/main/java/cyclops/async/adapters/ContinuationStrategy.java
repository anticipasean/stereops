package cyclops.async.adapters;

import cyclops.stream.async.Continuation;

public interface ContinuationStrategy {

    void addContinuation(Continuation c);

    void handleContinuation();

    default boolean isBlocking() {
        return false;
    }
}

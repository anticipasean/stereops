package cyclops.async.adapters;

import cyclops.stream.async.Continuation;

public interface ContinuationStrategy {

    public void addContinuation(Continuation c);

    public void handleContinuation();

    default boolean isBlocking() {
        return false;
    }
}

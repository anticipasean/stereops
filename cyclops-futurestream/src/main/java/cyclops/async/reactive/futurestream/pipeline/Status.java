package cyclops.async.reactive.futurestream.pipeline;

import cyclops.container.persistent.PersistentList;
import cyclops.reactive.collection.container.immutable.LinkedListX;
import lombok.AllArgsConstructor;

/**
 * Class that returned to blocking predicates for short circuiting result toX
 *
 * @param <T> Result type
 * @author johnmcclean
 */
@AllArgsConstructor
public class Status<T> {

    private final int completed;
    private final int errors;
    private final int total;
    private final long elapsedNanos;
    private final LinkedListX<T> resultsSoFar;

    public final int getAllCompleted() {
        return completed + errors;
    }

    public final long getElapsedMillis() {
        return elapsedNanos / 1000000;
    }

    public int getCompleted() {
        return completed;
    }

    public int getErrors() {
        return errors;
    }

    public int getTotal() {
        return total;
    }

    public long getElapsedNanos() {
        return elapsedNanos;
    }

    public PersistentList<T> getResultsSoFar() {
        return resultsSoFar;
    }
}

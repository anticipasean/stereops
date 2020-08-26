package cyclops.async.exception;

import cyclops.exception.SimpleReactProcessingException;
import java.util.List;
import lombok.Getter;

/**
 * Exception thrown if Queue closed
 *
 * @author johnmcclean
 */
public class ClosedQueueException extends SimpleReactProcessingException {

    private static final long serialVersionUID = 1L;
    @Getter
    private final List currentData;

    public ClosedQueueException(List currentData) {
        this.currentData = currentData;
    }

    public ClosedQueueException() {
        currentData = null;

    }

    public boolean isDataPresent() {
        return currentData != null;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}

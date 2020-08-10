package cyclops.async.exception;

import cyclops.exception.SimpleReactProcessingException;

/**
 * Exception thrown if Queue polling timesout
 *
 * @author johnmcclean
 */
public class QueueTimeoutException extends SimpleReactProcessingException {

    private static final long serialVersionUID = 1L;

    @Override
    public Throwable fillInStackTrace() {

        return this;
    }
}

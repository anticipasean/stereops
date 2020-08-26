package cyclops.async.reactive.futurestream.exception;

import cyclops.exception.SimpleReactProcessingException;

public class FilteredExecutionPathException extends SimpleReactProcessingException {

    private static final long serialVersionUID = 1L;

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}

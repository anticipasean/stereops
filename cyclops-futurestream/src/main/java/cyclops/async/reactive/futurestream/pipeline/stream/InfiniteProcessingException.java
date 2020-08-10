package cyclops.async.reactive.futurestream.pipeline.stream;

import cyclops.exception.SimpleReactProcessingException;

public class InfiniteProcessingException extends SimpleReactProcessingException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InfiniteProcessingException(final String message) {
        super(message);
    }

}

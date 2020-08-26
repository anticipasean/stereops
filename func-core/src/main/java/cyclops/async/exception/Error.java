package cyclops.async.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Accessors(fluent = true)
public final class Error extends RuntimeException {

    @Getter
    Throwable throwable;

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

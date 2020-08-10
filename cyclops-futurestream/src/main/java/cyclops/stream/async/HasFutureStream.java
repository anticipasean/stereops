package cyclops.stream.async;

import cyclops.futurestream.FutureStream;

public interface HasFutureStream<T> {

    FutureStream<T> getStream();
}

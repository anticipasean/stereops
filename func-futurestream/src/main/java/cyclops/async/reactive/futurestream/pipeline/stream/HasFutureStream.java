package cyclops.async.reactive.futurestream.pipeline.stream;

import cyclops.async.reactive.futurestream.FutureStream;

public interface HasFutureStream<T> {

    FutureStream<T> getStream();
}

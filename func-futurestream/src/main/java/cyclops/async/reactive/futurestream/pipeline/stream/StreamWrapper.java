package cyclops.async.reactive.futurestream.pipeline.stream;

import java.util.stream.Stream;

public interface StreamWrapper<U> {

    public Stream<U> stream();

}

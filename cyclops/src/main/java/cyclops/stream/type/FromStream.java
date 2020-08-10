package cyclops.stream.type;

import cyclops.reactive.ReactiveSeq;

public interface FromStream<T> {

    public <R> R fromStream(ReactiveSeq<T> t);
}

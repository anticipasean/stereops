package cyclops.stream.type;

import cyclops.reactive.ReactiveSeq;

public interface FromStream<T> {

    <R> R fromStream(ReactiveSeq<T> t);
}

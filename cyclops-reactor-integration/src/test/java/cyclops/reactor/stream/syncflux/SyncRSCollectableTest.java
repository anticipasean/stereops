package cyclops.reactor.stream.syncflux;


import cyclops.reactive.ReactiveSeq;
import cyclops.reactor.stream.CollectableTest;
import cyclops.reactor.stream.FluxReactiveSeq;
import reactor.core.publisher.Flux;

public class SyncRSCollectableTest extends CollectableTest {


    public <T> ReactiveSeq<T> of(T... values) {
        return FluxReactiveSeq.reactiveSeq(Flux.just(values));
    }

}

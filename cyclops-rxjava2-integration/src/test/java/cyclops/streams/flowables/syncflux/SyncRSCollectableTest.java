package cyclops.streams.flowables.syncflux;


import cyclops.pure.reactive.FlowableReactiveSeq;
import cyclops.reactive.ReactiveSeq;
import cyclops.streams.flowables.CollectableTest;
import reactor.core.publisher.Flux;

public class SyncRSCollectableTest extends CollectableTest {


    public <T> ReactiveSeq<T> of(T... values) {
        return FlowableReactiveSeq.reactiveSeq(Flux.just(values));
    }

}

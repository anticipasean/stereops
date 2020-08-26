package cyclops.rxjava2.companion.flowables.sync;


import cyclops.reactive.ReactiveSeq;
import cyclops.rxjava2.adapter.CollectableTest;
import cyclops.rxjava2.adapter.FlowableReactiveSeq;
import reactor.core.publisher.Flux;

public class SyncRSCollectableTest extends CollectableTest {


    public <T> ReactiveSeq<T> of(T... values) {
        return FlowableReactiveSeq.reactiveSeq(Flux.just(values));
    }

}

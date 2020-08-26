package cyclops.rxjava2.companion.flowables.async;


import cyclops.rxjava2.adapter.FlowableReactiveSeq;
import cyclops.reactive.ReactiveSeq;
import cyclops.rxjava2.adapter.CollectableTest;
import java.util.concurrent.ForkJoinPool;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class AsyncRSCollectableTest extends CollectableTest {


    public <T> ReactiveSeq<T> of(T... values) {

        return FlowableReactiveSeq.reactiveSeq(Flux.just(values)
                                                   .subscribeOn(Schedulers.fromExecutor(ForkJoinPool.commonPool())));
    }

}

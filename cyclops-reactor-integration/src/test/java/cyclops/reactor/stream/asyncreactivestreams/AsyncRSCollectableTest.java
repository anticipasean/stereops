package cyclops.reactor.stream.asyncreactivestreams;

import cyclops.reactor.stream.FluxReactiveSeq;
import cyclops.reactive.ReactiveSeq;
import cyclops.reactor.stream.CollectableTest;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ForkJoinPool;

public class AsyncRSCollectableTest extends CollectableTest {


    public <T> ReactiveSeq<T> of(T... values){

        return FluxReactiveSeq.reactiveSeq(Flux.just(values)
                .subscribeOn(Schedulers.fromExecutor(ForkJoinPool.commonPool())));
    }

}

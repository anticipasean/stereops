package cyclops.stream.push.asyncreactivestreams;


import cyclops.container.foldable.Foldable;
import cyclops.reactive.companion.Spouts;
import cyclops.stream.CollectableTest;
import java.util.concurrent.ForkJoinPool;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class AsyncRSCollectableTest extends CollectableTest {


    public <T> Foldable<T> of(T... values) {

        return Spouts.from(Flux.just(values)
                               .subscribeOn(Schedulers.fromExecutor(ForkJoinPool.commonPool())));
    }

}

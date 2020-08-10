package cyclops.streams.push.asyncreactivestreams;

import cyclops.reactive.ReactiveSeq;
import cyclops.reactive.companion.Spouts;
import cyclops.streams.AbstractReactiveSeqTest;
import java.util.concurrent.ForkJoinPool;
import org.junit.Ignore;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Ignore
public class AsyncRSReactiveSeqTest extends AbstractReactiveSeqTest {

    @Override
    public ReactiveSeq<Integer> of(Integer... values) {
        return Spouts.from(Flux.just(values)
                               .subscribeOn(Schedulers.fromExecutor(ForkJoinPool.commonPool())));
    }

    @Override
    public ReactiveSeq<Integer> empty() {
        return Spouts.from(Flux.<Integer>empty().subscribeOn(Schedulers.fromExecutor(ForkJoinPool.commonPool())));
    }


}

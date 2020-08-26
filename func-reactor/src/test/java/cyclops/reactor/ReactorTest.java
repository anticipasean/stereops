package cyclops.reactor;


import cyclops.reactive.ReactiveSeq;
import cyclops.reactive.collection.container.mutable.SetX;
import cyclops.reactor.container.higherkinded.FluxAnyM;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ReactorTest {

    @Test
    public void amb() {

        Stream<List<Integer>> stream = Stream.of(Arrays.asList(1,
                                                               2,
                                                               3),
                                                 Arrays.asList(10,
                                                               20,
                                                               30));

        SetX.fromPublisher(Flux.first(ReactiveSeq.of(1,
                                                     2,
                                                     3),
                                      Flux.just(10,
                                                20,
                                                30)));
    }

    @Test
    public void anyMTest() {
        System.out.println("Start");
        //Flux.just(1,2,3,4,5).subscribeOn(Schedulers.fromExecutor(ForkJoinPool.commonPool())).subscribe(System.out::println);
        FluxAnyM.anyM(Flux.just(1,
                                2,
                                3,
                                4,
                                5)
                          .subscribeOn(Schedulers.fromExecutor(ForkJoinPool.commonPool())))
                .forEach(System.out::println,
                         System.err::println);
        System.out.println("Set up");

    }

    static class LinkedList {

    }

}

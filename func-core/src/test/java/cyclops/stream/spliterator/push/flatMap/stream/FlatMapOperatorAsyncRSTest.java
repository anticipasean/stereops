package cyclops.stream.spliterator.push.flatMap.stream;

import cyclops.stream.spliterator.push.AbstractOperatorTest;
import cyclops.stream.spliterator.push.ArrayOfValuesOperator;
import cyclops.stream.spliterator.push.Fixtures;
import cyclops.stream.spliterator.push.FlatMapOperator;
import cyclops.stream.spliterator.push.Operator;
import cyclops.stream.spliterator.push.SingleValueOperator;
import cyclops.reactive.companion.Spouts;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Created by johnmcclean on 17/01/2017.
 */
public class FlatMapOperatorAsyncRSTest extends AbstractOperatorTest {


    public Operator<Integer> createEmpty() {
        return new FlatMapOperator<Integer, Integer>(new ArrayOfValuesOperator<>(),
                                                     i -> asyncSpout(i));
    }

    private Stream<? extends Integer> asyncSpout(Integer i) {
        return Spouts.from(Flux.just(i * 2)
                               .subscribeOn(Schedulers.fromExecutor(Executors.newFixedThreadPool(1))));
    }

    public Operator<Integer> createOne() {
        return new FlatMapOperator<Integer, Integer>(new SingleValueOperator<>(1),
                                                     i -> asyncSpout(i));
    }

    public Operator<Integer> createThree() {
        return new FlatMapOperator<Integer, Integer>(new ArrayOfValuesOperator<>(1,
                                                                                 2,
                                                                                 3),
                                                     i -> asyncSpout(i));
    }

    public Operator<Integer> createTwoAndError() {
        return new FlatMapOperator<Integer, Integer>(Fixtures.twoAndErrorSource,
                                                     i -> asyncSpout(i));
    }

    public Operator<Integer> createThreeErrors() {
        return new FlatMapOperator<Integer, Integer>(Fixtures.threeErrorsSource,
                                                     i -> asyncSpout(i));
    }


}

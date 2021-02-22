package cyclops.stream.operator;

import static cyclops.reactive.ReactiveSeq.of;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import cyclops.async.Future;
import cyclops.container.control.eager.option.Option;
import cyclops.reactive.ReactiveSeq;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.junit.Before;
import org.junit.Test;


//see BaseSequentialSeqTest for in order tests
public class FutureTest {

    static final Executor exec = Executors.newFixedThreadPool(1);
    ReactiveSeq<Integer> empty;
    ReactiveSeq<Integer> nonEmpty;

    @Before
    public void setup() {
        empty = of();
        nonEmpty = of(1);

    }

    @Test
    public void testMapReduce() {
        assertThat(of(1,
                      2,
                      3,
                      4,
                      5).map(it -> it * 100)
                        .foldFuture(exec,
                                    s -> s.foldLeft((acc, next) -> acc + next))
                        .orElse(null),
                   is(Option.of(1500)));
    }

    @Test
    public void testMapReduceSeed() {
        assertThat(of(1,
                      2,
                      3,
                      4,
                      5).map(it -> it * 100)
                        .foldFuture(exec,
                                    s -> s.foldLeft(50,
                                                    (acc, next) -> acc + next))
                        .get(),
                   is(Future.ofResult(1550)
                            .get()));
    }


}

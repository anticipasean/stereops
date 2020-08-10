package cyclops.async.reactive.futurestream.react.base;

import static org.junit.Assert.assertThat;

import cyclops.container.control.Option;
import cyclops.async.reactive.futurestream.FutureStream;
import java.util.function.Supplier;
import org.junit.Before;
import org.junit.Test;


//see BaseSequentialSeqTest for in order tests
public abstract class BaseSeqFutureTest {

    FutureStream<Integer> empty;
    FutureStream<Integer> nonEmpty;

    abstract protected <U> FutureStream<U> of(U... array);

    abstract protected <U> FutureStream<U> ofThread(U... array);

    abstract protected <U> FutureStream<U> react(Supplier<U>... array);

    @Before
    public void setup() {
        empty = of();
        nonEmpty = of(1);

    }

    @Test
    public void testMax() {
        assertThat(of(1,
                      2,
                      3,
                      4,
                      5).foldFuture(s -> s.maximum((t1, t2) -> t1 - t2))
                        .orElse(Option.none()),
                   is(Option.of(5)));
    }

}

package cyclops.futurestream.react.base;

import static org.junit.Assert.assertThat;

import cyclops.container.control.Option;
import cyclops.futurestream.FutureStream;
import java.util.function.Supplier;
import org.junit.Before;
import org.junit.Test;


//see BaseSequentialSeqTest for in order tests
public abstract class BaseSeqLazyTest {

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
                      5).foldLazy(s -> s.maximum((t1, t2) -> t1 - t2))
                        .get(),
                   is(Option.of(5)));
    }

    protected Object value() {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "jello";
    }

    protected int value2() {
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 200;
    }


    protected Object sleep(int i) {
        try {
            Thread.currentThread()
                  .sleep(i);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return i;
    }

}

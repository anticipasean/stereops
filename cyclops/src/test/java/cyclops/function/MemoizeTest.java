package cyclops.function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.concurrent.Executors;
import org.junit.Test;


public class MemoizeTest {

    int x = 0;

    @Test
    public void asyncUpdate() throws InterruptedException {
        Function1<Integer, Integer> fn = Memoize.memoizeFunctionAsync(Lambda.<Integer, Integer>λ(i -> i + x),
                                                                      Executors.newScheduledThreadPool(1),
                                                                      1);
        assertThat(fn.apply(1),
                   equalTo(1));
        assertThat(fn.apply(1),
                   equalTo(1));
        x = x + 1;
        Thread.sleep(2);

        assertThat(fn.apply(1),
                   equalTo(2));
    }

}
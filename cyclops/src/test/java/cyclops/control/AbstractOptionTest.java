package cyclops.control;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.reactivestreams.Publisher;

public abstract class AbstractOptionTest extends AbstractValueTest {

    boolean run;

    protected abstract <T> Option<T> of(T value);

    protected abstract <T> Option<T> empty();

    protected abstract <T> Option<T> fromPublisher(Publisher<T> p);

    @Test
    public void whenEmpty_onEmpty_isRun() {
        run = false;
        empty().onEmpty(() -> run = true)
               .isPresent();
        assertTrue(run);
    }

    @Test
    public void whenNotEmpty_onEmpty_isNotRun() {
        run = false;
        of(10).onEmpty(() -> run = true)
              .isPresent();
        assertFalse(run);
    }

}

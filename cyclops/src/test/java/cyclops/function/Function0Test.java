package cyclops.function;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import cyclops.async.Future;
import cyclops.container.control.Maybe;
import cyclops.container.control.Option;
import cyclops.container.control.Try;
import cyclops.function.enhanced.Function0;
import java.util.concurrent.ForkJoinPool;
import org.junit.Test;

public class Function0Test {

    Function0<Integer> some = () -> 10;
    Function0<Integer> none = () -> null;
    Function0<Integer> ex = () -> {
        throw new RuntimeException();
    };

    @Test
    public void lift() {
        assertThat(some.lift()
                       .get(),
                   equalTo(Option.some(10)));
        assertThat(none.lift()
                       .get(),
                   equalTo(Option.none()));
    }

    @Test
    public void liftEx() {
        assertThat(some.lift(ForkJoinPool.commonPool())
                       .get()
                       .orElse(-1),
                   equalTo(Future.ofResult(10)
                                 .orElse(-1)));
        assertThat(none.lift(ForkJoinPool.commonPool())
                       .get()
                       .orElse(-1),
                   equalTo(null));
    }

    @Test
    public void liftTry() {
        assertThat(some.liftTry()
                       .get(),
                   equalTo(Try.success(10)));
        assertThat(ex.liftTry()
                     .get()
                     .isFailure(),
                   equalTo(true));
        assertThat(none.liftTry()
                       .get()
                       .isFailure(),
                   equalTo(false));
    }

    @Test
    public void lazyLift() {
        assertThat(some.lazyLift()
                       .get(),
                   equalTo(Maybe.just(10)));
        assertThat(none.lazyLift()
                       .get(),
                   equalTo(Maybe.nothing()));
    }
}

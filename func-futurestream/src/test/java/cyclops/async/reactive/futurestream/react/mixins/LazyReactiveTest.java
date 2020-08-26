package cyclops.async.reactive.futurestream.react.mixins;

import static org.junit.Assert.assertFalse;

import cyclops.async.adapters.Queue;
import cyclops.async.reactive.futurestream.Pipes;
import cyclops.pure.reactive.ReactiveSeq;
import org.junit.Test;

public class LazyReactiveTest {

    Pipes<String, String> pipes = Pipes.of();

    @Test
    public void testNoPipe() {
        pipes.register("hello",
                       new Queue<String>());
        pipes.clear();
        assertFalse(pipes.size() > 0);
    }

    @Test
    public void testOneOrError() {
        pipes.register("hello",
                       new Queue<String>());

        pipes.push("hello",
                   "world");

        pipes.get("hello")
             .map(a -> a.close())
             .orElse(false);
        pipes.reactiveSeq("hello")
             .orElse(ReactiveSeq.of("boo!"))
             .forEach(System.out::println);
        //  assertThat(pipes.oneOrError("hello"),equalTo(Xor.lazyRight("world")));

    }

}



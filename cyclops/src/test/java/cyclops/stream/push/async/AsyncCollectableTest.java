package cyclops.stream.push.async;

import cyclops.container.foldable.Foldable;
import cyclops.reactive.companion.Spouts;
import cyclops.stream.CollectableTest;


public class AsyncCollectableTest extends CollectableTest {


    public <T> Foldable<T> of(T... values) {

        return Spouts.<T>async(s -> {
            Thread t = new Thread(() -> {
                for (T next : values) {
                    s.onNext(next);
                }
                s.onComplete();
            });
            t.start();
        });
    }

}

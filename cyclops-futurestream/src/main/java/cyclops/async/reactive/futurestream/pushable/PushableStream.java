package cyclops.async.reactive.futurestream.pushable;

import cyclops.async.adapters.Queue;
import java.util.stream.Stream;

/**
 * A more concrete Tuple2 impl _1 is Queue&lt;T&gt; _2 is Stream&lt;T&gt;
 *
 * @param <T> data type
 * @author johnmcclean
 */
public class PushableStream<T> extends AbstractPushableStream<T, Queue<T>, Stream<T>> {

    private static final long serialVersionUID = 1L;

    public PushableStream(final Queue<T> v1,
                          final Stream<T> v2) {
        super(v1,
              v2);
    }

}

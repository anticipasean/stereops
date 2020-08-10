package cyclops.async.reactive.futurestream.pushable;

import cyclops.async.queue.Queue;
import cyclops.async.reactive.futurestream.FutureStream;

/**
 * A more concrete Tuple2 impl _1 is Queue&lt;T&gt; _2 is LazyFutureStream&lt;T&gt;
 *
 * @param <T> data type
 * @author johnmcclean
 */
public class PushableFutureStream<T> extends AbstractPushableStream<T, Queue<T>, FutureStream<T>> {

    private static final long serialVersionUID = 1L;

    public PushableFutureStream(final Queue<T> v1,
                                final FutureStream<T> v2) {
        super(v1,
              v2);

    }

}

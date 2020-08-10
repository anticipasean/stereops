package cyclops.async.reactive.futurestream.pushable;

import cyclops.async.queue.Queue;
import cyclops.reactive.ReactiveSeq;

/**
 * A more concrete Tuple2 impl _1 is Queue&lt;T&gt; _2 is Seq&lt;T&gt;
 *
 * @param <T> data type
 * @author johnmcclean
 */
public class PushableReactiveSeq<T> extends AbstractPushableStream<T, Queue<T>, ReactiveSeq<T>> {

    private static final long serialVersionUID = 1L;

    public PushableReactiveSeq(final Queue<T> v1,
                               final ReactiveSeq<T> v2) {
        super(v1,
              v2);

    }

}

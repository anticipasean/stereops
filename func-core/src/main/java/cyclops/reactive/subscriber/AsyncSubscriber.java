package cyclops.reactive.subscriber;

import cyclops.reactive.ReactiveSeq;
import cyclops.reactive.companion.Spouts;
import cyclops.stream.spliterator.push.CapturingOperator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import lombok.AllArgsConstructor;
import lombok.val;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * A subscriber for Observable type Streams that avoid the overhead of applying backpressure. For backpressure aware event driven
 * Streams {@link Spouts#reactiveSubscriber}
 *
 * <pre>
 *    {@code
 *          PushSubscriber<Integer> sub = Spouts.asyncSubscriber();
 *
 *          //on a seperate thread
 *          for(int i=0;i<100;i++)
 * sub.onNext(i);
 *
 * sub.onComplete();
 *
 *          //on the main thread
 *
 *          sub.stream()
 *             .peek(System.out::println)
 *             .collect(CyclopsCollectors.toList());
 *
 *          //note JDK Stream based terminal operations may block the current thread
 *          //see ReactiveSeq#collectStream ReactiveSeq#foldAll for non-blocking alternatives
 *    }
 * </pre>
 *
 * @param <T> Subscriber type
 * @author johnmcclean
 */
@AllArgsConstructor//(access=AccessLevel.PRIVATE)
@Deprecated
public class AsyncSubscriber<T> implements Subscriber<T>, PushSubscriber<T> {


    volatile boolean isOpen;
    volatile boolean streamCreated = false;
    private final AtomicReference<CapturingOperator<T>> action = new AtomicReference<>(null);


    public AsyncSubscriber() {
    }

    CapturingOperator<T> getAction(Runnable onInit) {
        while (action.get() == null) {
            action.compareAndSet(null,
                                 new CapturingOperator<T>(onInit));
        }
        return action.get();
    }

    CapturingOperator<T> getAction() {
        while (action.get() == null) {
            action.compareAndSet(null,
                                 new CapturingOperator<T>());
        }
        return action.get();
    }

    /**
     * <pre>
     *    {@code
     *          PushSubscriber<Integer> sub = Spouts.asyncSubscriber();
     *
     *          //on a seperate thread
     *          for(int i=0;i<100;i++)
     * sub.onNext(i);
     *
     * sub.onComplete();
     *
     *          //on the main thread
     *
     *          sub.stream()
     *             .peek(System.out::println)
     *             .collect(CyclopsCollectors.toList());
     *
     *          //note JDK Stream based terminal operations may block the current thread
     *          //see ReactiveSeq#collectStream ReactiveSeq#foldAll for non-blocking alternatives
     *    }
     * </pre>
     *
     * @return A push-based asychronous event driven Observable-style Stream that avoids the overhead of backpressure support
     **/
    public ReactiveSeq<T> stream() {
        streamCreated = true;
        return Spouts.asyncStream(getAction(() -> {
        }));
    }

    public ReactiveSeq<T> registerAndstream(Runnable r) {
        streamCreated = true;

        return Spouts.asyncStream(getAction(r));
    }


    @Override
    public void onSubscribe(final Subscription s) {

    }

    @Override
    public void onNext(final T t) {

        val cons = getAction().getAction();
        //if (cons != null)
        cons.accept(t);


    }

    @Override
    public void onError(final Throwable t) {
        Objects.requireNonNull(t);
        val cons = getAction().getError();
        //  if(cons!=null)
        cons.accept(t);


    }

    @Override
    public void onComplete() {

        val run = getAction().getOnComplete();

        // if(run!=null)
        run.run();

    }

    public boolean isInitialized() {
        return getAction().isInitialized();
    }

    public void awaitInitialization() {
        while (!isInitialized()) {
            LockSupport.parkNanos(0l);
        }
    }

}

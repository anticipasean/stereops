package cyclops.stream.spliterator.push.rangeLong;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import cyclops.reactive.companion.Spouts;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by johnmcclean on 18/01/2017.
 */
public class RangeLongTest {

    Subscription sub;
    AtomicInteger count = new AtomicInteger();
    AtomicInteger error = new AtomicInteger();
    AtomicInteger complete = new AtomicInteger();

    @Test
    public void rangeLong() {
        Spouts.rangeLong(0,
                         3)
              .filter(i -> true)
              .subscribe(new Subscriber<Long>() {
                  @Override
                  public void onSubscribe(Subscription s) {
                      sub = s;
                  }

                  @Override
                  public void onNext(Long aLong) {
                      count.incrementAndGet();

                  }

                  @Override
                  public void onError(Throwable t) {
                      error.incrementAndGet();
                  }

                  @Override
                  public void onComplete() {
                      complete.incrementAndGet();
                  }
              });
        sub.request(Long.MAX_VALUE);
        assertThat(count.get(),
                   equalTo(3));
    }
}

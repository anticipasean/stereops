package cyclops.container.control.future;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import cyclops.util.SimpleTimer;
import cyclops.async.Future;
import org.junit.Test;

public class FutureOrElseValueTest {

    @Test
    public void recoverWith_switchesOnEmpty() {
        assertThat(Future.ofError(new Exception())
                         .recoverWith(() -> Future.ofResult(1))
                         .getOrElse(-1),
                   equalTo(1));
    }

    @Test
    public void recoverWith_doesntswitchesWhenNotEmpty() {
        assertThat(Future.ofResult(1)
                         .recoverWith(() -> Future.ofResult(2))
                         .getOrElse(-1),
                   equalTo(1));
    }

    @Test
    public void recoverWith_nonBlocking() throws InterruptedException {
        SimpleTimer timer = new SimpleTimer();
        Future.of(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException();
        })
              .recoverWith(() -> {
                  try {
                      Thread.sleep(100000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  return Future.ofResult(1);
              });
        Thread.sleep(100);
        assertThat(timer.getElapsedMillis(),
                   lessThan(1000L));
    }

}

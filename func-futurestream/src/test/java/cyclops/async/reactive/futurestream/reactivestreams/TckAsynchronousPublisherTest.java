package cyclops.async.reactive.futurestream.reactivestreams;


import cyclops.async.reactive.futurestream.LazyReact;
import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;
import org.testng.annotations.Test;

@Test
public class TckAsynchronousPublisherTest extends PublisherVerification<Long> {

    public TckAsynchronousPublisherTest() {
        super(new TestEnvironment(300L));
    }


    @Override
    public Publisher<Long> createPublisher(long elements) {
        return new LazyReact().generateAsync(() -> 100l)
                              .limit(elements);

    }

    @Override
    public Publisher<Long> createFailedPublisher() {
        return null; //not possible to forEachAsync to failed Stream

    }


}

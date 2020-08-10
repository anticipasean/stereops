package cyclops.stream.spliterator.push.filter;

import cyclops.container.persistent.impl.LazySeq;
import cyclops.reactive.companion.Spouts;
import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;
import org.testng.annotations.Test;

@Test
public class FilterIterableTckPublisherTest extends PublisherVerification<Long> {

    public FilterIterableTckPublisherTest() {
        super(new TestEnvironment(300L));
    }


    @Override
    public Publisher<Long> createPublisher(long elements) {
        return Spouts.fromIterable(LazySeq.fill(Math.min(elements,
                                                         10_000),
                                                10l))
                     .filter(i -> true);

    }

    @Override
    public Publisher<Long> createFailedPublisher() {
        return null; //not possible to forEachAsync to failed Stream

    }


}

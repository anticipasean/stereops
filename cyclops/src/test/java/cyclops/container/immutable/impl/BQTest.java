package cyclops.container.immutable.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.stream.Stream;
import org.junit.Test;

public class BQTest {

    @Test
    public void fromStream() {
        System.out.println(BankersQueue.of(1,
                                           2,
                                           3));
        System.out.println(BankersQueue.fromStream(Stream.of(1,
                                                             2,
                                                             3)));
        BankersQueue.fromStream(Stream.of(1,
                                          2,
                                          3))
                    .iterator();
        assertThat(BankersQueue.fromStream(Stream.of(1,
                                                     2,
                                                     3)),
                   equalTo(BankersQueue.of(1,
                                           2,
                                           3)));
    }
}

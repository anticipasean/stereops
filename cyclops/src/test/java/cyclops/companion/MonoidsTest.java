package cyclops.companion;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import cyclops.data.Chain;
import cyclops.function.Monoid;
import java.util.Arrays;
import org.junit.Test;

public class MonoidsTest {

    @Test
    public void testChainConcat() {
        Chain<Integer> list = Chain.empty();
        list = list.plus(1);
        list = list.plus(2);
        list = list.plus(4);
        Monoid<Chain<Integer>> combiner = Monoids.chainConcat();
        assertThat(combiner.apply(list,
                                  Chain.of(4,
                                           5,
                                           6))
                           .toList(),
                   equalTo(Arrays.asList(1,
                                         2,
                                         4,
                                         4,
                                         5,
                                         6)));
    }

    @Test
    public void testChainConcatIdentity() {
        Chain<Integer> list = Chain.empty();
        list = list.plus(1);
        list = list.plus(2);
        list = list.plus(4);
        Monoid<Chain<Integer>> combiner = Monoids.chainConcat();
        assertThat(combiner.zero(),
                   equalTo(Chain.empty()));
    }
}

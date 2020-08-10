package cyclops.streams.push;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.oath.cyclops.streams.BaseSequentialTest;
import cyclops.container.tuple.Tuple2;
import cyclops.reactive.ReactiveSeq;
import cyclops.reactive.companion.Spouts;
import java.util.Arrays;
import org.junit.Test;

/**
 * Created by johnmcclean on 14/01/2017.
 */
public class PushSequentialTest extends BaseSequentialTest {

    @Override
    protected <U> ReactiveSeq<U> of(U... array) {

        return Spouts.of(array);
    }

    @Test
    public void duplicateReplay() {
        final Tuple2<ReactiveSeq<Integer>, ReactiveSeq<Integer>> t = of(1).duplicate();
        assertThat(t._1()
                    .limit(1)
                    .toList(),
                   equalTo(Arrays.asList(1)));
        assertThat(t._1()
                    .limit(1)
                    .toList(),
                   equalTo(Arrays.asList(1)));
    }

    @Test
    public void iterate() {
        Spouts.iterate(1,
                       i -> i + 1)
              .limit(10)
              .forEach(System.out::println);
    }

}


package cyclops.container.traversable;

import cyclops.container.immutable.tuple.Tuple;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.reactive.ReactiveSeq;
import org.junit.Test;

public class ForPublishersTest {


    @Test
    public void publishers() {

        // import static com.oath.cyclops.container.control.For.*;

        ReactiveSeq<Tuple2<Integer, Integer>> stream = ReactiveSeq.of(1,
                                                                      2,
                                                                      3)
                                                                  .forEach2(i -> ReactiveSeq.range(i,
                                                                                                   5),
                                                                            Tuple::tuple)
                                                                  .stream();

        stream.printOut();
        /*
           (1, 1)
           (1, 2)
           (1, 3)
           (1, 4)
           (2, 2)
           (2, 3)
           (2, 4)
           (3, 3)
           (3, 4)

         */

    }


}

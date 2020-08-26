package cyclops.reactive.collection.container.immutable;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import cyclops.container.immutable.impl.Vector;
import cyclops.function.companion.Reducers;
import cyclops.reactive.ReactiveSeq;
import java.util.Arrays;
import org.junit.Test;

public class PVectorsTest {

    @Test
    public void testOf() {
        assertThat(VectorX.of("a",
                              "b",
                              "c"),
                   equalTo(Arrays.asList("a",
                                         "b",
                                         "c")));
    }

    @Test
    public void testEmpty() {
        assertThat(VectorX.empty(),
                   equalTo(Arrays.asList()));
    }

    @Test
    public void testSingleton() {
        assertThat(VectorX.of("a"),
                   equalTo(Arrays.asList("a")));
    }

    @Test
    public void testFromCollection() {
        assertThat(VectorX.fromIterable(Arrays.asList("a",
                                                      "b",
                                                      "c")),
                   equalTo(Arrays.asList("a",
                                         "b",
                                         "c")));
    }

    @Test
    public void testToPVectorStreamOfT() {
        assertThat(VectorX.vectorX(ReactiveSeq.of("a",
                                                  "b",
                                                  "c")),
                   equalTo(Arrays.asList("a",
                                         "b",
                                         "c")));
    }

    @Test
    public void testToPVector() {
        assertThat(ReactiveSeq.of("a",
                                  "b",
                                  "c")
                              .foldMap(Reducers.toPersistentVector()),
                   equalTo(Vector.of("a",
                                     "b",
                                     "c")));
    }

}

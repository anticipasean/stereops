package cyclops.container.immutable.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import cyclops.function.higherkinded.DataWitness.reactiveSeq;
import cyclops.function.higherkinded.DataWitness.seq;
import cyclops.function.higherkinded.DataWitness.vector;
import cyclops.function.higherkinded.Higher;
import cyclops.function.higherkinded.NaturalTransformation;
import cyclops.reactive.ReactiveSeq;
import org.junit.Test;


public class NaturalTransformationTest {

    NaturalTransformation<reactiveSeq, seq> streamToList = new NaturalTransformation<reactiveSeq, seq>() {
        @Override
        public <T> Higher<seq, T> apply(Higher<reactiveSeq, T> a) {
            return a.convert(ReactiveSeq::narrowK)
                    .seq();
        }
    };
    NaturalTransformation<seq, vector> listToVector = new NaturalTransformation<seq, vector>() {
        @Override
        public <T> Higher<vector, T> apply(Higher<seq, T> a) {
            return a.convert(Seq::narrowK)
                    .to()
                    .vector();
        }
    };

    @Test
    public void streamToList() {
        assertThat(streamToList.apply(ReactiveSeq.of(1,
                                                     2,
                                                     3)),
                   equalTo(Vector.of(1,
                                     2,
                                     3)));
    }

    @Test
    public void streamToListAndThenToVectorX() {
        assertThat(streamToList.andThen(listToVector)
                               .apply(ReactiveSeq.of(1,
                                                     2,
                                                     3)),
                   equalTo(Vector.of(1,
                                     2,
                                     3)));
    }

    @Test
    public void compose() {
        assertThat(listToVector.compose(streamToList)
                               .apply(ReactiveSeq.of(1,
                                                     2,
                                                     3)),
                   equalTo(Vector.of(1,
                                     2,
                                     3)));
    }
}

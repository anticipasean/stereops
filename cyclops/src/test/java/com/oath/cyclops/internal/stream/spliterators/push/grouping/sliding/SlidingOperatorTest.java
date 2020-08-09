package com.oath.cyclops.internal.stream.spliterators.push.grouping.sliding;

import com.oath.cyclops.internal.stream.spliterators.push.AbstractOperatorTest;
import com.oath.cyclops.internal.stream.spliterators.push.ArrayOfValuesOperator;
import com.oath.cyclops.internal.stream.spliterators.push.Fixtures;
import com.oath.cyclops.internal.stream.spliterators.push.IterableSourceOperator;
import com.oath.cyclops.internal.stream.spliterators.push.MapOperator;
import com.oath.cyclops.internal.stream.spliterators.push.Operator;
import com.oath.cyclops.internal.stream.spliterators.push.SlidingOperator;
import cyclops.data.Seq;
import java.util.Arrays;
import java.util.List;

/**
 * Created by johnmcclean on 17/01/2017.
 */
public class SlidingOperatorTest extends AbstractOperatorTest {


    public Operator<Integer> createEmpty() {
        return new MapOperator<Seq<Integer>, Integer>(new SlidingOperator(new ArrayOfValuesOperator<>(),
                                                                          i -> i,
                                                                          1,
                                                                          1),
                                                      i -> i.getOrElse(0,
                                                                       -1));
    }

    public Operator<Integer> createOne() {
        return new MapOperator<Seq<Integer>, Integer>(new SlidingOperator(new ArrayOfValuesOperator<>(1),
                                                                          i -> i,
                                                                          1,
                                                                          1),
                                                      i -> i.getOrElse(0,
                                                                       -1));

    }

    public Operator<Integer> createThree() {
        List<Integer> list = Arrays.asList(1,
                                           2,
                                           3,
                                           4,
                                           5,
                                           6,
                                           7,
                                           8,
                                           9);
        return new MapOperator<Seq<Integer>, Integer>(new SlidingOperator(new IterableSourceOperator(list),
                                                                          i -> i,
                                                                          3,
                                                                          3),
                                                      i -> i.getOrElse(0,
                                                                       -1));
    }

    public Operator<Integer> createTwoAndError() {

        return new MapOperator<Seq<Integer>, Integer>(new SlidingOperator(Fixtures.twoAndErrorSource,
                                                                          i -> i,
                                                                          1,
                                                                          1),
                                                      i -> i.getOrElse(0,
                                                                       -1));
    }

    public Operator<Integer> createThreeErrors() {
        return new MapOperator<Seq<Integer>, Integer>(new SlidingOperator(Fixtures.threeErrorsSource,
                                                                          i -> i,
                                                                          1,
                                                                          1),
                                                      i -> i.getOrElse(0,
                                                                       -1));
    }


}

package cyclops.stream.spliterator.push.grouping;

import cyclops.stream.spliterator.push.AbstractOperatorTest;
import cyclops.stream.spliterator.push.ArrayOfValuesOperator;
import cyclops.stream.spliterator.push.Fixtures;
import cyclops.stream.spliterator.push.GroupingOperator;
import cyclops.stream.spliterator.push.IterableSourceOperator;
import cyclops.stream.spliterator.push.MapOperator;
import cyclops.stream.spliterator.push.Operator;
import cyclops.container.immutable.impl.Vector;
import java.util.Arrays;
import java.util.List;

/**
 * Created by johnmcclean on 17/01/2017.
 */
public class GroupedOperatorTest extends AbstractOperatorTest {


    public Operator<Integer> createEmpty() {
        return new MapOperator<Vector<Integer>, Integer>(new GroupingOperator(new ArrayOfValuesOperator<>(),
                                                                              () -> Vector.empty(),
                                                                              i -> i,
                                                                              1),
                                                         i -> i.getOrElse(0,
                                                                          -1));
    }

    public Operator<Integer> createOne() {
        return new MapOperator<Vector<Integer>, Integer>(new GroupingOperator(new ArrayOfValuesOperator<>(1),
                                                                              () -> Vector.empty(),
                                                                              i -> i,
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
        return new MapOperator<Vector<Integer>, Integer>(new GroupingOperator(new IterableSourceOperator(list),
                                                                              () -> Vector.empty(),
                                                                              i -> i,
                                                                              3),
                                                         i -> i.getOrElse(0,
                                                                          -1));
    }

    public Operator<Integer> createTwoAndError() {

        return new MapOperator<Vector<Integer>, Integer>(new GroupingOperator(Fixtures.twoAndErrorSource,
                                                                              () -> Vector.empty(),
                                                                              i -> i,
                                                                              1),
                                                         i -> i.getOrElse(0,
                                                                          -1));
    }

    public Operator<Integer> createThreeErrors() {
        return new MapOperator<Vector<Integer>, Integer>(new GroupingOperator(Fixtures.threeErrorsSource,
                                                                              () -> Vector.empty(),
                                                                              i -> i,
                                                                              1),
                                                         i -> i.getOrElse(0,
                                                                          -1));
    }


}

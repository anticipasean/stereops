package cyclops.stream.spliterator.push.grouping.groupedTimeAndSize;

import cyclops.stream.spliterator.push.AbstractOperatorTest;
import cyclops.stream.spliterator.push.ArrayOfValuesOperator;
import cyclops.stream.spliterator.push.Fixtures;
import cyclops.stream.spliterator.push.GroupedByTimeAndSizeOperator;
import cyclops.stream.spliterator.push.IterableSourceOperator;
import cyclops.stream.spliterator.push.MapOperator;
import cyclops.stream.spliterator.push.Operator;
import cyclops.container.persistent.impl.Vector;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by johnmcclean on 17/01/2017.
 */
public class GroupedByTimeAndSizeOperatorTest extends AbstractOperatorTest {


    public Operator<Integer> createEmpty() {
        return new MapOperator<Vector<Integer>, Integer>(new GroupedByTimeAndSizeOperator(new ArrayOfValuesOperator<>(),
                                                                                          () -> Vector.empty(),
                                                                                          i -> i,
                                                                                          1,
                                                                                          TimeUnit.SECONDS,
                                                                                          2),
                                                         i -> i.getOrElse(0,
                                                                          -1));
    }

    public Operator<Integer> createOne() {
        return new MapOperator<Vector<Integer>, Integer>(new GroupedByTimeAndSizeOperator(new ArrayOfValuesOperator<>(1),
                                                                                          () -> Vector.empty(),
                                                                                          i -> i,
                                                                                          1,
                                                                                          TimeUnit.SECONDS,
                                                                                          2),
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
        return new MapOperator<Vector<Integer>, Integer>(new GroupedByTimeAndSizeOperator(new IterableSourceOperator(list),
                                                                                          () -> Vector.empty(),
                                                                                          i -> i,
                                                                                          3,
                                                                                          TimeUnit.SECONDS,
                                                                                          3),
                                                         i -> i.getOrElse(0,
                                                                          -1));
    }

    public Operator<Integer> createTwoAndError() {

        return new MapOperator<Vector<Integer>, Integer>(new GroupedByTimeAndSizeOperator(Fixtures.twoAndErrorSource,
                                                                                          () -> Vector.empty(),
                                                                                          i -> i,
                                                                                          1,
                                                                                          TimeUnit.SECONDS,
                                                                                          1),
                                                         i -> i.getOrElse(0,
                                                                          -1));
    }

    public Operator<Integer> createThreeErrors() {
        return new MapOperator<Vector<Integer>, Integer>(new GroupedByTimeAndSizeOperator(Fixtures.threeErrorsSource,
                                                                                          () -> Vector.empty(),
                                                                                          i -> i,
                                                                                          1,
                                                                                          TimeUnit.SECONDS,
                                                                                          6),
                                                         i -> i.getOrElse(0,
                                                                          -1));
    }


}

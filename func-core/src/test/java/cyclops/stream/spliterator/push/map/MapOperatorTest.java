package cyclops.stream.spliterator.push.map;

import cyclops.stream.spliterator.push.AbstractOperatorTest;
import cyclops.stream.spliterator.push.ArrayOfValuesOperator;
import cyclops.stream.spliterator.push.Fixtures;
import cyclops.stream.spliterator.push.MapOperator;
import cyclops.stream.spliterator.push.Operator;
import cyclops.stream.spliterator.push.SingleValueOperator;

/**
 * Created by johnmcclean on 17/01/2017.
 */
public class MapOperatorTest extends AbstractOperatorTest {


    public Operator<Integer> createEmpty() {
        return new MapOperator<Integer, Integer>(new ArrayOfValuesOperator<>(),
                                                 i -> i * 2);
    }

    public Operator<Integer> createOne() {
        return new MapOperator<Integer, Integer>(new SingleValueOperator<>(1),
                                                 i -> i * 2);
    }

    public Operator<Integer> createThree() {
        return new MapOperator<Integer, Integer>(new ArrayOfValuesOperator<>(1,
                                                                             2,
                                                                             3),
                                                 i -> i * 2);
    }

    public Operator<Integer> createTwoAndError() {
        return new MapOperator<Integer, Integer>(Fixtures.twoAndErrorSource,
                                                 i -> i * 2);
    }

    public Operator<Integer> createThreeErrors() {
        return new MapOperator<Integer, Integer>(Fixtures.threeErrorsSource,
                                                 i -> i * 2);
    }


}

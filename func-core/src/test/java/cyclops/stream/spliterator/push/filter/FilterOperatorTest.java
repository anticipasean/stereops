package cyclops.stream.spliterator.push.filter;

import cyclops.stream.spliterator.push.AbstractOperatorTest;
import cyclops.stream.spliterator.push.ArrayOfValuesOperator;
import cyclops.stream.spliterator.push.FilterOperator;
import cyclops.stream.spliterator.push.Fixtures;
import cyclops.stream.spliterator.push.Operator;
import cyclops.stream.spliterator.push.SingleValueOperator;

/**
 * Created by johnmcclean on 17/01/2017.
 */
public class FilterOperatorTest extends AbstractOperatorTest {


    public Operator<Integer> createEmpty() {
        return new FilterOperator<>(new ArrayOfValuesOperator<>(),
                                    i -> true);
    }

    public Operator<Integer> createOne() {
        return new FilterOperator<>(new SingleValueOperator<>(1),
                                    i -> true);
    }

    public Operator<Integer> createThree() {
        return new FilterOperator<>(new ArrayOfValuesOperator<>(1,
                                                                2,
                                                                3),
                                    i -> true);
    }

    public Operator<Integer> createTwoAndError() {
        return new FilterOperator<>(Fixtures.twoAndErrorSource,
                                    i -> true);
    }

    public Operator<Integer> createThreeErrors() {
        return new FilterOperator<>(Fixtures.threeErrorsSource,
                                    i -> true);
    }


}

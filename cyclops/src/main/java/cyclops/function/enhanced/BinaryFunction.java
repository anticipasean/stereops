package cyclops.function.enhanced;

import java.util.function.BinaryOperator;


public interface BinaryFunction<T> extends BinaryOperator<T>, Function2<T, T, T> {

}

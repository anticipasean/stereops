package cyclops.function.enhanced;

import java.util.function.UnaryOperator;


public interface UnaryFunction<T> extends UnaryOperator<T>, Function1<T, T> {

}

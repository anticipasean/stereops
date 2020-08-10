package cyclops.stream.pushable;

import cyclops.async.adapters.Adapter;
import cyclops.container.tuple.Tuple2;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class AbstractPushableStream<T, X extends Adapter<T>, R extends Stream<T>> extends Tuple2<X, R> {

    private static final long serialVersionUID = 1L;

    public AbstractPushableStream(final X v1,
                                  final R v2) {
        super(v1,
              v2);
    }

    public X getInput() {
        return _1();
    }

    public R getStream() {
        return _2();
    }

    public <U> U fold(final BiFunction<? super X, ? super R, ? extends U> visitor) {
        return visitor.apply(_1(),
                             _2());
    }

    public void peekStream(final Consumer<? super R> consumer) {
        consumer.accept(_2());
    }

    public void peekInput(final Consumer<? super X> consumer) {
        consumer.accept(_1());
    }

}

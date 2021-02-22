package cyclops.container.persistent;

import cyclops.container.control.eager.option.Option;
import java.util.function.Supplier;

public interface PersistentIndexed<T> extends PersistentCollection<T> {

    Option<T> get(int index);

    T getOrElse(int index,
                T alt);

    T getOrElseGet(int index,
                   Supplier<? extends T> alt);


}

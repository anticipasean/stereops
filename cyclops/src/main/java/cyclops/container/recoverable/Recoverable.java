package cyclops.container.recoverable;


import java.util.function.Supplier;

public interface Recoverable<U> {

    Recoverable<U> recover(Supplier<? extends U> value);


}

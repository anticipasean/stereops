package cyclops.typeclasses.taglessfinal;

import cyclops.function.higherkinded.Higher;

public interface LogAlgebra<W> {

    Higher<W,Void> info(String message);
    Higher<W, Void> error(String message);

}

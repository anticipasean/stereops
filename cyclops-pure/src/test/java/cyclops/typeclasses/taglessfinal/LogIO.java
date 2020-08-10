package cyclops.typeclasses.taglessfinal;

import cyclops.function.hkt.DataWitness.io;
import cyclops.function.hkt.Higher;
import cyclops.reactive.IO;

public class LogIO implements LogAlgebra<io> {


    @Override
    public Higher<io, Void> info(String message) {
        return IO.of(null);
    }

    @Override
    public Higher<io, Void> error(String message) {
        return IO.of(null);
    }
}

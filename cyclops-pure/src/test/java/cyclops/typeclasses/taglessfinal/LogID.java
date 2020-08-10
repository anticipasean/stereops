package cyclops.typeclasses.taglessfinal;


import cyclops.function.hkt.DataWitness.identity;
import cyclops.function.hkt.Higher;
import cyclops.control.Identity;

public class LogID implements LogAlgebra<identity> {


    @Override
    public Higher<identity, Void> info(String message) {
        return Identity.of(null);
    }

    @Override
    public Higher<identity, Void> error(String message) {
        return Identity.of(null);
    }
}

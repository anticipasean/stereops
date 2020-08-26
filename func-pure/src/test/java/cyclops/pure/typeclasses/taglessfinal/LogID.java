package cyclops.pure.typeclasses.taglessfinal;


import cyclops.function.higherkinded.DataWitness.identity;
import cyclops.function.higherkinded.Higher;
import cyclops.pure.control.Identity;

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

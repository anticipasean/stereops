package cyclops.pure.typeclasses.taglessfinal;

import cyclops.function.higherkinded.DataWitness.io;
import cyclops.pure.instances.reactive.IOInstances;
import cyclops.pure.typeclasses.Do;
import cyclops.pure.typeclasses.taglessfinal.Cases.Account;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountIO2 implements AccountAlgebra2<io> {
    StoreIO<Long, Account> storeIO;
    @Override
    public StoreAlgebra<io, Long, Account> store() {
        return storeIO;
    }

    @Override
    public Do<io> forEach() {
        return Do.forEach(IOInstances::monad);
    }
}

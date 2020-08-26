package cyclops.pure.typeclasses.taglessfinal;

import cyclops.function.higherkinded.Higher;
import cyclops.pure.typeclasses.taglessfinal.Cases.Account;

public interface AccountAlgebra<W> {

    Higher<W,Account> debit(Account account, double amount);
    Higher<W, Account> credit(Account account, double amount);

}

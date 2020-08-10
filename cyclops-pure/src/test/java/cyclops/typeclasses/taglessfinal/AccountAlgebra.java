package cyclops.typeclasses.taglessfinal;

import cyclops.function.hkt.Higher;
import cyclops.typeclasses.taglessfinal.Cases.Account;

public interface AccountAlgebra<W> {

    Higher<W,Account> debit(Account account, double amount);
    Higher<W, Account> credit(Account account, double amount);

}

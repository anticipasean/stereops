package cyclops.pure.typeclasses.taglessfinal;

import cyclops.function.higherkinded.Higher;
import cyclops.container.control.eager.option.Option;
import cyclops.container.immutable.tuple.Tuple;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.pure.typeclasses.Do;
import cyclops.pure.typeclasses.monad.Monad;
import cyclops.pure.typeclasses.taglessfinal.Cases.Account;
import lombok.AllArgsConstructor;

import java.util.function.Function;

import static cyclops.function.enhanced.Function2._1;
import static cyclops.function.enhanced.Function3.__23;

@AllArgsConstructor
public class ProgramStore<W> {

    private final Monad<W> monad;
    private final AccountAlgebra2<W> accountService;
    private final Account to;
    private final Account from;

    public <R> R transfer(double amount, Function<Higher<W, Tuple2<Option<Account>,Option<Account>>>,R> fn){

        return Do.forEach(monad)
                 ._of(amount)
                 .__(this::debit)
                 .__(_1(this::credit))
                 .yield(__23(Tuple::tuple))
                 .fold(fn);
    }

    private Higher<W,Option<Account>> debit(double amount){
        return accountService.debit(from, amount);
    }
    private Higher<W,Option<Account>> credit(double amount){
        return accountService.credit(to,amount);
    }
}

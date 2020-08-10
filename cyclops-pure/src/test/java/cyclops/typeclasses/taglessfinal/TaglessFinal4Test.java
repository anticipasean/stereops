package cyclops.typeclasses.taglessfinal;

import cyclops.function.higherkinded.DataWitness.io;
import cyclops.container.control.Option;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.instances.reactive.IOInstances;
import cyclops.reactive.IO;
import org.junit.Before;
import org.junit.Test;

import static cyclops.typeclasses.taglessfinal.Cases.Account;

public class TaglessFinal4Test {

    private Account acc1;
    private Account acc2;
    ProgramStore<io> prog;
    


    @Before
    public void setup(){
        
        acc1 = new Account(10000d,10);
        acc2 = new Account(0d,11);
        StoreIO<Long,Account> store = new StoreIO<>();
        store.put(acc1.getId(),acc1);
        store.put(acc2.getId(),acc2);
        prog = new ProgramStore<>(IOInstances.monad(),new AccountIO2(store),acc2,acc1);
    }


    @Test
    public void programB(){

        IO<Tuple2<Option<Account>, Option<Account>>> res = prog.transfer(100, IO::narrowK);
        res.run().peek(System.out::println);
    }
}

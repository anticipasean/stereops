package cyclops.pure.typeclasses.taglessfinal;

import cyclops.function.higherkinded.DataWitness.io;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.pure.instances.reactive.IOInstances;
import cyclops.reactive.IO;
import org.junit.Before;
import org.junit.Test;
import static cyclops.pure.typeclasses.taglessfinal.Cases.*;

public class TaglessFinalTest {

    private Account acc1;
    private Account acc2;
    Program<io> prog;
    @Before
    public void setup(){
        acc1 = new Account(10000d,10);
        acc2 = new Account(0d,11);
        prog = new Program<>(IOInstances.monad(),new AccountIO(),acc1,acc2);
    }

    @Test
    public void programA(){

        IO<Tuple2<Account, Account>> res = prog.transfer(100)
                                                .convert(IO::narrowK);
        res.run().peek(System.out::println);
    }
    @Test
    public void programB(){

        IO<Tuple2<Account, Account>> res = prog.transfer(100,IO::narrowK);
        res.run().peek(System.out::println);
    }
}

package cyclops.free;

import cyclops.function.higherkinded.Higher;
import cyclops.container.control.Identity;
import cyclops.function.higherkinded.DataWitness.identity;
import org.junit.Test;


import static cyclops.instances.control.IdentityInstances.functor;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by johnmcclean on 04/07/2017.
 */
public class CofreeTest {

    int i = 0;
    @Test
    public void unfold(){
        Cofree<identity, Integer>  unfolding = Cofree.unfold(functor(), i, next->{ i++; return Identity.of(next + 1);});
        assertThat(i,equalTo(0));
        Higher<identity, Cofree<identity, Integer>> next = unfolding.tailForced();
        assertThat(i,equalTo(1));
        Identity<Cofree<identity, Integer>> cof = Identity.narrowK(next);
        cof.map(c->c.tailForced());
        assertThat(i,equalTo(2));
    }

}

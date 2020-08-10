package cyclops.function;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import cyclops.function.companion.Monoids;
import org.junit.Test;


public class MonoidTest {

    @Test
    public void visit() throws Exception {

        int res = Monoids.intSum.fold((fn, z) -> {
            if (z == 0) {
                return fn.apply(1,
                                2);
            } else {
                return fn.apply(10,
                                20);
            }
        });

        assertThat(res,
                   equalTo(3));


    }

}

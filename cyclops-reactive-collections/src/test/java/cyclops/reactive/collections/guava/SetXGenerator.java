package cyclops.reactive.collections.guava;

import com.google.common.collect.testing.TestStringSetGenerator;
import cyclops.reactive.collections.mutable.SetX;
import java.util.Set;

public class SetXGenerator extends TestStringSetGenerator {


    @Override
    public Set<String> create(String... elements) {
        return SetX.of(elements);
    }

}

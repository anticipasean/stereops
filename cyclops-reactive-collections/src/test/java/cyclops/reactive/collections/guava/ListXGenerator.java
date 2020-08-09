package cyclops.reactive.collections.guava;

import com.google.common.collect.testing.TestStringListGenerator;
import cyclops.reactive.collections.mutable.ListX;
import java.util.List;

public class ListXGenerator extends TestStringListGenerator {


    @Override
    public List<String> create(String... elements) {
        return ListX.of(elements);
    }

}

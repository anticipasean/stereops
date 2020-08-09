package cyclops.reactive.collections.guava;

import com.google.common.collect.testing.TestStringSetGenerator;
import cyclops.reactive.collections.mutable.SortedSetX;
import java.util.Set;

public class SortedSetXGenerator extends TestStringSetGenerator {


    @Override
    public Set<String> create(String... elements) {
        return SortedSetX.of(elements);
    }

}

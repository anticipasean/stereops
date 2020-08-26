package cyclops.reactive.collection.container.mutable;

import com.google.common.collect.testing.TestStringSetGenerator;
import java.util.Set;

public class SetXGenerator extends TestStringSetGenerator {


    @Override
    public Set<String> create(String... elements) {
        return SetX.of(elements);
    }

}

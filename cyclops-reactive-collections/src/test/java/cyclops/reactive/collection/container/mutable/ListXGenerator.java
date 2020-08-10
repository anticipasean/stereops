package cyclops.reactive.collection.container.mutable;

import com.google.common.collect.testing.TestStringListGenerator;
import java.util.List;

public class ListXGenerator extends TestStringListGenerator {


    @Override
    public List<String> create(String... elements) {
        return ListX.of(elements);
    }

}

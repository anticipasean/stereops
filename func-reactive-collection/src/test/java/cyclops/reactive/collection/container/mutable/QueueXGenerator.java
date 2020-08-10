package cyclops.reactive.collection.container.mutable;

import com.google.common.collect.testing.TestStringQueueGenerator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class QueueXGenerator extends TestStringQueueGenerator {


    @Override
    public Queue<String> create(String... elements) {
        return QueueX.of(elements)
                     .type(Collectors.toCollection(() -> new LinkedList<>()));
    }

}

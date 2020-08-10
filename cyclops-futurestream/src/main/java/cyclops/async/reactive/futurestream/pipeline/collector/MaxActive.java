package cyclops.async.reactive.futurestream.pipeline.collector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;

@AllArgsConstructor
@Getter
@lombok.With
@Builder
public class MaxActive {

    public static final MaxActive IO = new MaxActive(100,
                                                     90);
    public static final MaxActive CPU = new MaxActive(Runtime.getRuntime()
                                                             .availableProcessors(),
                                                      Runtime.getRuntime()
                                                             .availableProcessors() - 1);
    public static final MaxActive SEQUENTIAL = new MaxActive(10,
                                                             1);
    private final int maxActive;
    private final int reduceTo;

}

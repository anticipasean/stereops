package cyclops.async.reactive.futurestream.threading;

import cyclops.async.reactive.futurestream.LazyReact;
import cyclops.async.reactive.futurestream.SimpleReact;
import java.util.concurrent.ForkJoinPool;

/**
 * A ReactPool of each type for parallel Streams Thread pool will be sized to number of processors
 *
 * @author johnmcclean
 */
public class ParallelElasticPools {

    public final static ReactPool<SimpleReact> simpleReact = ReactPool.elasticPool(() -> new SimpleReact(new ForkJoinPool(Runtime.getRuntime()
                                                                                                                                 .availableProcessors())));
    public final static ReactPool<LazyReact> lazyReact = ReactPool.elasticPool(() -> new LazyReact(new ForkJoinPool(Runtime.getRuntime()
                                                                                                                           .availableProcessors())));
}

package cyclops.async.reactive.futurestream.threading;

import cyclops.async.reactive.futurestream.LazyReact;
import cyclops.async.reactive.futurestream.SimpleReact;
import java.util.concurrent.Executors;

/**
 * A ReactPool of each type for sequential Streams
 *
 * @author johnmcclean
 */
public class SequentialElasticPools {

    public final static ReactPool<SimpleReact> simpleReact = ReactPool.elasticPool(() -> new SimpleReact(Executors.newFixedThreadPool(1)));
    public final static ReactPool<LazyReact> lazyReact = ReactPool.elasticPool(() -> new LazyReact(Executors.newFixedThreadPool(1)));
}

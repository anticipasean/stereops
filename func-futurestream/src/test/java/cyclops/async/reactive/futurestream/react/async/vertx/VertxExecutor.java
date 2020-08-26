package cyclops.async.reactive.futurestream.react.async.vertx;

import io.vertx.core.Vertx;
import java.util.concurrent.Executor;

/* vert.x 3 dependency:
    <dependency>
			<groupId>io.vertx</groupId>
			<artifactId>vertx-core</artifactId>
			<version>3.0.0</version>
		</dependency>
*/

public class VertxExecutor implements Executor {

    protected final Vertx vertx;

    public VertxExecutor(Vertx vertx) {
        super();
        this.vertx = vertx;
    }

    @Override
    public void execute(Runnable command) {
        vertx.runOnContext(v -> command.run()); // event loop, non-blocking
        // vertx.executeBlocking(v -> command.run(), null); // thread pool, blocking
    }
}

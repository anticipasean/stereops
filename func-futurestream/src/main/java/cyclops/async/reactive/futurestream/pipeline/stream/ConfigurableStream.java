package cyclops.async.reactive.futurestream.pipeline.stream;

import cyclops.async.queue.QueueFactory;
import cyclops.reactive.subscription.Continueable;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;


public interface ConfigurableStream<T, C> {

    ConfigurableStream<T, C> withTaskExecutor(Executor e);


    ConfigurableStream<T, C> withQueueFactory(QueueFactory<T> queue);

    ConfigurableStream<T, C> withErrorHandler(Optional<Consumer<Throwable>> errorHandler);

    ConfigurableStream<T, C> withSubscription(Continueable sub);

    ConfigurableStream<T, C> withAsync(boolean b);

    abstract Executor getTaskExecutor();


    ReactBuilder getSimpleReact();

    Optional<Consumer<Throwable>> getErrorHandler();

    boolean isAsync();

}

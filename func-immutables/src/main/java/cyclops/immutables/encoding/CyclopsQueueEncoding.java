package cyclops.immutables.encoding;

import cyclops.container.immutable.ImmutableQueue;
import cyclops.container.immutable.impl.BankersQueue;
import org.immutables.encode.Encoding;

@Encoding
class CyclopsQueueEncoding<T> {

    @Encoding.Impl
    private final ImmutableQueue<T> field = BankersQueue.empty();

    CyclopsQueueEncoding() {

    }

    @Encoding.Builder
    static final class Builder<T> {

        private ImmutableQueue<T> queue = BankersQueue.empty();

        Builder() {

        }

        @Encoding.Naming(value = "enqueue*", depluralize = true)
        @Encoding.Init
        void enqueue(final T element) {
            this.queue = this.queue.append(element);
        }


        @SafeVarargs
        @Encoding.Naming(value = "enqueue*", depluralize = true)
        @Encoding.Init
        final void enqueueVarArgs(final T... elements) {
            this.queue = this.queue.appendAll(elements);
        }

        @Encoding.Naming(value = "enqueueAll*", depluralize = true)
        @Encoding.Init
        void enqueueAll(final Iterable<T> element) {
            this.queue = this.queue.appendAll(element);
        }

        @Encoding.Init
        @Encoding.Copy
        void set(final ImmutableQueue<T> elements) {
            this.queue = elements;
        }

        @Encoding.Naming(value = "setIterable*")
        @Encoding.Init
        void setIterable(final Iterable<T> elements) {
            this.queue = BankersQueue.fromIterable(elements);
        }

        @Encoding.Build
        ImmutableQueue<T> build() {
            return this.queue;
        }
    }
}

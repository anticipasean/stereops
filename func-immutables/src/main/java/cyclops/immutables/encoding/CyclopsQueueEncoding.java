package cyclops.immutables.encoding;

import cyclops.container.immutable.impl.Chain;
import java.util.Arrays;
import org.immutables.encode.Encoding;

@Encoding
class CyclopsQueueEncoding<T> {

    @Encoding.Impl
    private final Chain<T> field = Chain.empty();

    CyclopsQueueEncoding() {

    }

    @Encoding.Builder
    static final class Builder<T> {

        private Chain<T> queue = Chain.empty();

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
            this.queue = this.queue.appendAll(Arrays.asList(elements));
        }

        @Encoding.Naming(value = "enqueueAll*", depluralize = true)
        @Encoding.Init
        void enqueueAll(final Iterable<T> element) {
            this.queue = this.queue.appendAll(element);
        }

        @Encoding.Init
        @Encoding.Copy
        void set(final Chain<T> elements) {
            this.queue = elements;
        }

        @Encoding.Naming(value = "setIterable*")
        @Encoding.Init
        void setIterable(final Iterable<T> elements) {
            this.queue = Chain.wrap(elements);
        }

        @Encoding.Build
        Chain<T> build() {
            return this.queue;
        }
    }
}

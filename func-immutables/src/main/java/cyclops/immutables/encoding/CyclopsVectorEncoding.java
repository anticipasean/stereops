package cyclops.immutables.encoding;

import cyclops.container.immutable.impl.Vector;
import java.util.Arrays;
import org.immutables.encode.Encoding;

@Encoding
class CyclopsVectorEncoding<T> {

    @Encoding.Impl
    private final Vector<T> field = Vector.empty();

    CyclopsVectorEncoding() {

    }

    @Encoding.Builder
    static final class Builder<T> {

        private Vector<T> vector = Vector.empty();

        Builder() {

        }

        @Encoding.Naming(standard = Encoding.StandardNaming.ADD)
        @Encoding.Init
        void add(final T element) {
            this.vector = this.vector.append(element);
        }

        @SafeVarargs
        @Encoding.Naming(standard = Encoding.StandardNaming.ADD)
        @Encoding.Init
        final void addVarArgs(final T... elements) {
            this.vector = this.vector.appendAll(Arrays.asList(elements));
        }

        @Encoding.Naming(standard = Encoding.StandardNaming.ADD_ALL)
        @Encoding.Init
        void addAll(final Iterable<T> element) {
            this.vector = this.vector.appendAll(element);
        }

        @Encoding.Init
        @Encoding.Copy
        void set(final Vector<T> elements) {
            this.vector = elements;
        }

        @Encoding.Naming(value = "setIterable*")
        @Encoding.Init
        void setIterable(final Iterable<T> elements) {
            this.vector = Vector.fromIterable(elements);
        }

        @Encoding.Build
        Vector<T> build() {
            return this.vector;
        }
    }
}

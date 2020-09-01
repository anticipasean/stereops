package cyclops.immutables.encoding;

import cyclops.container.immutable.ImmutableList;
import cyclops.container.immutable.impl.Vector;
import org.immutables.encode.Encoding;

@Encoding
class CyclopsListEncoding<T> {

    @Encoding.Impl
    private final ImmutableList<T> field = Vector.empty();

    CyclopsListEncoding() {

    }

    @Encoding.Builder
    static final class Builder<T> {

        private ImmutableList<T> list = Vector.empty();

        Builder() {

        }

        @Encoding.Naming(standard = Encoding.StandardNaming.ADD)
        @Encoding.Init
        void add(final T element) {
            this.list = this.list.append(element);
        }

        @SafeVarargs
        @Encoding.Naming(standard = Encoding.StandardNaming.ADD)
        @Encoding.Init
        final void addVarArgs(final T... elements) {
            this.list = this.list.appendAll(elements);
        }

        @Encoding.Naming(standard = Encoding.StandardNaming.ADD_ALL)
        @Encoding.Init
        void addAll(final Iterable<T> element) {
            this.list = this.list.appendAll(element);
        }

        @Encoding.Init
        @Encoding.Copy
        void set(final ImmutableList<T> elements) {
            this.list = elements;
        }

        @Encoding.Naming(value = "setIterable*")
        @Encoding.Init
        void setIterable(final Iterable<T> elements) {
            this.list = Vector.fromIterable(elements);
        }

        @Encoding.Build
        ImmutableList<T> build() {
            return this.list;
        }
    }
}

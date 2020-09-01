package cyclops.immutables.encoding;

import cyclops.container.immutable.ImmutableSet;
import cyclops.container.immutable.impl.HashSet;
import org.immutables.encode.Encoding;

@Encoding
class CyclopsSetEncoding<T> {

    // Using a linked variant provides more predictable semantics for serialization
    @Encoding.Impl
    private final ImmutableSet<T> field = HashSet.empty();

    CyclopsSetEncoding() {

    }

    @Encoding.Builder
    static final class Builder<T> {

        private ImmutableSet<T> set = HashSet.empty();

        Builder() {

        }

        @Encoding.Naming(standard = Encoding.StandardNaming.ADD)
        @Encoding.Init
        void add(final T element) {
            this.set = this.set.add(element);
        }

        @SafeVarargs
        @Encoding.Naming(standard = Encoding.StandardNaming.ADD)
        @Encoding.Init
        final void addVarArgs(final T... elements) {
            this.set = this.set.appendAll(elements);
        }

        @Encoding.Naming(standard = Encoding.StandardNaming.ADD_ALL)
        @Encoding.Init
        void addAll(final Iterable<T> element) {
            this.set = this.set.appendAll(element);
        }

        @Encoding.Init
        @Encoding.Copy
        void set(final ImmutableSet<T> elements) {
            this.set = elements;
        }

        @Encoding.Naming(value = "setIterable*")
        @Encoding.Init
        void setIterable(final Iterable<T> elements) {
            this.set = HashSet.fromIterable(elements);
        }

        @Encoding.Build
        ImmutableSet<T> build() {
            return this.set;
        }
    }
}

package cyclops.immutables.encoding;

import cyclops.container.immutable.impl.HashSet;
import org.immutables.encode.Encoding;

@Encoding
class CyclopsHashSetEncoding<T> {

    @Encoding.Impl
    private final HashSet<T> field = HashSet.empty();

    CyclopsHashSetEncoding() {

    }

    @Encoding.Builder
    static final class Builder<T> {

        private HashSet<T> set = HashSet.empty();

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
        void set(final HashSet<T> elements) {
            this.set = elements;
        }

        @Encoding.Naming(value = "setIterable*")
        @Encoding.Init
        void setIterable(final Iterable<T> elements) {
            this.set = HashSet.fromIterable(elements);
        }

        @Encoding.Build
        HashSet<T> build() {
            return this.set;
        }
    }
}

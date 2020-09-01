package cyclops.immutables.encoding;

import cyclops.container.immutable.impl.TreeSet;
import org.immutables.encode.Encoding;

@Encoding
class CyclopsTreeSetEncoding<T extends Comparable<T>> {

    @Encoding.Impl
    private final TreeSet<T> field = TreeSet.empty();

    CyclopsTreeSetEncoding() {

    }

    @Encoding.Builder
    static final class Builder<T extends Comparable<T>> {

        private TreeSet<T> set = TreeSet.empty(Comparable::compareTo);

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
        void set(final TreeSet<T> elements) {
            this.set = elements;
        }

        @Encoding.Naming(value = "setIterable*")
        @Encoding.Init
        void setIterable(final Iterable<T> elements) {
            this.set = TreeSet.fromIterable(elements,
                                            Comparable::compareTo);
        }

        @Encoding.Build
        TreeSet<T> build() {
            return this.set;
        }
    }
}

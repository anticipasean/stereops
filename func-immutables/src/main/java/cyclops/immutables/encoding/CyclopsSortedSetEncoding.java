package cyclops.immutables.encoding;

import cyclops.container.immutable.ImmutableSortedSet;
import cyclops.container.immutable.impl.TreeSet;
import org.immutables.encode.Encoding;

@Encoding
class CyclopsSortedSetEncoding<T extends Comparable<T>> {

    @Encoding.Impl
    private final ImmutableSortedSet<T> field = TreeSet.empty(Comparable::compareTo);

    CyclopsSortedSetEncoding() {

    }

    @Encoding.Builder
    static final class Builder<T extends Comparable<T>> {

        private ImmutableSortedSet<T> set = TreeSet.empty(Comparable::compareTo);

        Builder() {

        }

        @Encoding.Naming(standard = Encoding.StandardNaming.ADD)
        @Encoding.Init
        void add(final T element) {
            this.set = this.set.add(element);
        }

        @Encoding.Naming(standard = Encoding.StandardNaming.ADD_ALL)
        @Encoding.Init
        void addAll(final Iterable<T> element) {
            this.set = (ImmutableSortedSet<T>) this.set.appendAll(element);
        }

        @Encoding.Init
        @Encoding.Copy
        void set(final ImmutableSortedSet<T> elements) {
            this.set = elements;
        }

        @Encoding.Naming(value = "setIterable*")
        @Encoding.Init
        void setIterable(final Iterable<T> elements) {
            this.set = TreeSet.fromIterable(elements,
                                            Comparable::compareTo);
        }

        @Encoding.Build
        ImmutableSortedSet<T> build() {
            return this.set;
        }
    }
}

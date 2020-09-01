package cyclops.immutables.encoding;

import cyclops.container.immutable.impl.Seq;
import java.util.Arrays;
import org.immutables.encode.Encoding;

@Encoding
class CyclopsSeqEncoding<T> {

    @Encoding.Impl
    private final Seq<T> field = Seq.empty();

    CyclopsSeqEncoding() {

    }

    @Encoding.Builder
    static final class Builder<T> {

        private Seq<T> linear_seq = Seq.empty();

        Builder() {

        }

        @Encoding.Naming(standard = Encoding.StandardNaming.ADD)
        @Encoding.Init
        void add(final T element) {
            this.linear_seq = this.linear_seq.append(element);
        }

        @SafeVarargs
        @Encoding.Naming(standard = Encoding.StandardNaming.ADD)
        @Encoding.Init
        final void addVarArgs(final T... elements) {
            this.linear_seq = this.linear_seq.appendAll(Arrays.asList(elements));
        }

        @Encoding.Naming(standard = Encoding.StandardNaming.ADD_ALL)
        @Encoding.Init
        void addAll(final Iterable<T> element) {
            this.linear_seq = this.linear_seq.appendAll(element);
        }

        @Encoding.Init
        @Encoding.Copy
        void set(final Seq<T> elements) {
            this.linear_seq = elements;
        }

        @Encoding.Naming(value = "setIterable*")
        @Encoding.Init
        void setIterable(final Iterable<T> elements) {
            this.linear_seq = Seq.fromIterable(elements);
        }

        @Encoding.Build
        Seq<T> build() {
            return this.linear_seq;
        }
    }
}

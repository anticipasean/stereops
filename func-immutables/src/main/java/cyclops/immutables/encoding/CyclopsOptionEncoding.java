package cyclops.immutables.encoding;

import cyclops.container.control.eager.option.Option;
import java.util.Objects;
import org.immutables.encode.Encoding;

@Encoding
class CyclopsOptionEncoding<T> {

    @Encoding.Impl
    private final Option<T> field = Option.none();

    CyclopsOptionEncoding() {

    }

    @Encoding.Copy
    public Option<T> withOption(final Option<T> value) {
        return Objects.requireNonNull(value);
    }

    @Encoding.Copy
    public Option<T> with(final T value) {
        return Option.some(value);
    }

    @Encoding.Builder
    static final class Builder<T> {

        private Option<T> optional = Option.none();

        Builder() {

        }

        @Encoding.Init
        @Encoding.Copy
        void set(final Option<T> opt) {
            this.optional = opt;
        }

        @Encoding.Init
        void setValue(final T x) {
            this.optional = Option.of(x);
        }

        @Encoding.Naming(value = "unset*")
        @Encoding.Init
        void unset() {
            this.optional = Option.none();
        }

        @Encoding.Build
        Option<T> build() {
            return this.optional;
        }
    }
}

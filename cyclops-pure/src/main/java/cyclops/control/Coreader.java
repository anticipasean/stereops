package cyclops.control;

import cyclops.function.hkt.DataWitness.coreader;
import cyclops.function.hkt.Higher2;
import java.io.Serializable;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Coreader<R, T> implements Higher2<coreader, R, T>, Serializable {

    private static final long serialVersionUID = 1L;

    private final T extract;
    private final R ask;

    public static <R, T> Coreader<R, T> coreader(T extract,
                                                 R ask) {
        return new Coreader<>(extract,
                              ask);
    }

    public <B> Coreader<R, B> map(Function<? super T, ? extends B> fn) {
        return new Coreader<R, B>(fn.apply(extract),
                                  ask);
    }

    public Coreader<R, Coreader<R, T>> nest() {
        return new Coreader<>(this,
                              ask);
    }

    public <B> Coreader<R, B> coflatMap(Function<? super Coreader<R, T>, ? extends B> fn) {
        return nest().map(fn);
    }
}

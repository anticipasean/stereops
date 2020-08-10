package cyclops.container.tuple;

/**
 * Empty tuple type
 */

public final class Tuple0 {

    private Tuple0() {

    }

    static Tuple0 INSTANCE = new Tuple0();

    public static Tuple0 empty() {
        return INSTANCE;
    }

    public <T> Tuple1<T> add(T t) {
        return Tuple.tuple(t);
    }
}

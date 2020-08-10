package cyclops.typeclasses;


import cyclops.function.hkt.Higher;

public interface Show<W> {

    default <T> String show(Higher<W, T> ds) {
        return ds.toString();
    }
}

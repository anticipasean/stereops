package cyclops.typeclasses;


import cyclops.function.higherkinded.Higher;

public interface Show<W> {

    default <T> String show(Higher<W, T> ds) {
        return ds.toString();
    }
}

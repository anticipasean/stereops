package cyclops.container.foldable;


public interface Present<T> {

    T orElse(T alt);
}

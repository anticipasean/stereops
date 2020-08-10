package cyclops.container.foldable;


public interface Present<T> {

    public T orElse(T alt);
}

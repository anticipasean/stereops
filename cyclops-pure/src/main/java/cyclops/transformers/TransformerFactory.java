package cyclops.transformers;


import cyclops.function.higherkinded.Nested;

public interface TransformerFactory<W1, W2> {

    <T> Transformer<W1, W2, T> build(Nested<W1, W2, T> nested);
}

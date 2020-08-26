package cyclops.pure.transformers;


import cyclops.pure.container.functional.Nested;

public interface TransformerFactory<W1, W2> {

    <T> Transformer<W1, W2, T> build(Nested<W1, W2, T> nested);
}

package cyclops.function.evaluation;


public enum Evaluation {
    EAGER,
    LAZY;

    public void fold(Runnable eager,
                     Runnable lazy) {
        if (this == EAGER) {
            eager.run();
        }
        lazy.run();
    }
}

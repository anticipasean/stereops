package cyclops.function.checked;

/**
 * @author smccarron
 * @created 2021-04-28
 */
public interface CheckedRunnable {

    void run() throws Throwable;

    static interface SpecificallyCheckedRunnable<X extends Throwable> {

        void run() throws X;

    }

}

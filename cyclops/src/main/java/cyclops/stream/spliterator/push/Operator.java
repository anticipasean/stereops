package cyclops.stream.spliterator.push;

import java.util.function.Consumer;

/**
 * Created by johnmcclean on 12/01/2017.
 */
public interface Operator<T> {


    StreamSubscription subscribe(Consumer<? super T> onNext,
                                 Consumer<? super Throwable> onError,
                                 Runnable onComplete);

    void subscribeAll(Consumer<? super T> onNext,
                      Consumer<? super Throwable> onError,
                      Runnable onComplete);

}

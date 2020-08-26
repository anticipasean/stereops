package cyclops.stream.spliterator;

import java.util.Spliterator;

/**
 * Created by johnmcclean on 23/12/2016.
 */
public interface Composable<R> {

    Spliterator<R> compose();
}

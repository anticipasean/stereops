package cyclops.container.transformable;

import cyclops.container.filterable.Filterable;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a Transformable that is also Filters (e.g. a Stream or Optional type)
 *
 * @param <T> Data type of the element(s) in this FilterableTransformable
 * @author johnmcclean
 */
public interface FilterableTransformable<T> extends Filterable<T>, Transformable<T> {

    /* (non-Javadoc)
     * @see cyclops.container.filterable.Filters#filter(java.util.function.Predicate)
     */
    @Override
    FilterableTransformable<T> filter(Predicate<? super T> fn);

    /* (non-Javadoc)
     * @see cyclops.data.transformable.Transformable#transform(java.util.function.Function)
     */
    @Override
    <R> FilterableTransformable<R> map(Function<? super T, ? extends R> fn);

}

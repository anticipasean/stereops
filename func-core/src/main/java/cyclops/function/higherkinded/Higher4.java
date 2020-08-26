package cyclops.function.higherkinded;

//import org.derive4j.hkt.__;


/**
 * Higher Kinded Type - a core type (e.g. a List) and a data type of the elements within the List (e.g. Integers).
 *
 * @param <T1> Core type
 * @param <T2> Data type of elements in Core Type
 * @author johnmcclean
 */
public interface Higher4<T1, T2, T3, T4, T5> extends Higher3<Higher<T1, T2>, T3, T4, T5> {


}

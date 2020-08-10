package cyclops.typeclasses.monad;

import cyclops.function.higherkinded.Higher;
import cyclops.typeclasses.Filterable;
import java.util.function.Predicate;


/**
 * A filterable monad
 * <p>
 * The zero() operator is used to one supplied HKT with it's zero / zero equivalent when filtered out
 *
 * @param <CRE> CORE Type
 * @author johnmcclean
 */
public interface MonadZero<CRE> extends Monad<CRE>, Filterable<CRE> {


    <T> Higher<CRE, T> zero();


    @Override
    default <T> Higher<CRE, T> filter(Predicate<? super T> predicate,
                                      Higher<CRE, T> ds) {

        return flatMap((T in) -> predicate.test(in) ? ds : zero(),
                       ds);
    }

    default <T> Higher<CRE, T> filter_(Higher<CRE, T> ds,
                                       Predicate<? super T> predicate) {

        return filter(predicate,
                      ds);
    }


}

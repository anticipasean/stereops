package cyclops.function.companion;

import cyclops.function.combiner.Group;
import java.math.BigInteger;


public interface Groups {

    /**
     * Combine two Integers by summing them
     */
    Group<Integer> intSum = Group.of(a -> -a,
                                            Monoids.intSum);
    /**
     * Combine two Longs by summing them
     */
    Group<Long> longSum = Group.of(a -> -a,
                                          Monoids.longSum);
    /**
     * Combine two Doubles by summing them
     */
    Group<Double> doubleSum = Group.of(a -> -a,
                                              Monoids.doubleSum);
    /**
     * Combine two BigIngegers by summing them
     */
    Group<BigInteger> bigIntSum = Group.of(a -> BigInteger.ZERO.subtract(a),
                                                  Monoids.bigIntSum);


}

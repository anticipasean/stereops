package com.oath.cyclops.types.factory;

/**
 * A Data type that supports instantiation of instances of the same type
 *
 * @param <T> Data type of element(s) stored inside this Pure instance
 * @author johnmcclean
 */
@FunctionalInterface
public interface Unit<T> {

    public <T> Unit<T> unit(T unit);

}

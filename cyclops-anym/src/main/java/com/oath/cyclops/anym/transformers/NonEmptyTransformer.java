package com.oath.cyclops.anym.transformers;

import cyclops.container.MonadicValue;
import cyclops.container.unwrappable.Unwrappable;
import cyclops.container.Value;
import cyclops.container.factory.Unit;
import cyclops.container.foldable.Folds;
import cyclops.container.transformable.Transformable;
import cyclops.control.Option;
import cyclops.monads.AnyM;
import cyclops.monads.WitnessType;
import cyclops.monads.transformers.StreamT;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.function.*;

@Deprecated
public abstract class NonEmptyTransformer<W extends WitnessType<W>,T> implements Publisher<T>,
                                                                            Unwrappable,Transformable<T>,
                                                                            Unit<T>,
                                                                            Folds<T>{

    public abstract AnyM<W,? extends Value<T>> transformerStream();
    protected abstract <R> NonEmptyTransformer<W,R> unitAnyM(AnyM<W,? super MonadicValue<R>> anyM);

    public boolean isPresent(){

        return !stream().isEmpty();
    }
    public Option<T> get(){
        return stream().takeOne();
    }

    public T orElse(T value){
        return stream().findAny().orElse(value);
    }
    public T orElseGet(Supplier<? super T> s){
       return stream().findAny().orElseGet((Supplier<T>)s);
    }
    public <X extends Throwable> T orElseThrow(Supplier<? super X> s) throws X {
        return stream().findAny().orElseThrow((Supplier<X>)s);
    }




    /* (non-Javadoc)
     * @see cyclops.types.Traversable#forEachAsync(org.reactivestreams.Subscriber)
     */
     @Override
    public void subscribe(final Subscriber<? super T> s) {

       transformerStream().forEach(v->v.subscribe(s));

    }






    public StreamT<W,T> iterate(UnaryOperator<T> fn, T altSeed) {

        return StreamT.of(this.transformerStream().map(v->v.asSupplier(altSeed).iterate(fn)));
    }

    public StreamT<W,T> generate(T altSeed) {

        return StreamT.of(this.transformerStream().map(v->v.asSupplier(altSeed).generate()));
    }



    public <R> AnyM<W,R> fold(Function<? super T, ? extends R> some, Supplier<? extends R> none){
        return this.transformerStream().map(v->v.fold(some,none));
    }


}

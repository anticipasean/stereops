package cyclops.monads.function;

import cyclops.function.enhanced.Function0;
import cyclops.function.enhanced.Function1;
import cyclops.monads.AnyM;
import cyclops.monads.AnyMs;
import cyclops.monads.WitnessType;
import cyclops.reactor.container.transformer.FutureT;
import cyclops.reactor.container.transformer.ListT;
import cyclops.reactive.collection.container.mutable.ListX;

import java.util.function.Supplier;

/**
 * Created by johnmcclean on 18/12/2016.
 */
@FunctionalInterface
public interface AnyMFunction0<W extends WitnessType<W>,R> extends Function0<AnyM<W,R>> {
  static <W extends WitnessType<W>,R> AnyMFunction0<W,R> liftF(Supplier<R> fn0, W witness){
    return ()-> witness.adapter().unit(fn0.get());
  }
  static <W extends WitnessType<W>,R> Function1<W,AnyMFunction0<W,R>> liftF(Supplier<R> fn0){
    return w->liftF(fn0,w);
  }
  static <W extends WitnessType<W>,R> Function0<FutureT<W,R>> liftFutureT(Function0<R> fn0, W witness) {
    return fn0.functionOps().liftFuture().andThen(f-> AnyMs.liftM(f,witness));
  }

  static <W extends WitnessType<W>,R> Function0<ListT<W,R>> liftListT(Function0<R> fn0, W witness) {
      Function0<ListX<R>> f = ()-> ListX.of(fn0.apply());
    return f.andThen(l->AnyMs.liftM(l,witness));
  }
}

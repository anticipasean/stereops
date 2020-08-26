package cyclops.pure.typeclasses.cyclops;

import static cyclops.function.companion.Lambda.l1;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import cyclops.function.higherkinded.Higher;
import cyclops.container.control.Eval;
import cyclops.container.control.Maybe;
import cyclops.container.control.Either;
import cyclops.container.control.LazyEither;
import cyclops.function.enhanced.Function1;
import cyclops.function.higherkinded.DataWitness.eval;
import cyclops.pure.instances.control.EvalInstances;
import cyclops.pure.instances.control.MaybeInstances;
import org.junit.Test;



public class EvalsTest {

    @Test
    public void unit(){

        Eval<String> opt = EvalInstances.unit()
                                            .unit("hello")
                                            .convert(Eval::narrowK);

        assertThat(opt,equalTo(Eval.now("hello")));
    }
    @Test
    public void functor(){

        Eval<Integer> opt = EvalInstances.unit()
                                     .unit("hello")
                                     .applyHKT(h-> EvalInstances.functor().map((String v) ->v.length(), h))
                                     .convert(Eval::narrowK);

        assertThat(opt,equalTo(Eval.now("hello".length())));
    }
    @Test
    public void apSimple(){

        EvalInstances.applicative()
            .ap(Eval.now(l1(this::multiplyByTwo)),Eval.now(1));
    }
    private int multiplyByTwo(int x){
        return x*2;
    }
    @Test
    public void tailRec(){
        System.out.println(EvalInstances.monadRec().tailRec(10, i->Eval.now(Either.right(i+10))));
        System.out.println(EvalInstances.monadRec().tailRec(10, i-> i<1000_000 ? Eval.now(LazyEither.left(i+1)) : Eval.now(LazyEither.right(i+10))));
    }
    @Test
    public void applicative(){


        Eval<Function1<Integer,Integer>> optFn = EvalInstances.unit()
                                                             .unit(l1((Integer i) ->i*2))
                                                              .convert(Eval::narrowK);

        Eval<Integer> opt = EvalInstances.unit()
                                     .unit("hello")
                                     .applyHKT(h-> EvalInstances.functor().map((String v) ->v.length(), h))
                                     .applyHKT(h-> EvalInstances.applicative().ap(optFn, h))
                                     .convert(Eval::narrowK);

        assertThat(opt,equalTo(Eval.now("hello".length()*2)));
    }
    @Test
    public void monadSimple(){
       Eval<Integer> opt  = EvalInstances.monad()
                                            .<Integer,Integer>flatMap(i->Eval.now(i*2), Eval.now(3))
                                            .convert(Eval::narrowK);
    }
    @Test
    public void monad(){

        Eval<Integer> opt = EvalInstances.unit()
                                     .unit("hello")
                                     .applyHKT(h-> EvalInstances.monad().flatMap((String v) -> EvalInstances.unit().unit(v.length()), h))
                                     .convert(Eval::narrowK);

        assertThat(opt,equalTo(Eval.now("hello".length())));
    }



    @Test
    public void  foldLeft(){
        int sum  = EvalInstances.foldable()
                        .foldLeft(0, (a,b)->a+b, Eval.now(4));

        assertThat(sum,equalTo(4));
    }
    @Test
    public void  foldRight(){
        int sum  = EvalInstances.foldable()
                        .foldRight(0, (a,b)->a+b, Eval.now(1));

        assertThat(sum,equalTo(1));
    }
    @Test
    public void traverse(){
       Maybe<Higher<eval, Integer>> res = EvalInstances.traverse()
                                                         .traverseA(MaybeInstances.applicative(), (Integer a)->Maybe.just(a*2), Eval.now(1))
                                                         .convert(Maybe::narrowK);


       assertThat(res.map(h->h.convert(Eval::narrowK).get()),
                  equalTo(Maybe.just(Eval.now(2).get())));
    }

}

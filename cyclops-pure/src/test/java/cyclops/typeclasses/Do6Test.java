package cyclops.typeclasses;

import cyclops.function.higherkinded.DataWitness.option;
import cyclops.container.control.Option;
import cyclops.container.immutable.impl.Seq;
import cyclops.function.companion.Lambda;
import cyclops.instances.control.OptionInstances;
import cyclops.instances.data.SeqInstances;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static cyclops.container.control.Option.some;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class Do6Test {

    @Test
    public void doOption6(){
        assertThat(Do.forEach(OptionInstances::monad)
                     .__(some(10))
                     .__(some(5))
                     .__(some(2))
                     .__(some(1))
                     .__(some(100))
                     .__(some(1000))
                     .__(some(10000))
                     .yield((a,b,c,d,e,f,g)->a+b+c+d+e+f+g)
                    .fold(Option::narrowK),equalTo(some(11118)));
    }
    @Test
    public void doOptionUnbound6(){
        assertThat(Do.forEach(OptionInstances::monad)
                     ._of(10)
                     ._of(5)
                     ._of(2)
                     ._of(1)
                     ._of(100)
                     .__(some(1000))
                     .__(some(10000))
                     .yield((a,b,c,d,e,f,g)->a+b+c+d+e+f+g)
                     .fold(Option::narrowK),
            equalTo(some(11118)));
    }

    @Test
    public void doOptionLazy6(){
        assertThat(Do.forEach(OptionInstances::monad)
            ._of(10)
            .__(i->some(i/2))
            .__((a,b)->some(a-b-3))
            .__((a,b,c)->some(a-c-b-2))
            .__((a,b,c,d)->some(a+b+c+d+82))
            .__((a,b,c,d,e)->some(1000))
            .__((a,b,c,d,e,g)->some(10000))
            .yield((a,b,c,d,e,f,g)->a+b+c+d+e+f+g)
            .fold(Option::narrowK),equalTo(some(11118)));
    }


    @Test
    public void doOptionGuardSome3(){
        assertThat(Do.forEach(OptionInstances::monad)
                    .__(some(10))
                    .__(some(5))
                    .__(some(2))
                    .__(some(1))
                    .__(some(100))
                    .__(some(1000))
                    .__(some(10000))
                    .guard(OptionInstances.monadZero(),(a,b,c,d,e,f,g)->a+b+c+d+e+f+g>117)
                    .yield((a,b,c,d,e,f,g)->a+b+c+d+e+f+g)
                    .fold(Option::narrowK),
            equalTo(some(11118)));
    }
    @Test
    public void doOptionGuardNone3(){
        assertThat(Do.forEach(OptionInstances::monad)
            .__(some(10))
            .__(some(5))
            .__(some(2))
            .__(some(1))
            .__(some(100))
            .__(some(1000))
            .__(some(10000))
            .guard(OptionInstances.monadZero(),(a,b,c,d,e,f,g)->a+b+c+d+e+f+g<117)
            .yield((a,b,c,d,e,f,g)->a+b+c+d+e+f+g)
            .fold(Option::narrowK),equalTo(Option.none()));
    }
    @Test
    public void doOptionShow(){
        String s = Do.forEach(OptionInstances.monad())
            ._of(10)
            ._of(20)
            ._of(100)
            ._of(1000)
            ._of(10000)
            ._of(100000)
            .show(new Show<option>(){})
            .yield((a,b,c,d,e,f)->a+b+c+d+e+f)
            .fold(Option::narrowK).orElse(null);
        assertThat(s,equalTo("11130Some[10000]"));
    }
    @Test
    public void doOptionShowDefault(){
        String s = Do.forEach(OptionInstances.monad())
            ._of(10)
            ._of(20)
            ._of(200)
            ._of(1000)
            ._of(10000)
            ._of(100000)
            ._show(new Show<option>() {})
            .yield((a,b,c,d,e,f,st)->st+a+b+c+d+e+f).fold(Option::narrowK).orElse(null);
        assertThat(s,equalTo("Some[1000]1020200100010000100000"));
    }

    @Test
    public void doOptionMap1(){
        Option<Integer> eleven =   Do.forEach(OptionInstances.monad())
            ._of(10)
            ._of(100)
            ._of(1000)
            ._of(10000)
            ._of(100000)
            ._of(1000000)
            .map(i->i+1)
            .fold(Option::narrowK);

        assertThat(eleven,equalTo(some(1000001)));

    }
    @Test
    public void doOptionPeek1(){
        AtomicInteger ai = new AtomicInteger(-1);
        Option<Integer> eleven =   Do.forEach(OptionInstances.monad())
            ._of(10)
            ._of(100)
            ._of(1000)
            ._of(10000)
            ._of(100000)
            ._of(1000000)
            .peek(i->{
                ai.set(i);
            })
            .fold(Option::narrowK);

        assertThat(ai.get(),equalTo(1000000));

    }
    @Test
    public void doOptionFlatten (){

        Option<Integer> res =   Do.forEach(OptionInstances.monad())
            ._of(10)
            ._of(100)
            ._of(1000)
            ._of(10000)
            ._of(100000)
            ._of(1000000)
            ._flatten(some(some(10)))
            .yield((a,b,c,d,e,f,g)->a+b+c+d+e+f+g)
            .fold(Option::narrowK);

        assertThat(res,equalTo(some(1111120)));

    }



    @Test
    public void doSeqAp(){
        Seq<Integer> seq = Do.forEach(SeqInstances::monad)
            ._of(10)
            ._of(20)
            ._of(1000)
            ._of(10000)
            ._of(100000)
            ._of(1000000)
            .ap(Seq.of(Lambda.λ((Integer i) -> i + 1)))
            .fold(Seq::narrowK);


        assertThat(seq,equalTo(Seq.of(1000001)));

    }

}

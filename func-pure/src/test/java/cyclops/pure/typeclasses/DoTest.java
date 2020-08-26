package cyclops.pure.typeclasses;

import cyclops.function.higherkinded.DataWitness.option;
import cyclops.pure.arrow.Kleisli;
import cyclops.pure.arrow.MonoidKs;
import cyclops.function.companion.Monoids;
import cyclops.container.control.Either;
import cyclops.container.control.Option;
import cyclops.container.immutable.impl.Seq;
import cyclops.container.immutable.tuple.Tuple;
import cyclops.pure.instances.control.EitherInstances;
import cyclops.pure.instances.control.OptionInstances;
import cyclops.pure.instances.data.SeqInstances;
import org.junit.Test;

import static cyclops.container.control.Either.right;
import static cyclops.container.control.Option.some;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class DoTest {



    @Test
    public void doOption(){
        assertThat(Do.forEach(OptionInstances::monad)
                     .__(some(10))
                     .yield(i->i+1)
                     .fold(Option::narrowK),equalTo(some(11)));
    }
    @Test
    public void doOptionUnbound(){
        assertThat(Do.forEach(OptionInstances::monad)
                        ._of(10)
                        .yield(i->i+1)
                        .fold(Option::narrowK),equalTo(some(11)));
    }
    @Test
    public void doOptionLazy(){
        assertThat(Do.forEach(OptionInstances::monad)
                        .__(()->some(10))
                        .yield(i->i+1)
                        .fold(Option::narrowK),equalTo(some(11)));
    }
    @Test
    public void doOptionGuardSome(){
        assertThat(Do.forEach(OptionInstances::monad)
            .__(some(10))
            .guard(OptionInstances.monadZero(),i->i>5)
            .yield(i->i+1)
            .fold(Option::narrowK),equalTo(some(11)));
    }
    @Test
    public void doOptionGuardNone(){
        assertThat(Do.forEach(OptionInstances::monad)
                        .__(some(10))
                        .guard(OptionInstances.monadZero(),i->i<5)
                        .yield(i->i+1)
                        .fold(Option::narrowK),equalTo(Option.none()));
    }
    @Test
    public void doSeq(){
        assertThat(Do.forEach(SeqInstances::monad)
                        .__(Seq.of(10))
                        .yield(i->i+1)
                        .fold(Seq::narrowK),equalTo(Seq.of(11)));
    }
    @Test
    public void doSeqUnbound(){
        assertThat(Do.forEach(SeqInstances::monad)
                        ._of(10)
                        .yield(i->i+1)
                        .fold(Seq::narrowK),equalTo(Seq.of(11)));
    }
    @Test
    public void doSeqLazy(){
        assertThat(Do.forEach(SeqInstances::monad)
                        ._of(10)
                        .yield(i->i+1)
                        .fold(Seq::narrowK),equalTo(Seq.of(11)));
    }

    @Test
    public void doEither(){
        Do.forEach(EitherInstances.<String>getInstance())
            .__(Either.<String,Integer>right(10))
            .yield(i->i+1);

        assertThat(Do.forEach(EitherInstances::monad)
                        .__(right(10))
                        .yield(i->i+1)
                        .fold(Either::narrowK),equalTo(right(11)));
    }

    @Test
    public void doOptionFlatten(){

        Option<Integer> res =   Do.forEach(OptionInstances.monad())
            ._flatten(some(some(10)))
            .fold(Option::narrowK);

        assertThat(res,equalTo(some(10)));

    }
    @Test
    public void doKliesli(){
       Kleisli<option,Integer,Integer> fnK =  Do.forEach(OptionInstances.monad())
                                                .kliesli((Integer i)->i+1);

       assertThat(fnK.apply(10),equalTo(some(11)));

    }
    @Test
    public void doKliesliK(){
        Kleisli<option,Integer,Integer> fnK =  Do.forEach(OptionInstances.monad())
                                                 .kliesliK((Integer i)->some(i+1));

        assertThat(fnK.apply(10),equalTo(some(11)));

    }

    @Test
    public void doExpandOne(){
        Seq<Integer> seq = Do.forEach(SeqInstances::monad)
                            .expand(SeqInstances::unfoldable)
                            .one(1)
                            .fold(Seq::narrowK);

        assertThat(seq,equalTo(Seq.of(1)));

    }
    @Test
    public void doExpandNone(){
        Seq<Integer> seq = Do.forEach(SeqInstances::monad)
                .expand(SeqInstances::unfoldable)
                .<Integer>none()
                .fold(Seq::narrowK);

        assertThat(seq,equalTo(Seq.empty()));

    }
    @Test
    public void doExpandReplicate(){
        Seq<Integer> seq = Do.forEach(SeqInstances::monad)
                                .expand(SeqInstances::unfoldable)
                                .replicate(2l,10)
                                .fold(Seq::narrowK);

        assertThat(seq,equalTo(Seq.of(10,10)));

    }
    @Test
    public void doExpandUnfold(){
        Seq<Integer> seq = Do.forEach(SeqInstances::monad)
            .expand(SeqInstances::unfoldable)
            .unfold(1,(a)-> a<4 ? Option.some(Tuple.tuple(a+1,a+1)) : Option.none())
            .fold(Seq::narrowK);

        assertThat(seq,equalTo(Seq.of(2,3,4)));

    }

    @Test
    public void doNestedFoldLeft(){
        Seq<Integer> seq = Do.forEach(SeqInstances::monad)
                                .__(SeqInstances::functor, Seq.of(Seq.of(20,100,40), Seq.of(10), Seq.empty()))
                                .foldLeft(SeqInstances::foldable, Monoids.intSum)
                                .fold(Seq::narrowK);

        assertThat(seq,equalTo(Seq.of(160,10,0)));

    }
    @Test
    public void doNestedFoldK(){
        Seq<Integer> seq = Do.forEach(SeqInstances::monad)
            .__(SeqInstances::functor, Seq.of(Seq.of(20,100,40), Seq.of(10), Seq.empty()))
            .foldK(SeqInstances::foldable, SeqInstances::monad, MonoidKs.seqConcat())
            .fold(Seq::narrowK);

        assertThat(seq,equalTo(Seq.of(10, 20, 100, 40)));

    }
    @Test
    public void doNestedMap(){
        Seq<Seq<Integer>> seq = Do.forEach(SeqInstances::monad)
                                    .__(SeqInstances::functor, Seq.of(Seq.of(20,100,40), Seq.of(10), Seq.empty()))
                                    .map(Seq::narrowK)
                                    .fold(Seq::narrowK);

        assertThat(seq,equalTo(Seq.of(Seq.of(20,100,40), Seq.of(10), Seq.empty())));

    }
    @Test
    public void doNestedSequence(){
        Option<Seq<Integer>> opt = Do.forEach(SeqInstances::monad)
                                                .__(OptionInstances::functor, Seq.of(Option.some(20), Option.some(10)))
                                                .sequence(SeqInstances.traverse(), OptionInstances.monad())
                                                .map(Seq::narrowK)
                                                .fold(Option::narrowK);

        assertThat(opt,equalTo(Option.of(Seq.of(20,10))));

    }
    @Test
    public void doNestedSequenceNone(){
        Option<Seq<Integer>> opt = Do.forEach(SeqInstances::monad)
            .__(OptionInstances::functor, Seq.of(Option.some(20), Option.some(10),Option.none()))
            .sequence(SeqInstances.traverse(), OptionInstances.monad())
            .map(Seq::narrowK)
            .fold(Option::narrowK);

        assertThat(opt,equalTo(Option.none()));

    }


}

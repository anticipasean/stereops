package com.aol.cyclops2.streams;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.jooq.lambda.tuple.Tuple.tuple;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cyclops.Semigroups;
import cyclops.async.QueueFactories;
import cyclops.async.Topic;
import cyclops.collections.ListX;
import cyclops.collections.SetX;
import cyclops.control.Maybe;
import cyclops.control.either.Either;
import cyclops.stream.Spouts;
import cyclops.stream.Streamable;
import org.hamcrest.Matchers;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cyclops.stream.ReactiveSeq;
import reactor.core.publisher.Flux;

public class BaseSequentialTest {

    public static final int ITERATIONS = 3;

    protected <U> ReactiveSeq<U> of(U... array) {
        return ReactiveSeq.of(array);
    }


    ReactiveSeq<Integer> empty;
    ReactiveSeq<Integer> nonEmpty;

    @Before
    public void setup() {
        empty = of();
        nonEmpty = of(1);
    }

    Integer value2() {
        return 5;
    }

    @Test
    public void duplicateDuplicate(){
        for(int k=0;k<ITERATIONS;k++) {
            assertThat(of(1, 2, 3).duplicate()
                    .v1.duplicate().v1.duplicate().v1.toListX(), equalTo(ListX.of(1, 2, 3)));
        }

    }
    @Test
    public void duplicateDuplicateDuplicate(){
        for(int k=0;k<ITERATIONS;k++) {
            assertThat(of(1, 2, 3).duplicate()
                    .v1.duplicate().v1.duplicate().v1.duplicate().v1.toListX(), equalTo(ListX.of(1, 2, 3)));
        }

    }
    @Test
    public void skipDuplicateSkip() {
        assertThat(of(1, 2, 3).duplicate().v1.skip(1).duplicate().v1.skip(1).toListX(), equalTo(ListX.of(3)));
        assertThat(of(1, 2, 3).duplicate().v2.skip(1).duplicate().v2.skip(1).toListX(), equalTo(ListX.of(3)));
    }
    @Test
    public void skipLimitDuplicateLimitSkip() {
        Tuple3<ReactiveSeq<Integer>, ReactiveSeq<Integer>,ReactiveSeq<Integer>> dup = of(1, 2, 3).triplicate();
        Optional<Integer> head1 = dup.v1.limit(1).toOptional().flatMap(l -> {
            return l.size() > 0 ? Optional.of(l.get(0)) : Optional.empty();
        });
        Tuple3<ReactiveSeq<Integer>, ReactiveSeq<Integer>,ReactiveSeq<Integer>> dup2 = dup.v2.skip(1).triplicate();
        Optional<Integer> head2 = dup2.v1.limit(1).toOptional().flatMap(l -> {
            return l.size() > 0 ? Optional.of(l.get(0)) : Optional.empty();
        });
        assertThat(dup2.v2.skip(1).toListX(),equalTo(ListX.of(3)));

        assertThat(of(1, 2, 3).duplicate().v1.skip(1).duplicate().v1.skip(1).toListX(), equalTo(ListX.of(3)));
    }


    @Test
    public void splitThenSplit(){
        assertThat(of(1,2,3).toOptional(),equalTo(Optional.of(ListX.of(1,2,3))));
        // System.out.println(of(1, 2, 3).splitAtHead().v2.toListX());
        System.out.println("split " + of(1, 2, 3).splitAtHead().v2.splitAtHead().v2.toListX());
        assertEquals(Optional.of(3), of(1, 2, 3).splitAtHead().v2.splitAtHead().v2.splitAtHead().v1);
    }
    @Test
    public void changesEmpty(){
        assertThat(of().changes()
                .toListX(),equalTo(ListX.of()));


    }


    @Test
    public void changes(){
        assertThat(of(1,1,1,2,2,2,3,3,3,4,4,4,4,5,6).changes()
                .toListX(),equalTo(ListX.of(1,2,3,4,5,6)));


    }


    @Test
    public void publishToAndMerge(){
        for(int k = 0; k< ITERATIONS; k++) {
            System.out.println("Publish to and merge iteration "+k);
            cyclops.async.Queue<Integer> queue = QueueFactories.<Integer>boundedNonBlockingQueue(10)
                    .build();

            Thread t = new Thread(() -> {

                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Closing! " + queue.size());
                    queue.close();

                }
            });
            t.start();

            AtomicBoolean complete = new AtomicBoolean(false);


            ListX<Integer> list = of(1, 2, 3)
                    .publishTo(queue)
                    .peek(System.out::println)
                    .merge(queue)
                    .toListX();
            assertThat(list, hasItems(1, 2, 3));
            assertThat(list.size(), equalTo(6));
            assertThat(list.toSet(), equalTo(SetX.of(1, 2, 3)));
            t=null;
            System.gc();
        }

    }
    @Test
    public void publishTest(){
        for(int k=0;k<ITERATIONS;k++) {
            cyclops.async.Queue<Integer> queue = QueueFactories.<Integer>boundedNonBlockingQueue(10)
                    .build();

            Thread t = new Thread(() -> {

                while (true) {
                    try {
                        System.out.println("Sleeping!");
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Waking!");
                    System.out.println("Closing! " + queue.size());
                    queue.close();

                }
            });
            t.start();
            of(1, 2, 3).peek(i -> System.out.println("publishing " + i))
                    .publishTo(queue)
                    .forEach(System.out::println);
            assertThat(queue.stream().collect(Collectors.toList()), equalTo(ListX.of(1, 2, 3)));
            t=null;
            System.gc();
        }
    }
    @Test
    public void mergeAdapterTest(){
        for(int k=0;k<ITERATIONS;k++) {
            cyclops.async.Queue<Integer> queue = QueueFactories.<Integer>boundedNonBlockingQueue(10)
                    .build();

            Thread t = new Thread(() -> {

                while (true) {

                    queue.add(1);
                    queue.add(2);
                    queue.add(3);
                    try {
                        System.out.println("Sleeping!");
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Waking!");
                    System.out.println("Closing! " + queue.size());
                    queue.close();

                }
            });
            t.start();


            assertThat(this.<Integer>of().peek(i -> System.out.println("publishing " + i))
                    .merge(queue).collect(Collectors.toList()), equalTo(ListX.of(1, 2, 3)));
            t=null;
            System.gc();
        }
    }
    @Test
    public void mergeAdapterTest1(){
        for(int k=0;k<ITERATIONS;k++) {
            System.out.println("Test iteration " +k);
            cyclops.async.Queue<Integer> queue = QueueFactories.<Integer>boundedNonBlockingQueue(10)
                    .build();

            Thread t = new Thread(() -> {

                while (true) {

                    queue.add(1);
                    queue.add(2);
                    queue.add(3);
                    try {
                        //    System.out.println("Sleeping!");
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //   System.out.println("Closing! " + queue.size());
                    queue.close();

                }
            });
            t.start();
            assertThat(this.<Integer>of(10).peek(i -> System.out.println("publishing " + i))
                    .merge(queue).collect(Collectors.toList()), hasItems(10,1, 2, 3));

            t=null;
            System.gc();
        }
    }
    @Test
    public void parallelFanOut(){
        assertThat(of(1,2,3,4)
                .parallelFanOut(ForkJoinPool.commonPool(), s1->s1.filter(i->i%2==0).map(i->i*2),
                        s2->s2.filter(i->i%2!=0).map(i->i*100))
                .toListX(), Matchers.equalTo(ListX.of(4,100,8,300)));

        assertThat(of(1,2,3,4)
                .parallelFanOutZipIn(ForkJoinPool.commonPool(), s1->s1.filter(i->i%2==0).map(i->i*2),
                        s2->s2.filter(i->i%2!=0).map(i->i*100),(a,b)->a+b)
                .toListX(), Matchers.equalTo(ListX.of(104,308)));
    }
    @Test
    public void mergePTest(){
        for(int i=0;i<1_000;i++) {
            ListX<Integer> list = of(3, 6, 9).mergeP(of(2, 4, 8), of(1, 5, 7)).toListX();
            assertThat(list, hasItems(1, 2, 3, 4, 5, 6, 7, 8, 9));
            assertThat(list.size(), Matchers.equalTo(9));
        }
    }
    @Test
    public void triplicateFanOut(){

        for (int k = 0; k < 10; k++) {
            assertThat(of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                    .fanOut(s1 -> s1.peek(System.out::println).filter(i -> i % 3 == 0).map(i -> i * 2),
                            s2 -> s2.filter(i -> i % 3 == 1).map(i -> i * 100),
                            s3 -> s3.filter(i -> i % 3 == 2).map(i -> i * 1000))
                    .toListX(), Matchers.equalTo(ListX.of(6, 100, 2000, 12, 400, 5000, 18, 700, 8000)));
        }

    }
    @Test
    public void fanOut(){
        for (int k = 0; k < ITERATIONS; k++) {
            assertThat(of(1, 2, 3, 4)
                    .fanOut(s1 -> s1.filter(i -> i % 2 == 0).map(i -> i * 2),
                            s2 -> s2.filter(i -> i % 2 != 0).map(i -> i * 100))
                    .toListX(), Matchers.equalTo(ListX.of(4, 100, 8, 300)));
            assertThat(of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                    .fanOut(s1 -> s1.filter(i -> i % 3 == 0).map(i -> i * 2),
                            s2 -> s2.filter(i -> i % 3 == 1).map(i -> i * 100),
                            s3 -> s3.filter(i -> i % 3 == 2).map(i -> i * 1000))
                    .toListX(), Matchers.equalTo(ListX.of(6, 100, 2000, 12, 400, 5000, 18, 700, 8000)));
            assertThat(of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
                    .fanOut(s1 -> s1.filter(i -> i % 4 == 0).map(i -> i * 2),
                            s2 -> s2.filter(i -> i % 4 == 1).map(i -> i * 100),
                            s3 -> s3.filter(i -> i % 4 == 2).map(i -> i * 1000),
                            s4 -> s4.filter(i -> i % 4 == 3).map(i -> i * 10000))
                    .toListX(), Matchers.equalTo(ListX.of(8, 100, 2000, 30000, 16, 500, 6000, 70000, 24, 900, 10000, 110000)));
        }
    }
    @Test
    public void parallelFanOut2(){
        for (int k = 0; k < ITERATIONS; k++) {
            assertThat(of(1, 2, 3, 4)
                    .parallelFanOut(ForkJoinPool.commonPool(), s1 -> s1.filter(i -> i % 2 == 0).map(i -> i * 2),
                            s2 -> s2.filter(i -> i % 2 != 0).map(i -> i * 100))
                    .toListX(), Matchers.equalTo(ListX.of(4, 100, 8, 300)));
            assertThat(of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                    .parallelFanOut(ForkJoinPool.commonPool(), s1 -> s1.filter(i -> i % 3 == 0).map(i -> i * 2),
                            s2 -> s2.filter(i -> i % 3 == 1).map(i -> i * 100),
                            s3 -> s3.filter(i -> i % 3 == 2).map(i -> i * 1000))
                    .toListX(), Matchers.equalTo(ListX.of(6, 100, 2000, 12, 400, 5000, 18, 700, 8000)));
            assertThat(of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
                    .parallelFanOut(ForkJoinPool.commonPool(), s1 -> s1.filter(i -> i % 4 == 0).map(i -> i * 2),
                            s2 -> s2.filter(i -> i % 4 == 1).map(i -> i * 100),
                            s3 -> s3.filter(i -> i % 4 == 2).map(i -> i * 1000),
                            s4 -> s4.filter(i -> i % 4 == 3).map(i -> i * 10000))
                    .toListX(), Matchers.equalTo(ListX.of(8, 100, 2000, 30000, 16, 500, 6000, 70000, 24, 900, 10000, 110000)));
        }
    }
    @Test
    public void broadcastTest(){
        Topic<Integer> topic = of(1,2,3)
                .broadcast();


        ReactiveSeq<Integer> stream1 = topic.stream();
        ReactiveSeq<Integer> stream2 = topic.stream();
        assertThat(stream1.toListX(), Matchers.equalTo(ListX.of(1,2,3)));
        assertThat(stream2.stream().toListX(), Matchers.equalTo(ListX.of(1,2,3)));

    }



    @Test
    public void ambTest(){
        assertThat(of(1,2,3).ambWith(Flux.just(10,20,30)).toListX(), isOneOf(ListX.of(10,20,30),ListX.of(1,2,3)));
    }

    @Test
    public void flatMapI() {
        for(int k=0;k<1_000;k++) {
            assertThat(of(1, 2, 3)
                    .flatMapI(i -> of(10, 20, 30 * i))
                    .toList(), equalTo(ListX.of(10, 20, 30, 10, 20, 60, 10, 20, 90)));
        }
    }

    @Test
    public void flatMapStreamFilter() {
        assertThat(of(1, 2, 3, null).flatMap(i -> ReactiveSeq.of(i).filter(Objects::nonNull))
                        .collect(Collectors.toList()),
                Matchers.equalTo(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void flatMapIStream() {
        assertThat(of(1, 2, 3, null).flatMapI(i -> ReactiveSeq.of(i).filter(Objects::nonNull))
                        .collect(Collectors.toList()),
                Matchers.equalTo(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void flatMapIMaybe() {
        assertThat(of(1, 2, 3, null).flatMapI(Maybe::ofNullable)
                        .collect(Collectors.toList()),
                Matchers.equalTo(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void flatMapStream() {
        for (int i = 0; i < ITERATIONS; i++) {
            assertThat(of(1, 2, 3, null).flatMap(Stream::of)
                            .collect(Collectors.toList()),
                    Matchers.equalTo(Arrays.asList(1, 2, 3, null)));
        }
    }

    @Test
    public void flatMap() {
        assertThat(of(1, 2, 3)
                .flatMap(i -> Stream.of(10, 20, 30 * i))
                .toList(), equalTo(ListX.of(10, 20, 30, 10, 20, 60, 10, 20, 90)));
    }

    @Test
    public void flatMapSimple() {
        assertThat(of(1)
                .flatMap(i -> Stream.of(10, 20))
                .toList(), equalTo(ListX.of(10, 20)));
    }

    @Test
    public void combine() {
        assertThat(of(1,2,3,4,5,6,7,8)
                .combine((a, b)->a<5,Semigroups.intSum)
                .findOne(), Matchers.equalTo(Maybe.of(6)));
    }
    @Test
    public void combineOneFirstOrError() {
        assertThat(of(1)
                .combine((a, b)->a<5,Semigroups.intSum)
                .findFirstOrError(), Matchers.equalTo(Either.right(1)));
    }
    @Test
    public void combineOne() {
        assertThat(of(1)
                .combine((a, b)->a<5,Semigroups.intSum)
                .findOne(), Matchers.equalTo(Maybe.of(1)));
    }
    @Test
    public void combineTwo() {
        assertThat(of(1,2)
                .combine((a, b)->a<5,Semigroups.intSum)
                .findOne(), Matchers.equalTo(Maybe.of(3)));
    }
    @Test
    public void combineEmpty() {
        assertThat(this.<Integer>of()
                .combine((a, b)->a<5,Semigroups.intSum)
                .findOne(), Matchers.equalTo(Maybe.none()));
    }
    @Test
    public void combineTerminate() {
        assertThat(of(1,2,3,4,5,6,7,8)
                .combine((a, b)->a<5,Semigroups.intSum)
                .findFirst(), Matchers.equalTo(Optional.of(7)));
    }
    @Test
    public void dropRight(){
        assertThat(of(1,2,3).dropRight(1).toList(),hasItems(1,2));
    }
	@Test
	public void dropRightNeg(){
		assertThat(of(1,2,3).dropRight(1).toList(),not(hasItems(3)));
	}
    @Test
    public void dropRightEmpty(){
        assertThat(of().dropRight(1).toList(),equalTo(Arrays.asList()));
    }
    
    @Test
    public void dropUntil(){
        assertThat(of(1,2,3,4,5).dropUntil(p->p==2).toList().size(),lessThan(5));
    }
    @Test
    public void dropUntilEmpty(){
        assertThat(of().dropUntil(p->true).toList(),equalTo(Arrays.asList()));
    }
    @Test
    public void dropWhile(){
        assertThat(of(1,2,3,4,5).dropWhile(p->p<6).toList().size(),lessThan(1));
    }
    @Test
    public void dropWhileEmpty(){
        assertThat(of().dropWhile(p->true).toList(),equalTo(Arrays.asList()));
    }
    @Test
    public void takeRight(){
        assertThat(of(1,2,3).takeRight(1).toList(),hasItems(3));
    }
    @Test
    public void takeRightEmpty(){
        assertThat(of().takeRight(1).toList(),equalTo(Arrays.asList()));
    }
    
    @Test
    public void takeUntil(){
        assertThat(of(1,2,3,4,5).takeUntil(p->p==2).toList().size(),greaterThan(0));
    }
    @Test
    public void takeUntilEmpty(){
        assertThat(of().takeUntil(p->true).toList(),equalTo(Arrays.asList()));
    }
    @Test
    public void takeWhile(){
        assertThat(of(1,2,3,4,5).takeWhile(p->p<6).toList().size(),greaterThan(1));
    }
    @Test
    public void takeWhileEmpty(){
        assertThat(of().takeWhile(p->true).toList(),equalTo(Arrays.asList()));
    } 
    @Test
    public void presentConvert(){

        assertTrue(of(1).toOptional().isPresent());
        assertTrue(of(1).toListX().size()>0);
        assertTrue(of(1).toDequeX().size()>0);
        assertTrue(of(1).toPStackX().size()>0);
        assertTrue(of(1).toQueueX().size()>0);
        assertTrue(of(1).toPVectorX().size()>0);
        assertTrue(of(1).toPQueueX().size()>0);
        assertTrue(of(1).toSetX().size()>0);
        assertTrue(of(1).toSortedSetX().size()>0);
        assertTrue(of(1).toPOrderedSetX().size()>0);
        assertTrue(of(1).toPBagX().size()>0);
        assertTrue(of(1).toPMapX(t->t,t->t).size()>0);
        assertTrue(of(1).toMapX(t->t,t->t).size()>0);

        assertTrue(of(1).toSet().size()>0);
        assertTrue(of(1).toList().size()>0);
        assertTrue(of(1).toStreamable().size()>0);
        
        
    }
    @Test
    public void optionalConvert(){
        for(int i=0;i<10;i++){
            assertThat(of(1,2,3).toOptional(),equalTo(Optional.of(ListX.of(1,2,3))));
        }
    }
    @Test
    public void presentConvert2(){

        assertTrue(of(1,2).toOptional().isPresent());
        Optional<ListX<Integer>> opt = of(1, 2).toOptional();
        assertThat(opt,equalTo(Optional.of(ListX.of(1,2))));

        assertTrue(of(1,2).toListX().size()==2);
        assertTrue(of(1,2).toDequeX().size()==2);
        assertTrue(of(1,2).toPStackX().size()==2);
        assertTrue(of(1,2).toQueueX().size()==2);
        assertTrue(of(1,2).toPVectorX().size()==2);
        assertTrue(of(1,2).toPQueueX().size()==2);
        assertTrue(of(1,2).toSetX().size()==2);
        assertTrue(of(1,2).toSortedSetX().size()==2);
        assertTrue(of(1,2).toPOrderedSetX().size()==2);
        assertTrue(of(1,2).toPBagX().size()==2);
        assertTrue(of(1,2).toPMapX(t->t,t->t).size()==2);
        assertTrue(of(1,2).toMapX(t->t,t->t).size()==2);

        assertTrue(of(1,2).toSet().size()==2);
        assertTrue(of(1,2).toList().size()==2);
        assertTrue(of(1,2).toStreamable().size()==2);


    }

        
    private int addOne(Integer i){
        return i+1;
    }
    private int add(Integer a, Integer b){
        return a+b;
    }
    private String concat(String a, String b, String c){
        return a+b+c;
    }
    private String concat4(String a, String b, String c,String d){
        return a+b+c+d;
    }
    private String concat5(String a, String b, String c,String d,String e){
        return a+b+c+d+e;
    }
		@Test
		public void groupedFunction(){
		    assertThat(of(1,2,3).grouped(f-> f<3? "a" : "b").count(),equalTo((2L)));
		    assertThat(of(1,2,3).grouped(f-> f<3? "a" : "b").filter(t->t.v1.equals("a"))
		                    .map(t->t.v2).map(s->s.toList()).single(),
		                        equalTo((Arrays.asList(1,2))));
		}
		@Test
		public void groupedFunctionCollector(){
		    assertThat(of(1,2,3).grouped(f-> f<3? "a" : "b",Collectors.toList()).count(),equalTo((2L)));
		    assertThat(of(1,2,3).grouped(f-> f<3? "a" : "b",Collectors.toList()).filter(t->t.v1.equals("a"))
                    .map(t->t.v2).single(),
                        equalTo((Arrays.asList(1,2))));
		}
		
		@Test
		public void batchBySize(){
			System.out.println(of(1,2,3,4,5,6).grouped(3).collect(Collectors.toList()));
			assertThat(of(1,2,3,4,5,6).grouped(3).collect(Collectors.toList()).size(),is(2));
		}
		
		
		@Test
		public void prepend(){
		List<String> result = 	of(1,2,3).prepend(100,200,300)
				.map(it ->it+"!!").collect(Collectors.toList());

			assertThat(result,equalTo(Arrays.asList("100!!","200!!","300!!","1!!","2!!","3!!")));
		}	
		@Test
		public void append(){
		List<String> result = 	of(1,2,3).append(100,200,300)
				.map(it ->it+"!!").collect(Collectors.toList());

			assertThat(result,equalTo(Arrays.asList("1!!","2!!","3!!","100!!","200!!","300!!")));
		}
		@Test
		public void concatStreams(){
		List<String> result = 	of(1,2,3).appendS(of(100,200,300))
				.map(it ->it+"!!").collect(Collectors.toList());

			assertThat(result,equalTo(Arrays.asList("1!!","2!!","3!!","100!!","200!!","300!!")));
		}
		
		@Test
		public void prependStreams(){
		List<String> result = 	of(1,2,3).prependS(of(100,200,300))
				.map(it ->it+"!!").collect(Collectors.toList());

			assertThat(result,equalTo(Arrays.asList("100!!","200!!","300!!","1!!","2!!","3!!")));
		}
		@Test
		public void insertAt(){
		List<String> result = 	of(1,2,3).insertAt(1,100,200,300)
				.map(it ->it+"!!").collect(Collectors.toList());

			assertThat(result,equalTo(Arrays.asList("1!!","100!!","200!!","300!!","2!!","3!!")));
		}
		@Test
		public void insertAtStream(){
		List<String> result = 	of(1,2,3).insertAtS(1,of(100,200,300))
				.map(it ->it+"!!").collect(Collectors.toList());

			assertThat(result,equalTo(Arrays.asList("1!!","100!!","200!!","300!!","2!!","3!!")));
		}
		@Test
		public void deleteBetween(){
			List<String> result = 	of(1,2,3,4,5,6).deleteBetween(2,4)
				.map(it ->it+"!!").collect(Collectors.toList());

			assertThat(result,equalTo(Arrays.asList("1!!","2!!","5!!","6!!")));
		}
		
		@Test
		public void zip(){
			List<Tuple2<Integer,Integer>> list =
					of(1,2,3,4,5,6).zip(of(100,200,300,400))
													.peek(it -> System.out.println(it)).collect(Collectors.toList());
			
			List<Integer> right = list.stream().map(t -> t.v2).collect(Collectors.toList());
			assertThat(right,hasItem(100));
			assertThat(right,hasItem(200));
			assertThat(right,hasItem(300));
			assertThat(right,hasItem(400));
			
			List<Integer> left = list.stream().map(t -> t.v1).collect(Collectors.toList());
			assertThat(asList(1,2,3,4),equalTo(left));
			
			
		}
		
		@Test
		public void zip2of(){
			List<Tuple2<Integer,Integer>> list =of(1,2,3,4,5,6).zip(of(100,200,300,400)).peek(it -> System.out.println(it)).collect(Collectors.toList());
		
			List<Integer> right = list.stream().map(t -> t.v2).collect(Collectors.toList());
			assertThat(right,hasItem(100));
			assertThat(right,hasItem(200));
			assertThat(right,hasItem(300));
			assertThat(right,hasItem(400));
			
			List<Integer> left = list.stream().map(t -> t.v1).collect(Collectors.toList());
			assertThat(Arrays.asList(1,2,3,4,5,6),hasItem(left.get(0)));

		}
		@Test
		public void zipInOrder(){
			

				List<Tuple2<Integer,Integer>> list =  of(1,2,3,4,5,6).limit(6)
															.zip( of(100,200,300,400).limit(4))
															.collect(Collectors.toList());
				
				assertThat(list.get(0).v1,is(1));
				assertThat(list.get(0).v2,is(100));
				assertThat(list.get(1).v1,is(2));
				assertThat(list.get(1).v2,is(200));
				assertThat(list.get(2).v1,is(3));
				assertThat(list.get(2).v2,is(300));
				assertThat(list.get(3).v1,is(4));
				assertThat(list.get(3).v2,is(400));
			
			
			
		}

		@Test
		public void zipEmpty() throws Exception {
			
			
			final ReactiveSeq<Integer> zipped = empty.zip(this.<Integer>of(), (a, b) -> a + b);
			assertTrue(zipped.collect(Collectors.toList()).isEmpty());
		}

		@Test
		public void shouldReturnEmptySeqWhenZipEmptyWithNonEmpty() throws Exception {
			
			
			
			final ReactiveSeq<Integer> zipped = empty.zip(nonEmpty, (a, b) -> a + b);
			assertTrue(zipped.collect(Collectors.toList()).isEmpty());
		}

		@Test
		public void shouldReturnEmptySeqWhenZipNonEmptyWithEmpty() throws Exception {
			
			
			final ReactiveSeq<Integer> zipped = nonEmpty.zip(empty, (a, b) -> a + b);

			
			assertTrue(zipped.collect(Collectors.toList()).isEmpty());
		}

		@Test
		public void shouldZipTwoFiniteSequencesOfSameSize() throws Exception {
			
			final ReactiveSeq<String> first = of("A", "B", "C");
			final ReactiveSeq<Integer> second = of(1, 2, 3);

			
			final ReactiveSeq<String> zipped = first.zip(second, (a, b) -> a + b);

			
			assertThat(zipped.collect(Collectors.toList()),equalTo(asList("A1", "B2", "C3")));
		}

		

		@Test
		public void shouldTrimSecondFixedSeqIfLonger() throws Exception {
			final ReactiveSeq<String> first = of("A", "B", "C");
			final ReactiveSeq<Integer> second = of(1, 2, 3, 4);

			
			final ReactiveSeq<String> zipped = first.zip(second, (a, b) -> a + b);

			assertThat(zipped.collect(Collectors.toList()),equalTo(asList("A1", "B2", "C3")));
		}

		@Test
		public void shouldTrimFirstFixedSeqIfLonger() throws Exception {
			final ReactiveSeq<String> first = of("A", "B", "C","D");
			final ReactiveSeq<Integer> second = of(1, 2, 3);
			final ReactiveSeq<String> zipped = first.zip(second, (a, b) -> a + b);

			
			assertThat(zipped.collect(Collectors.toList()),equalTo(asList("A1", "B2", "C3")));
		}

		@Test
		public void limitWhileTest(){
			List<Integer> list = of(1,2,3,4,5,6).limitWhile(it -> it<4).peek(it -> System.out.println(it)).collect(Collectors.toList());

			System.out.println("List " + list);
			assertThat(list,hasItem(1));
			assertThat(list,hasItem(2));
			assertThat(list,hasItem(3));
			
			
			
		}

	 

	    

	    @Test
	    public void testReverse() {
	        assertThat( of(1, 2, 3).reverse().toList(), is(asList(3, 2, 1)));
	    }

	    @Test
	    public void testShuffle() {
	        Supplier<ReactiveSeq<Integer>> s = () ->of(1, 2, 3);

	        assertEquals(3, s.get().shuffle().toList().size());
	        assertThat(s.get().shuffle().toList(), hasItems(1, 2, 3));

	        
	    }

	    @Test
	    public void testCycle() {

	        assertEquals(asList(1, 2, 1, 2, 1, 2),of(1, 2).cycle().limit(6).toList());
	        assertEquals(asList(1, 2, 3, 1, 2, 3), of(1, 2, 3).cycle().limit(6).toList());
	    }
	    
	    @Test
	    public void testIterable() {
	        List<Integer> list = of(1, 2, 3).toCollection(LinkedList::new);

	        for (Integer i :of(1, 2, 3)) {
	            assertThat(list,hasItem(i));
	        }
	    }
		
		@Test
		public void testDuplicate(){
			 Tuple2<ReactiveSeq<Integer>, ReactiveSeq<Integer>> copies =of(1,2,3,4,5,6).duplicate();

			 assertTrue(copies.v1.anyMatch(i->i==2));
			 assertTrue(copies.v2.anyMatch(i->i==2));
		}
		
		

		
		   

			
		    @Test
		    public void testGroupByEager() {
		        Map<Integer, ListX<Integer>> map1 =of(1, 2, 3, 4).groupBy(i -> i % 2);
		        assertEquals(asList(2, 4), map1.get(0));
		        assertEquals(asList(1, 3), map1.get(1));
		        assertEquals(2, map1.size());

		     
		    }
		    

		    @Test
		    public void testJoin() {
		        assertEquals("123",of(1, 2, 3).join());
		        assertEquals("1, 2, 3", of(1, 2, 3).join(", "));
		        assertEquals("^1|2|3$", of(1, 2, 3).join("|", "^", "$"));
		    }

		    
		    @Test @Ignore //failing!
		    public void testOptional() {
		        assertEquals(asList(1),of(Optional.of(1)).toList());
		        assertEquals(asList(), of(Optional.empty()).toList());
		    }
		    @Test
		    public void testZipDifferingLength() {
		        List<Tuple2<Integer, String>> list = of(1, 2).zip(of("a", "b", "c", "d")).toList();

		        assertEquals(2, list.size());
		        assertTrue(asList(1,2).contains( list.get(0).v1));
		        assertTrue(""+list.get(1).v2,asList(1,2).contains( list.get(1).v1)); 
		        assertTrue(asList("a", "b", "c", "d").contains( list.get(0).v2));
		        assertTrue(asList("a", "b", "c", "d").contains( list.get(1).v2));
		       
		        
		    }

		    @Test
			public void zip2(){
				assertThat(of(1,2).zipS(of('a','b')).toList(),equalTo(ListX.of(tuple(1,'a'), tuple(2,'b'))));
			}
		    @Test
		    public void testZipWithIndex() {
			System.out.println();
		    	assertEquals(asList(), of().zipWithIndex().toList());
		        assertEquals(asList(tuple("a", 0L)), of("a").zip(of(0L)).toList());
		        assertEquals(asList(tuple("a", 0L)), of("a").zipWithIndex().toList());
		    	assertEquals(asList(new Tuple2("a", 0L), new Tuple2("b", 1L)), of("a", "b").zipWithIndex().toList());
		        assertEquals(asList(new Tuple2("a", 0L), new Tuple2("b", 1L), new Tuple2("c", 2L)), of("a", "b", "c").zipWithIndex().toList());
		    }

		   
		    @Test
		    public void testSkipWhile() {
		    	 Supplier<ReactiveSeq<Integer>> s = () -> of(1, 2, 3, 4, 5);

		         assertEquals(asList(1, 2, 3, 4, 5), s.get().skipWhile(i -> false).toList());
		         assertEquals(asList(3, 4, 5), s.get().skipWhile(i -> i % 3 != 0).toList());
		         assertEquals(asList(3, 4, 5), s.get().skipWhile(i -> i < 3).toList());
		         assertEquals(asList(4, 5), s.get().skipWhile(i -> i < 4).toList());
		         assertEquals(asList(), s.get().skipWhile(i -> true).toList());
		    }

		    @Test
		    public void testSkipUntil() {
		    	Supplier<ReactiveSeq<Integer>> s = () -> of(1, 2, 3, 4, 5);

		        assertEquals(asList(), s.get().skipUntil(i -> false).toList());
		        assertEquals(asList(3, 4, 5), s.get().skipUntil(i -> i % 3 == 0).toList());
		        assertEquals(asList(3, 4, 5), s.get().skipUntil(i -> i == 3).toList());
		        assertEquals(asList(4, 5), s.get().skipUntil(i -> i == 4).toList());
		        assertEquals(asList(1, 2, 3, 4, 5), s.get().skipUntil(i -> true).toList());
			  }

		    @Test
		    public void testSkipUntilWithNulls() {
		    	 Supplier<ReactiveSeq<Integer>> s = () -> ReactiveSeq.of(1, 2, null, 3, 4, 5);

		         assertEquals(asList(1, 2, null, 3, 4, 5), s.get().skipUntil(i -> true).toList());
		    }

		    @Test
		    public void testLimitWhile() {
		    	 Supplier<ReactiveSeq<Integer>> s = () -> ReactiveSeq.of(1, 2, 3, 4, 5);

		         assertEquals(asList(), s.get().limitWhile(i -> false).toList());
		         assertEquals(asList(1, 2), s.get().limitWhile(i -> i % 3 != 0).toList());
		         assertEquals(asList(1, 2), s.get().limitWhile(i -> i < 3).toList());
		         assertEquals(asList(1, 2, 3), s.get().limitWhile(i -> i < 4).toList());
		         assertEquals(asList(1, 2, 3, 4, 5), s.get().limitWhile(i -> true).toList());
		    }

		    @Test
		    public void testLimitUntil() {
		    	 assertEquals(asList(1, 2, 3, 4, 5),of(1, 2, 3, 4, 5).limitUntil(i -> false).toList());
		         assertEquals(asList(1, 2), of(1, 2, 3, 4, 5).limitUntil(i -> i % 3 == 0).toList());
		         assertEquals(asList(1, 2), of(1, 2, 3, 4, 5).limitUntil(i -> i == 3).toList());
		         assertEquals(asList(1, 2, 3), of(1, 2, 3, 4, 5).limitUntil(i -> i == 4).toList());
		         assertEquals(asList(), of(1, 2, 3, 4, 5).limitUntil(i -> true).toList());

		        
		        
		        assertEquals(asList(), of(1, 2, 3, 4, 5).limitUntil(i -> true).toList());
		    }

		    @Test
		    public void testLimitUntilWithNulls() {
		       

		        assertThat(of(1, 2, null, 3, 4, 5).limitUntil(i -> false).toList(),equalTo(asList(1, 2, null, 3, 4, 5)));
		    }

		    @Test
		    public void testPartition() {
		        Supplier<ReactiveSeq<Integer>> s = () -> of(1, 2, 3, 4, 5, 6);

		        assertEquals(asList(1, 3, 5), s.get().partition(i -> i % 2 != 0).v1.toList());
		        assertEquals(asList(2, 4, 6), s.get().partition(i -> i % 2 != 0).v2.toList());

		        assertEquals(asList(2, 4, 6), s.get().partition(i -> i % 2 == 0).v1.toList());
		        assertEquals(asList(1, 3, 5), s.get().partition(i -> i % 2 == 0).v2.toList());

		        assertEquals(asList(1, 2, 3), s.get().partition(i -> i <= 3).v1.toList());
		        assertEquals(asList(4, 5, 6), s.get().partition(i -> i <= 3).v2.toList());

		        assertEquals(asList(1, 2, 3, 4, 5, 6), s.get().partition(i -> true).v1.toList());
		        assertEquals(asList(), s.get().partition(i -> true).v2.toList());

		        assertEquals(asList(), s.get().partition(i -> false).v1.toList());
		        assertEquals(asList(1, 2, 3, 4, 5, 6), s.get().splitBy(i -> false).v2.toList());
		    }

		    @Test
		    public void testSplitAt() {
		    	for(int i=0;i<20;i++){
			        Supplier<ReactiveSeq<Integer>> s = () -> of(1, 2, 3, 4, 5, 6);
		
			   
			        assertEquals(asList(4, 5, 6), s.get().splitAt(3).v2.toList());
		
			  
		    	}
		    	for(int i=0;i<20;i++){
			        Supplier<ReactiveSeq<Integer>> s = () -> of(1, 2, 3, 4, 5, 6);
		
			     
			        assertEquals(asList(1, 2, 3), s.get().splitAt(3).v1.toList());
			       
		    	}
		    	for(int i=0;i<20;i++){
			        Supplier<ReactiveSeq<Integer>> s = () -> of(1, 2, 3, 4, 5, 6);
		
			   
			       assertEquals(asList(1, 2, 3, 4, 5, 6), s.get().splitAt(6).v1.toList());
			      	}
		    	for(int i=0;i<20;i++){
			        Supplier<ReactiveSeq<Integer>> s = () -> of(1, 2, 3, 4, 5, 6);
		
			   
			        assertEquals(asList(1, 2, 3, 4, 5, 6), s.get().splitAt(7).v1.toList());
			      	}
		    }
	@Test
	public void skipInvestigate() {
		System.out.println("0" + of(1, 2, 3).skip(0).toListX());

		assertThat(of(1, 2, 3).skip(0).toListX(), equalTo(ListX.of(1,2, 3)));
	}
	@Test
	public void splitAtInvestigate() {



		System.out.println("0" + of(1, 2, 3).splitAt(0).v2.toListX());

		assertThat(of(1, 2, 3).splitAt(0).v2.toListX(), equalTo(ListX.of(1,2, 3)));
	}

		    @Test
			public void splitAtHeadInvestigate(){
				System.out.println("0" + of(1, 2, 3).splitAt(0).v2.toListX());
				System.out.println(of(1, 2, 3).splitAtHead().v1);
				System.out.println(of(1, 2, 3).splitAtHead().v2.toListX());
				System.out.println(of(1, 2, 3).splitAtHead().v2.splitAtHead().v1);
				System.out.println(of(1, 2, 3).splitAtHead().v2.splitAtHead().v2.toListX());
				assertThat(of(1,2,3).splitAtHead().v2.toListX(),equalTo(ListX.of(2,3)));
			}

    @Test
    public void splitAtHeadImpl2(){
        final Tuple2<ReactiveSeq<Integer>, ReactiveSeq<Integer>> t = of(1).duplicate();

        assertThat(t.v1.limit(1).toList(),equalTo(ListX.of(1)));
        assertThat(t.v2.skip(1).toList(),equalTo(ListX.of()));

    }

    @Test
    public void limitReplay(){
        final ReactiveSeq<Integer> t = of(1).map(i->i).flatMap(i->Stream.of(i));
        assertThat(t.limit(1).toList(),equalTo(ListX.of(1)));
        assertThat(t.limit(1).toList(),equalTo(ListX.of(1)));
    }

    @Test
    public void duplicateReplay(){
        final Tuple2<ReactiveSeq<Integer>, ReactiveSeq<Integer>> t = of(1).duplicate();
        assertThat(t.v1.limit(1).toList(),equalTo(ListX.of(1)));
        assertThat(t.v1.limit(1).toList(),equalTo(ListX.of(1)));
    }
    @Test
    public void splitLimit(){
        ReactiveSeq<Integer> stream = of(1);
        final Tuple2<ReactiveSeq<Integer>, ReactiveSeq<Integer>> t = stream.duplicate();
        assertThat(stream.limit(1).toList(),equalTo(ListX.of(1)));
        assertThat(t.v1.limit(1).toList(),equalTo(ListX.of(1)));
        assertThat(t.v1.limit(1).toList(),equalTo(ListX.of(1)));
    }

			@Test
            public void splitAtHeadImpl(){

                final Tuple2<ReactiveSeq<Integer>, ReactiveSeq<Integer>> t = of(1).duplicate();



                Tuple2<ReactiveSeq<Integer>, ReactiveSeq<Integer>> dup = new Tuple2(
                        t.v1.limit(1), t.v2.skip(1));
                assertThat(t.v1.limit(1).toList(),equalTo(ListX.of(1)));
                assertThat(t.v2.skip(1).toList(),equalTo(ListX.of()));
                assertThat(dup.v1.toList(),equalTo(ListX.of(1)));
                assertThat(dup.v2.toList(),equalTo(ListX.of()));

            }
			@Test
			public void limitSkip(){
				ReactiveSeq<Integer> stream = of(1);
				Tuple2<ReactiveSeq<Integer>, ReactiveSeq<Integer>> dup = stream.duplicate();
				assertThat(dup.v1.limit(1).toList(),equalTo(ListX.of(1)));
                assertThat(dup.v2.skip(1).toList(),equalTo(ListX.of()));


                assertThat(Streamable.fromStream(dup.v1.limit(1)).toList(),equalTo(ListX.of(1)));
                assertThat(Streamable.fromStream(dup.v2.skip(1)).toList(),equalTo(ListX.of()));

			}
		    @Test
		    public void testSplitAtHead() {

		        assertEquals(Optional.empty(), of().splitAtHead().v1);
		        assertEquals(asList(), of().splitAtHead().v2.toList());

		        assertEquals(Optional.of(1), of(1).splitAtHead().v1);
		        assertEquals(asList(), of(1).splitAtHead().v2.toList());

		        assertEquals(Optional.of(1), of(1, 2).splitAtHead().v1);
		        assertEquals(asList(2), of(1, 2).splitAtHead().v2.toList());

		        assertEquals(Optional.of(1), of(1, 2, 3).splitAtHead().v1);
		        assertEquals(Optional.of(2), of(1, 2, 3).splitAtHead().v2.splitAtHead().v1);
		        assertEquals(Optional.of(3), of(1, 2, 3).splitAtHead().v2.splitAtHead().v2.splitAtHead().v1);
		        assertEquals(asList(2, 3), of(1, 2, 3).splitAtHead().v2.toList());
		        assertEquals(asList(3), of(1, 2, 3).splitAtHead().v2.splitAtHead().v2.toList());
		        assertEquals(asList(), of(1, 2, 3).splitAtHead().v2.splitAtHead().v2.splitAtHead().v2.toList());
		    }

		    @Test
		    public void testMinByMaxBy() {
		        Supplier<ReactiveSeq<Integer>> s = () -> of(1, 2, 3, 4, 5, 6);

		        assertEquals(1, (int) s.get().maxBy(t -> Math.abs(t - 5)).get());
		        assertEquals(5, (int) s.get().minBy(t -> Math.abs(t - 5)).get());

		        assertEquals(6, (int) s.get().maxBy(t -> "" + t).get());
		        assertEquals(1, (int) s.get().minBy(t -> "" + t).get());
		    }

		    @Test
		    public void testUnzip() {
		        Supplier<ReactiveSeq<Tuple2<Integer, String>>> s = () -> of(new Tuple2(1, "a"), new Tuple2(2, "b"), new Tuple2(3, "c"));

		        Tuple2<ReactiveSeq<Integer>, ReactiveSeq<String>> u1 = ReactiveSeq.unzip(s.get());
		        assertThat(u1.v1.toList(),equalTo(asList(1, 2, 3)));
		        assertThat(u1.v2.toList(),equalTo(asList("a", "b", "c")));

		        
		    }
		   

		    @Test
		    public void testFoldLeft() {
		        Supplier<ReactiveSeq<String>> s = () -> of("a", "b", "c");

		        assertTrue(s.get().foldLeft("", String::concat).contains("a"));
		        assertTrue(s.get().foldLeft("", String::concat).contains("b"));
		        assertTrue(s.get().foldLeft("", String::concat).contains("c"));
		       
		        assertEquals(3, (int) s.get().map(str->str.length()).foldLeft(0, (u, t) -> u + t));

		        
		        assertEquals(3, (int) s.get().map(str->str.length()).foldRight(0, (t, u) -> u + t));
		        assertEquals("-a-b-c", s.get().map(str->new StringBuilder(str)).foldLeft(new StringBuilder(), (u, t) -> u.append("-").append(t)).toString());
		    }
		    
		    @Test
		    public void testFoldRight(){
		    	 	Supplier<ReactiveSeq<String>> s = () -> of("a", "b", "c");

			        assertTrue(s.get().foldRight("", String::concat).equals("cba"));
			        assertTrue(s.get().foldRight("", String::concat).contains("b"));
			        assertTrue(s.get().foldRight("", String::concat).contains("c"));
			        assertEquals(3, (int) s.get().map(str->str.length()).foldRight(0, (t, u) -> u + t));
			        
		    }
		    
		   
		  //tests converted from lazy-seq suite
		    @Test
			public void flattenEmpty() throws Exception {
					assertTrue(this.<Integer>of().flatMap(i -> asList(i, -i).stream()).toList().isEmpty());
			}

			@Test
			public void flatten() throws Exception {
				assertThat(this.<Integer>of(1,2).flatMap(i -> asList(i, -i).stream()).toList(),equalTo(asList(1, -1, 2, -2)));		
			}

			

			@Test
			public void flattenEmptyStream() throws Exception {
				
				assertThat(this.<Integer>of(1,2,3,4,5,5,6,8,9,10).flatMap(this::flatMapFun).limit(10).collect(Collectors.toList()),
												equalTo(asList(2, 3, 4, 5, 6, 7, 0, 0, 0, 0)));
			}

			private  Stream<Integer> flatMapFun(int i) {
				if (i <= 0) {
					return Arrays.<Integer>asList().stream();
				}
				switch (i) {
					case 1:
						return asList(2).stream();
					case 2:
						return asList(3, 4).stream();
					case 3:
						return asList(5, 6, 7).stream();
					default:
						return asList(0, 0).stream();
				}
			}

		    
		
		
		
	}

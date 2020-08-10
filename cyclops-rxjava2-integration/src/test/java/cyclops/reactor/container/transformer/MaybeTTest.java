package cyclops.reactor.container.transformer;


import static cyclops.container.control.Option.some;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import com.oath.cyclops.ReactiveConvertableSequence;
import cyclops.container.printable.Printable;
import cyclops.container.mutable.Mutable;
import cyclops.ReactiveReducers;
import cyclops.function.companion.Reducers;
import cyclops.function.companion.Semigroups;
import cyclops.reactor.container.transformer.rx2.MaybeT;
import cyclops.stream.companion.Streams;
import cyclops.container.control.Option;
import cyclops.container.immutable.impl.Seq;
import cyclops.function.combiner.Monoid;
import cyclops.monads.AnyM;
import cyclops.monads.Witness.optional;
import cyclops.pure.reactive.collections.immutable.LinkedListX;
import cyclops.pure.reactive.collections.mutable.ListX;
import io.reactivex.Maybe;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.junit.Before;
import org.junit.Test;


public class MaybeTTest implements Printable {

    cyclops.reactor.container.transformer.rx2.MaybeT<optional, Integer> just;
    cyclops.reactor.container.transformer.rx2.MaybeT<optional, Integer> none;
    cyclops.reactor.container.transformer.rx2.MaybeT<optional, Integer> one;

    @Before
    public void setUp() throws Exception {

        just = cyclops.reactor.container.transformer.rx2.MaybeT.of(AnyM.ofNullable(Maybe.just(10)));
        none = cyclops.reactor.container.transformer.rx2.MaybeT.of(AnyM.ofNullable(null));
        one = MaybeT.of(AnyM.ofNullable(Maybe.just(1)));
    }

    @Test
    public void optionalVMaybe() {

        Optional.of(10)
                .map(i -> print("optional " + (i + 10)));

        Maybe.just(10)
             .map(i -> print("maybe " + (i + 10)));

    }


    private int add1(int i) {
        return i + 1;
    }

    @Test
    public void testOfT() {
        assertThat(cyclops.container.control.Maybe.of(1),
                   equalTo(cyclops.container.control.Maybe.of(1)));
    }


    @Test
    public void testOfNullable() {
        assertFalse(cyclops.container.control.Maybe.ofNullable(null)
                                                   .isPresent());
        assertThat(cyclops.container.control.Maybe.ofNullable(1),
                   equalTo(cyclops.container.control.Maybe.of(1)));

    }

    @Test
    public void testNarrow() {
        assertThat(cyclops.container.control.Maybe.ofNullable(1),
                   equalTo(cyclops.container.control.Maybe.narrow(cyclops.container.control.Maybe.of(1))));
    }


    @Test
    public void testMapFunctionOfQsuperTQextendsR() {
        assertThat(just.map(i -> i + 5)
                       .get(),
                   equalTo(some(15)));
        assertThat(none.map(i -> i + 5)
                       .orElse(1000),
                   equalTo(1000));
    }

    @Test
    public void testFlatMap() {

        assertThat(just.flatMap(i -> cyclops.container.control.Maybe.of(i + 5))
                       .get(),
                   equalTo(some(15)));
        assertThat(none.flatMap(i -> cyclops.container.control.Maybe.of(i + 5))
                       .orElse(-1),
                   equalTo(-1));
    }


    @Test
    public void testStream() {
        assertThat(just.stream()
                       .to(ReactiveConvertableSequence::converter)
                       .listX(),
                   equalTo(ListX.of(10)));
        assertThat(none.stream()
                       .to(ReactiveConvertableSequence::converter)
                       .listX(),
                   equalTo(ListX.of()));
    }

    @Test
    public void testOfSupplierOfT() {

    }


    @Test
    public void testMapReduceReducerOfE() {
        assertThat(just.foldMap(Reducers.toCountInt()),
                   equalTo(1));
    }

    @Test
    public void testFoldMonoidOfT() {

        assertThat(just.foldLeft(Reducers.toTotalInt()),
                   equalTo(10));
    }

    @Test
    public void testFoldTBinaryOperatorOfT() {
        assertThat(just.foldLeft(1,
                                 (a, b) -> a * b),
                   equalTo(10));
    }


    @Test
    public void testMkString() {
        assertThat(just.mkString(),
                   containsString("MaybeT[Optional[io.reactivex.internal.operators.maybe.MaybeJust@"));
        assertThat(none.mkString(),
                   equalTo("MaybeT[Optional.empty]"));
    }


    @Test
    public void testGet() {
        assertThat(just.get(),
                   equalTo(some(10)));
    }

    @Test
    public void testGetNone() {
        assertFalse(none.get()
                        .isPresent());

    }


    private int add(int a,
                    int b) {
        return a + b;
    }


    private int add3(int a,
                     int b,
                     int c) {
        return a + b + c;
    }

    private int add4(int a,
                     int b,
                     int c,
                     int d) {
        return a + b + c + d;
    }

    private int add5(int a,
                     int b,
                     int c,
                     int d,
                     int e) {
        return a + b + c + d + e;
    }


    @Test
    public void testMapReduceReducerOfR() {
        assertThat(just.foldMap(ReactiveReducers.toLinkedListX()),
                   equalTo(LinkedListX.of(10)));
    }

    @Test
    public void testMapReduceFunctionOfQsuperTQextendsRMonoidOfR() {
        assertThat(just.foldMap(s -> s.toString(),
                                Monoid.of("",
                                          Semigroups.stringJoin(","))),
                   equalTo(",10"));
    }

    @Test
    public void testReduceMonoidOfT() {
        assertThat(just.foldLeft(Monoid.of(1,
                                           Semigroups.intMult)),
                   equalTo(10));
    }

    @Test
    public void testReduceBinaryOperatorOfT() {
        assertThat(just.foldLeft((a, b) -> a + b),
                   equalTo(Option.of(10)));
    }

    @Test
    public void testReduceTBinaryOperatorOfT() {
        assertThat(just.foldLeft(10,
                                 (a, b) -> a + b),
                   equalTo(20));
    }

    @Test
    public void testReduceUBiFunctionOfUQsuperTUBinaryOperatorOfU() {
        assertThat(just.foldLeft(11,
                                 (a, b) -> a + b,
                                 (a, b) -> a * b),
                   equalTo(21));
    }

    @Test
    public void testReduceIterableOfReducerOfT() {
        Seq<Integer> countAndTotal = just.foldLeft(Arrays.asList(Reducers.toCountInt(),
                                                                 Reducers.toTotalInt()));
        assertThat(countAndTotal,
                   equalTo(Seq.of(1,
                                  10)));
    }


    @Test
    public void testFoldRightMonoidOfT() {
        assertThat(just.foldRight(Monoid.of(1,
                                            Semigroups.intMult)),
                   equalTo(10));
    }

    @Test
    public void testFoldRightTBinaryOperatorOfT() {
        assertThat(just.foldRight(10,
                                  (a, b) -> a + b),
                   equalTo(20));
    }

    @Test
    public void testFoldRightMapToType() {
        assertThat(just.foldMapRight(ReactiveReducers.toLinkedListX()),
                   equalTo(LinkedListX.of(10)));
    }


    @Test
    public void testOrElseGet() {
        assertThat(none.orElseGet(() -> 2),
                   equalTo(2));
        assertThat(just.orElseGet(() -> 2),
                   equalTo(10));
    }


    @Test
    public void testOrElse() {
        assertThat(none.orElse(20),
                   equalTo(20));
        assertThat(just.orElse(20),
                   equalTo(10));
    }


    @Test
    public void testIterator1() {
        assertThat(Streams.stream(just.iterator())
                          .collect(Collectors.toList()),
                   equalTo(Arrays.asList(10)));
    }

    @Test
    public void testForEach() {
        Mutable<Integer> capture = Mutable.of(null);
        none.forEach(c -> capture.set(c));
        assertNull(capture.get());
        just.forEach(c -> capture.set(c));
        assertThat(capture.get(),
                   equalTo(10));
    }

    @Test
    public void testSpliterator() {
        assertThat(StreamSupport.stream(just.spliterator(),
                                        false)
                                .collect(Collectors.toList()),
                   equalTo(Arrays.asList(10)));
    }


    @Test
    public void testMapFunctionOfQsuperTQextendsR1() {
        assertThat(just.map(i -> i + 5)
                       .get(),
                   equalTo(some(15)));
    }

    @Test
    public void testPeek() {
        Mutable<Integer> capture = Mutable.of(null);
        just = just.peek(c -> capture.set(c))
                   .map(i -> i + 2);

        just.get()
            .orElse(-1);
        assertThat(capture.get(),
                   equalTo(10));
    }


}

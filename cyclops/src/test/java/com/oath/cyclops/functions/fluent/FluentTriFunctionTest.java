package com.oath.cyclops.functions.fluent;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import cyclops.container.control.Option;
import cyclops.container.control.Try;
import cyclops.container.immutable.tuple.Tuple;
import cyclops.function.companion.FluentFunctions;
import cyclops.function.companion.FluentFunctions.FluentBiFunction;
import cyclops.function.companion.FluentFunctions.FluentFunction;
import cyclops.function.companion.FluentFunctions.FluentSupplier;
import cyclops.function.companion.FluentFunctions.FluentTriFunction;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;

public class FluentTriFunctionTest {

    int called;
    int set;
    int in;
    boolean out;
    int times = 0;
    Executor ex = Executors.newFixedThreadPool(1);

    @Before
    public void setup() {
        this.times = 0;
    }

    public int add(Integer a,
                   Integer b,
                   Integer c) {
        called++;
        return a + b + c;
    }

    @Test
    public void testApply() {

        assertThat(FluentFunctions.of(this::add)
                                  .name("myFunction")
                                  .println()
                                  .apply(10,
                                         1,
                                         0),
                   equalTo(11));

    }

    @Test
    public void testCache() {
        called = 0;
        FluentTriFunction<Integer, Integer, Integer, Integer> fn = FluentFunctions.of(this::add)
                                                                                  .name("myFunction")
                                                                                  .memoize3();

        fn.apply(10,
                 1,
                 0);
        fn.apply(10,
                 1,
                 0);
        fn.apply(10,
                 1,
                 0);

        assertThat(called,
                   equalTo(1));

    }

    @Test
    public void testCacheGuava() {
        Cache<Object, Integer> cache = CacheBuilder.newBuilder()
                                                   .maximumSize(1000)
                                                   .expireAfterWrite(10,
                                                                     TimeUnit.MINUTES)
                                                   .build();

        called = 0;
        FluentTriFunction<Integer, Integer, Integer, Integer> fn = FluentFunctions.of(this::add)
                                                                                  .name("myFunction")
                                                                                  .memoize3((key, f) -> cache.get(key,
                                                                                                                  () -> f.apply(key)));

        fn.apply(10,
                 1,
                 0);
        fn.apply(10,
                 1,
                 0);
        fn.apply(10,
                 1,
                 0);

        assertThat(called,
                   equalTo(1));

    }

    public boolean events(Integer i,
                          Integer a,
                          Integer c) {
        return set == i;
    }

    @Test
    public void testBefore() {
        set = 0;
        assertTrue(FluentFunctions.of(this::events)
                                  .before((a, b, c) -> set = a)
                                  .println()
                                  .apply(10,
                                         1,
                                         100));
    }

    @Test
    public void testAfter() {
        set = 0;
        assertFalse(FluentFunctions.of(this::events)
                                   .after((in1, in2, in3, out) -> set = in1)
                                   .println()
                                   .apply(10,
                                          1,
                                          0));

        boolean result = FluentFunctions.of(this::events)
                                        .after((inA2, inB2, inB3, out2) -> {
                                            in = inA2;
                                            out = out2;
                                        })
                                        .println()
                                        .apply(10,
                                               1,
                                               0);

        assertThat(in,
                   equalTo(10));
        assertTrue(out == result);
    }

    @Test
    public void testAround() {
        set = 0;
        assertThat(FluentFunctions.of(this::add)
                                  .around(advice -> advice.proceed1(advice.param1 + 1))
                                  .println()
                                  .apply(10,
                                         1,
                                         0),
                   equalTo(12));

    }

    public String exceptionalFirstTime(String input,
                                       String input2,
                                       String input3) throws IOException {
        if (times == 0) {
            times++;
            throw new IOException();
        }
        return input + " world" + input2 + input3;
    }

    @Test
    public void retry() {
        assertThat(FluentFunctions.ofChecked(this::exceptionalFirstTime)
                                  .println()
                                  .retry(2,
                                         500)
                                  .apply("hello",
                                         "woo!",
                                         "h"),
                   equalTo("hello worldwoo!h"));
    }

    @Test
    public void recover() {
        assertThat(FluentFunctions.ofChecked(this::exceptionalFirstTime)
                                  .recover(IOException.class,
                                           (in1, in2, in3) -> in1 + "boo!")
                                  .println()
                                  .apply("hello ",
                                         "woo!",
                                         "h"),
                   equalTo("hello boo!"));
    }

    @Test(expected = IOException.class)
    public void recoverDont() {
        assertThat(FluentFunctions.ofChecked(this::exceptionalFirstTime)
                                  .recover(RuntimeException.class,
                                           (in1, in2, in3) -> in1 + "boo!")
                                  .println()
                                  .apply("hello ",
                                         "woo!",
                                         "h"),
                   equalTo("hello boo!"));
    }

    public String gen(String input) {
        return input + System.currentTimeMillis();
    }

    @Test
    public void generate() {
        assertThat(FluentFunctions.of(this::gen)
                                  .println()
                                  .generate("next element")
                                  .onePer(1,
                                          TimeUnit.SECONDS)
                                  .limit(2)
                                  .toList()
                                  .size(),
                   equalTo(2));
    }

    @Test
    public void iterate() {
        FluentFunctions.of(this::add)
                       .iterate(1,
                                2,
                                3,
                                (i) -> Tuple.tuple(i,
                                                   i,
                                                   i))
                       .limit(2)
                       .printOut();
        assertThat(FluentFunctions.of(this::add)
                                  .iterate(1,
                                           2,
                                           3,
                                           (i) -> Tuple.tuple(i,
                                                              i,
                                                              i))
                                  .limit(2)
                                  .toList()
                                  .size(),
                   equalTo(2));
    }

    @Test
    public void testLift() {
        Integer nullValue = null;
        assertThat(FluentFunctions.of(this::add)
                                  .lift3()
                                  .apply(2,
                                         1,
                                         3),
                   equalTo(Option.some(6)));
    }

    @Test
    public void testTry() {

        Try<String, IOException> tried = FluentFunctions.ofChecked(this::exceptionalFirstTime)
                                                        .liftTry(IOException.class)
                                                        .apply("hello",
                                                               "boo!",
                                                               "r");

        if (tried.isSuccess()) {
            fail("expecting failure");
        }

    }

    @Test
    public void liftAsync() {
        assertThat(FluentFunctions.of(this::add)
                                  .liftAsync(ex)
                                  .apply(1,
                                         1,
                                         2)
                                  .join(),
                   equalTo(4));
    }

    @Test
    public void async() {
        assertThat(FluentFunctions.of(this::add)
                                  .async(ex)
                                  .thenApply(f -> f.apply(4,
                                                          1,
                                                          10))
                                  .join(),
                   equalTo(15));
    }

    @Test
    public void testPartiallyApply3() {
        FluentSupplier<Integer> supplier = FluentFunctions.of(this::add)
                                                          .partiallyApply(3,
                                                                          1,
                                                                          2)
                                                          .println();
        assertThat(supplier.get(),
                   equalTo(6));
    }

    @Test
    public void testPartiallyApply1() {
        FluentFunction<Integer, Integer> fn = FluentFunctions.of(this::add)
                                                             .partiallyApply(3,
                                                                             1)
                                                             .println();
        assertThat(fn.apply(1),
                   equalTo(5));
    }

    @Test
    public void testPartiallyApply() {
        FluentBiFunction<Integer, Integer, Integer> fn = FluentFunctions.of(this::add)
                                                                        .partiallyApply(3)
                                                                        .println();
        assertThat(fn.apply(1,
                            10),
                   equalTo(14));
    }

    @Test
    public void curry() {
        assertThat(FluentFunctions.of(this::add)
                                  .curry()
                                  .apply(1)
                                  .apply(2)
                                  .apply(10),
                   equalTo(13));
    }
}

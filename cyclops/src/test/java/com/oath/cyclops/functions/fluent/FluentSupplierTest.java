package com.oath.cyclops.functions.fluent;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import cyclops.control.Try;
import cyclops.function.companion.FluentFunctions;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import org.junit.Before;
import org.junit.Test;

public class FluentSupplierTest {

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

    public int getOne() {
        called++;
        return 1;
    }

    @Test
    public void testGet() {

        assertThat(FluentFunctions.of(this::getOne)
                                  .name("mySupplier")
                                  .println()
                                  .get(),
                   equalTo(1));

    }

    @Test
    public void testCache() {
        called = 0;
        Supplier<Integer> fn = FluentFunctions.of(this::getOne)
                                              .name("myFunction")
                                              .memoize();

        fn.get();
        fn.get();
        fn.get();

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
        Supplier<Integer> fn = FluentFunctions.of(this::getOne)
                                              .name("myFunction")
                                              .memoize((key, f) -> cache.get(key,
                                                                             () -> f.apply(key)));
        fn.get();
        fn.get();
        fn.get();

        assertThat(called,
                   equalTo(1));


    }

    public boolean events() {
        return set == 10;

    }

    @Test
    public void testBefore() {
        set = 0;
        assertTrue(FluentFunctions.of(this::events)
                                  .before(() -> set = 10)
                                  .println()
                                  .get());
    }

    @Test
    public void testAfter() {
        set = 0;
        assertFalse(FluentFunctions.of(this::events)
                                   .after(out -> out = true)
                                   .println()
                                   .get());

        boolean result = FluentFunctions.of(this::events)
                                        .after((out2) -> {
                                            out = out2;
                                        })
                                        .println()
                                        .get();

        assertTrue(out == result);
    }

    @Test
    public void testAround() {
        set = 0;
        assertThat(FluentFunctions.of(this::getOne)
                                  .around(advice -> advice.proceed())
                                  .println()
                                  .get(),
                   equalTo(1));


    }

    public String exceptionalFirstTime() throws IOException {
        if (times == 0) {
            times++;
            throw new IOException();
        }
        return "hello world";
    }

    @Test
    public void retry() {
        assertThat(FluentFunctions.ofChecked(this::exceptionalFirstTime)
                                  .println()
                                  .retry(2,
                                         500)
                                  .get(),
                   equalTo("hello world"));
    }

    @Test
    public void recover() {
        assertThat(FluentFunctions.ofChecked(this::exceptionalFirstTime)
                                  .recover(IOException.class,
                                           () -> "hello boo!")
                                  .println()
                                  .get(),
                   equalTo("hello boo!"));
    }

    @Test(expected = IOException.class)
    public void recoverDont() {
        assertThat(FluentFunctions.ofChecked(this::exceptionalFirstTime)
                                  .recover(RuntimeException.class,
                                           () -> "hello boo!")
                                  .println()
                                  .get(),
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
    public void testLift() {

        FluentFunctions.of(this::getOne)
                       .lazyLift()
                       .get();
    }

    @Test
    public void testTry() {

        Try<String, IOException> tried = FluentFunctions.ofChecked(this::exceptionalFirstTime)
                                                        .liftTry(IOException.class)
                                                        .get();

        if (tried.isSuccess()) {
            fail("expecting failure");
        }

    }

    @Test
    public void liftAsync() {
        assertThat(FluentFunctions.of(this::getOne)
                                  .liftAsync(ex)
                                  .get()
                                  .join(),
                   equalTo(1));
    }

    @Test
    public void async() {
        assertThat(FluentFunctions.of(this::getOne)
                                  .async(ex)
                                  .thenApply(f -> f.get())
                                  .join(),
                   equalTo(1));
    }


}

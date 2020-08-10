package com.oath.cyclops.jackson;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import cyclops.container.LazySeq;
import cyclops.container.Seq;
import cyclops.container.Vector;
import cyclops.pure.reactive.ReactiveSeq;
import cyclops.reactive.collection.container.mutable.ListX;
import org.junit.Test;

public class IterableXTest {

    @Test
    public void seq() {
        System.out.println(JacksonUtil.serializeToJson(Seq.of(1,
                                                              2,
                                                              3)));
        Seq<Integer> s = JacksonUtil.convertFromJson(JacksonUtil.serializeToJson(Seq.of(1,
                                                                                        2,
                                                                                        3)),
                                                     Seq.class);
        assertThat(s,
                   equalTo(Seq.of(1,
                                  2,
                                  3)));
    }

    @Test
    public void lazySeq() {
        System.out.println(JacksonUtil.serializeToJson(LazySeq.of(1,
                                                                  2,
                                                                  3)));
        LazySeq<Integer> s = JacksonUtil.convertFromJson(JacksonUtil.serializeToJson(LazySeq.of(1,
                                                                                                2,
                                                                                                3)),
                                                         LazySeq.class);
        assertThat(s,
                   equalTo(LazySeq.of(1,
                                      2,
                                      3)));
    }

    @Test
    public void vector() {
        System.out.println(JacksonUtil.serializeToJson(Vector.of(1,
                                                                 2,
                                                                 3)));
        Vector<Integer> s = JacksonUtil.convertFromJson(JacksonUtil.serializeToJson(Vector.of(1,
                                                                                              2,
                                                                                              3)),
                                                        Vector.class);
        assertThat(s,
                   equalTo(Vector.of(1,
                                     2,
                                     3)));
    }

    @Test
    public void listX() {
        System.out.println(JacksonUtil.serializeToJson(ListX.of(1,
                                                                2,
                                                                3)));
        ListX<Integer> s = JacksonUtil.convertFromJson(JacksonUtil.serializeToJson(ListX.of(1,
                                                                                            2,
                                                                                            3)),
                                                       ListX.class);
        assertThat(s,
                   equalTo(ListX.of(1,
                                    2,
                                    3)));
    }

    @Test
    public void reactiveSeq() {
        System.out.println(JacksonUtil.serializeToJson(ReactiveSeq.of(1,
                                                                      2,
                                                                      3)));
        ReactiveSeq<Integer> s = JacksonUtil.convertFromJson(JacksonUtil.serializeToJson(ReactiveSeq.of(1,
                                                                                                        2,
                                                                                                        3)),
                                                             ReactiveSeq.class);
        assertThat(s.toList(),
                   equalTo(ReactiveSeq.of(1,
                                          2,
                                          3)
                                      .toList()));
    }


}

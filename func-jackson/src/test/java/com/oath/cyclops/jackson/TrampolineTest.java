package com.oath.cyclops.jackson;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import cyclops.container.control.Trampoline;
import org.junit.Test;

public class TrampolineTest {

    Trampoline<Integer> some = Trampoline.done(10);

    @Test
    public void roundTrip() {

        String json = JacksonUtil.serializeToJson(Trampoline.done(10));
        System.out.println("Json " + json);
        Trampoline<Integer> des = JacksonUtil.convertFromJson(json,
                                                              Trampoline.class);

        assertThat(des.get(),
                   equalTo(some.get()));
    }

    @Test
    public void some() {
        assertThat(JacksonUtil.serializeToJson(Trampoline.done(5)),
                   equalTo("5"));
    }


}

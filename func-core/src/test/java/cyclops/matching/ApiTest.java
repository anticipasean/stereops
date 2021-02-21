package cyclops.matching;

import static cyclops.function.companion.Predicates.any;
import static cyclops.function.companion.Predicates.eq;
import static cyclops.pattern.Api.Any;
import static cyclops.pattern.Api.Case;
import static cyclops.pattern.Api.Match;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cyclops.matching.sample.Pet.Dog;
import cyclops.container.control.option.Option;
import org.junit.Test;

//import static cyclops.matching.matching.sample.Book.BookPatterns.Author;
//import static cyclops.matching.matching.sample.Book.BookPatterns.Name;
//import cyclops.matching.matching.sample.Book;

public class ApiTest {

    @Test
    public void shouldMatchSimpleObject() {
        Option<String> of = Match("b").with(Case(t -> t.equals("a"),
                                                 () -> "a"),
                                            Case(t -> t.equals("b"),
                                                 () -> "b"),
                                            Case(t -> t.equals("c"),
                                                 () -> "c"));
        assertTrue(of.isPresent());
        assertEquals("b",
                     of.orElse("c"));
    }

    @Test
    public void shouldMatchTheAnyOverride() {
        String of = Match("z").with(Case(t -> t.equals("a"),
                                         () -> "a"),
                                    Case(t -> t.equals("b"),
                                         () -> "b"),
                                    Case(t -> t.equals("c"),
                                         () -> "c"),
                                    Any(() -> "any"));
        assertEquals("any",
                     of);
    }

    @Test
    public void shouldProvideAllOptionalConvenience() {
        String of = Match("z").with(Case(t -> t.equals("a"),
                                         () -> "a"),
                                    Case(t -> t.equals("b"),
                                         () -> "b"),
                                    Case(t -> t.equals("c"),
                                         () -> "c"))
                              .orElse("orElse");
        assertEquals("orElse",
                     of);
    }

    @Test
    public void shouldSupportThirdPartyPredicates() {
        String of = Match("b").with(Case(eq("a"),
                                         () -> "a"),
                                    Case(eq("b"),
                                         () -> "b"),
                                    Case(eq("c"),
                                         () -> "c"))
                              .orElse("none");
        assertEquals("b",
                     of);
    }

    @Test
    public void shouldMatchDeconstruct3() {
        Dog dog = new Dog("bob",
                          3,
                          "M");
        Long of = Match(dog).with(Case(eq("bob"),
                                       any(),
                                       eq("F"),
                                       () -> 1L),
                                  Case(eq("not_bob"),
                                       eq(3),
                                       eq("M"),
                                       () -> 2L),
                                  Case(eq("bob"),
                                       eq(3),
                                       eq("M"),
                                       () -> 3L),
                                  Case(any(),
                                       any(),
                                       any(),
                                       () -> 4L))
                            .orElse(0L);
        assertEquals((Long) 3L,
                     of);
    }

    @Test
    public void shouldSupportAnyForDeconstruct() {
        Dog dog = new Dog("bob",
                          3,
                          "M");
        Long of = Match(dog).with(Case(eq("bob"),
                                       any(),
                                       eq("F"),
                                       () -> 1L),
                                  Case(eq("bob"),
                                       eq(10),
                                       eq("F"),
                                       () -> 1L),
                                  Any(() -> 0L));
        assertEquals((Long) 0L,
                     of);
    }

    /**
     @Test public void shouldSupportPatternDSL() {
     Book book = new Book("Chu's Day", "Neil Gaiman");
     Long with = Match(book).with(
     Case(Name("Chu's Day"), Author("Unknown"), () -> 1L),
     Case(Name("Chu's Day"), () -> 1L),
     Any(() -> 0L)
     );
     assertEquals((Long) 1L, with);
     }
     **/


}

package cyclops.reactive.collection.function;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import cyclops.container.immutable.ImmutableList;
import cyclops.container.immutable.impl.Seq;
import cyclops.reactive.collection.companion.MapXs;
import cyclops.reactive.collection.container.ReactiveConvertableSequence;
import cyclops.reactive.collection.container.mutable.ListX;
import cyclops.reactive.collection.container.mutable.MapX;
import cyclops.reactive.collection.container.mutable.SetX;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

public class ClojureOrJava8 {

    public void cyclopsReactTransformList() {

        ListX<Integer> org = ListX.of(10,
                                      20,
                                      30)
                                  .take(1);
        List<Integer> mapped = org.map(i -> i * 2);

    }

    public void java8TransformList() {

        List<Integer> org = Arrays.asList(10,
                                          20,
                                          30);
        List<Integer> mapped = org.stream()
                                  .map(i -> i * 2)
                                  .collect(Collectors.toList());


    }

    private String loadData(int d) {
        return "s";
    }

    public String processJob(String job) {
        return job;

    }

    @Test
    public void groupedSet() {
        SetX.of(10,
                20,
                30,
                40,
                50)
            .grouped(2)
            .printOut();


    }

    public MapX<Integer, ListX<Person>> cyclopsJava8(ListX<Person> people) {

        return MapX.fromPersistentMap(people.groupBy(Person::getAge)
                                            .map(v -> v.to(ReactiveConvertableSequence::converter)
                                                       .listX()));


    }

    public Map<Integer, List<Person>> plainJava8(List<Person> people) {
        return people.stream()
                     .collect(Collectors.groupingBy(Person::getAge));
    }

    public void transformMap() {

        MapX<String, String> x = MapX.fromMap(MapXs.of("hello",
                                                       "1"));

        MapX<String, Integer> y = x.map(Integer::parseInt);

        MapX<String, Integer> y2 = MapX.fromMap(MapXs.of("hello",
                                                         "1"))
                                       .map(Integer::parseInt);

    }

    public void transformMapJava8() {

        Map<String, String> x = MapXs.of("hello",
                                         "1");

        Map<String, Integer> y = x.entrySet()
                                  .stream()
                                  .collect(Collectors.toMap(e -> e.getKey(),
                                                            e -> Integer.parseInt(e.getValue())));

    }

    @Test
    public void listToString() {
        assertThat(listToString(Seq.of("a",
                                       "b",
                                       "c")),
                   equalTo("a b c "));
    }

    public String listToString(ImmutableList<String> list) {

        return list.fold(((x, xs) -> x + " " + listToString(xs)),
                         () -> "");

    }

    @AllArgsConstructor
    @Getter
    static class Person {

        int age;
    }

}


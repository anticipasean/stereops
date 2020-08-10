package cyclops.container;

import static cyclops.container.EnumerationTest.Days.Monday;
import static cyclops.container.EnumerationTest.Days.Thursday;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import cyclops.container.immutable.impl.Enumeration;
import org.junit.Test;

/**
 * Created by johnmcclean on 29/07/2017.
 */
public class EnumerationTest {

    @Test
    public void monday() {

        assertThat(Enumeration.enums(Days.values())
                              .stream(Monday)
                              .join(" "),
                   equalTo("Monday Tuesday Wednesday Thursday Friday Saturday Sunday"));

        assertThat(Enumeration.enums(Days.values())
                              .stream(Thursday)
                              .join(" "),
                   equalTo("Thursday Friday Saturday Sunday"));
    }

    @Test
    public void succOrElse() {
        assertThat(Enumeration.enums(Days.values())
                              .succOrElse(Days.Sunday,
                                          Days.Monday),
                   equalTo(Days.Monday));
    }

    @Test
    public void succOrElseGet() {
        assertThat(Enumeration.enums(Days.values())
                              .succOrElseGet(Days.Sunday,
                                             () -> Days.Monday),
                   equalTo(Days.Monday));
    }

    @Test
    public void predOrElse() {
        assertThat(Enumeration.enums(Days.values())
                              .predOrElse(Days.Monday,
                                          Days.Sunday),
                   equalTo(Days.Sunday));
    }

    @Test
    public void predOrElseGet() {
        assertThat(Enumeration.enums(Days.values())
                              .predOrElseGet(Days.Monday,
                                             () -> Days.Sunday),
                   equalTo(Days.Sunday));
    }

    public enum Days {
        Monday,
        Tuesday,
        Wednesday,
        Thursday,
        Friday,
        Saturday,
        Sunday
    }
}

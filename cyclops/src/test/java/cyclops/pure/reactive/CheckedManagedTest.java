package cyclops.pure.reactive;

import static cyclops.pure.reactive.ManagedTest.acquireNamed;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import cyclops.async.Future;
import cyclops.container.control.Try;
import cyclops.pure.reactive.ManagedTest.Resource;
import cyclops.reactive.Managed;
import org.junit.Before;
import org.junit.Test;

public class CheckedManagedTest {


    Resource resource;

    @Before
    public void setup() {

        resource = new Resource();
    }

    public Future<String> printUsing(Future<String> a) throws Exception {
        System.out.println("UsingX " + a.getOrElse(""));
        return a;
    }

    public Managed<Future<String>> acquire(Future<String> i) throws Exception {
        return acquireNamed(i.getOrElse("") + " world").map(b -> {
            System.out.println("UsingY " + b.getOrElse(""));
            return b;
        });
    }

    @Test
    public void flatMap() throws InterruptedException {
        Try<Future<String>, Throwable> t = acquireNamed("hello").checkedMap(this::printUsing)
                                                                .checkedFlatMap(this::acquire)
                                                                .run();
        System.out.println(t);
        assertTrue(t.isSuccess());
        assertThat(t.orElse(null)
                    .orElse("-1"),
                   equalTo("HELLO WORLD"));

    }

    @Test
    public void checked() {
        Managed.checked(this::autcloseable)
               .forEach(__ -> {
                        },
                        e -> e.printStackTrace());
        assertFalse(resource.open);

    }


    private Resource autcloseable() {
        return resource;
    }


}

package cyclops.monads.data;

import com.oath.cyclops.anym.AnyMSeq;
import cyclops.container.immutable.impl.LazySeq;
import cyclops.monads.AnyM;
import cyclops.monads.Witness.lazySeq;
import cyclops.monads.collections.AbstractAnyMSeqOrderedDependentTest;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LazySeqAnyMTest extends AbstractAnyMSeqOrderedDependentTest<lazySeq> {

	@Override
	public <T> AnyMSeq<lazySeq,T> of(T... values) {
		return AnyM.fromLazySeq(LazySeq.of(values));
	}

	@Override
	public <T> AnyMSeq<lazySeq,T> empty() {
		return AnyM.fromLazySeq(LazySeq.empty());
	}



    @Test @Ignore //only works for lazy data types
    public void testRecover1(){

     }
    @Test  @Ignore //only works for lazy data types
    public void testRecover2(){
    }

}


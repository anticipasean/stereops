package cyclops.monads.collections.mutable;

import static org.junit.Assert.assertThat;

import com.oath.cyclops.anym.AnyMSeq;
import cyclops.monads.Witness.list;
import cyclops.monads.collections.AbstractAnyMSeqOrderedDependentTest;

import cyclops.monads.AnyM;
import cyclops.pure.reactive.collections.mutable.ListX;

public class ListXTest extends AbstractAnyMSeqOrderedDependentTest<list> {

	@Override
	public <T> AnyMSeq<list,T> of(T... values) {
		return AnyM.fromList(ListX.of(values));
	}

	@Override
	public <T> AnyMSeq<list,T> empty() {
		return AnyM.fromList(ListX.empty());
	}


}


package cyclops.monads.collections.persistent;

import com.oath.cyclops.anym.AnyMSeq;
import cyclops.pure.reactive.collections.immutable.LinkedListX;
import cyclops.pure.reactive.collections.mutable.ListX;
import cyclops.monads.AnyM;
import cyclops.monads.Witness.linkedListX;
import cyclops.monads.collections.AbstractAnyMSeqOrderedDependentTest;

import static org.junit.Assert.assertThat;

public class LinkedListXTest extends AbstractAnyMSeqOrderedDependentTest<linkedListX> {

	@Override
	public <T> AnyMSeq<linkedListX,T> of(T... values) {
		return AnyM.fromLinkedListX(LinkedListX.of(values));
	}

	@Override
	public <T> AnyMSeq<linkedListX,T> empty() {
		return AnyM.fromLinkedListX(LinkedListX.empty());
	}



}


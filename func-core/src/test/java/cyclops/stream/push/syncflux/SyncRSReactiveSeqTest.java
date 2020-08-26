package cyclops.stream.push.syncflux;

import cyclops.reactive.ReactiveSeq;
import cyclops.reactive.companion.Spouts;
import cyclops.stream.AbstractReactiveSeqTest;

public class SyncRSReactiveSeqTest extends AbstractReactiveSeqTest {

    @Override
    public ReactiveSeq<Integer> of(Integer... values) {
        return Spouts.of(values);
    }

    @Override
    public ReactiveSeq<Integer> empty() {
        return Spouts.empty();
    }


}

package cyclops.streams.push.syncflux;

import cyclops.reactive.ReactiveSeq;
import cyclops.reactive.Spouts;
import cyclops.streams.AbstractReactiveSeqTest;

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

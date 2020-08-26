package cyclops.rxjava2.container.higherkinded;

import com.oath.cyclops.anym.extensability.MonadAdapter;
import cyclops.monads.AnyM;
import cyclops.monads.WitnessType;
import cyclops.rxjava2.adapter.FlowableAdapter;
import cyclops.rxjava2.adapter.impl.FlowableReactiveSeqImpl;
import cyclops.rxjava2.adapter.MaybeAdapter;
import cyclops.rxjava2.adapter.ObservableAdapter;
import cyclops.rxjava2.adapter.ObservableReactiveSeqImpl;
import cyclops.rxjava2.adapter.SingleAdapter;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Deprecated
public interface Rx2Witness {

    public static <T> Maybe<T> maybe(AnyM<maybe, ? extends T> anyM) {
        return anyM.unwrap();

    }

    public static <T> Flowable<T> flowable(AnyM<flowable, ? extends T> anyM) {
        FlowableReactiveSeqImpl<T> obs = anyM.unwrap();
        return obs.getFlowable();
    }

    public static <T> Single<T> single(AnyM<single, ? extends T> anyM) {
        return anyM.unwrap();

    }

    public static <T> Observable<T> observable(AnyM<observable, ? extends T> anyM) {
        ObservableReactiveSeqImpl<T> obs = anyM.unwrap();
        return obs.getObservable();
    }

    public static enum maybe implements MaybeWitness<maybe> {
        INSTANCE;

        @Override
        public MonadAdapter<maybe> adapter() {
            return new MaybeAdapter();
        }

    }

    public static enum flowable implements FlowableWitness<flowable> {
        INSTANCE;

        @Override
        public MonadAdapter<flowable> adapter() {
            return new FlowableAdapter();
        }

    }

    public static enum single implements SingleWitness<single> {
        INSTANCE;

        @Override
        public MonadAdapter<single> adapter() {
            return new SingleAdapter();
        }

    }

    public static enum observable implements ObservableWitness<observable> {
        INSTANCE;

        @Override
        public MonadAdapter<observable> adapter() {
            return new ObservableAdapter();
        }

    }

    static interface MaybeWitness<W extends MaybeWitness<W>> extends WitnessType<W> {

    }


    static interface FlowableWitness<W extends FlowableWitness<W>> extends WitnessType<W> {

    }

    static interface SingleWitness<W extends SingleWitness<W>> extends WitnessType<W> {

    }

    static interface ObservableWitness<W extends ObservableWitness<W>> extends WitnessType<W> {

    }

}

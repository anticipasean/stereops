package cyclops.reactor.container.higherkinded;


import com.oath.cyclops.anym.extensability.MonadAdapter;
import cyclops.monads.AnyM;
import cyclops.monads.WitnessType;
import cyclops.reactor.adapter.FluxAdapter;
import cyclops.reactor.adapter.MonoAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactorWitness {

    static <T> Flux<T> flux(AnyM<flux, ? extends T> anyM) {
        return anyM.unwrap();
    }

    static <T> Mono<T> mono(AnyM<mono, ? extends T> anyM) {
        return anyM.unwrap();
    }

    enum flux implements FluxWitness<ReactorWitness.flux> {
        INSTANCE;

        @Override
        public MonadAdapter<flux> adapter() {
            return new FluxAdapter();
        }

    }

    enum mono implements MonoWitness<mono> {
        INSTANCE;

        @Override
        public MonadAdapter<mono> adapter() {
            return new MonoAdapter();
        }

    }

    interface FluxWitness<W extends ReactorWitness.FluxWitness<W>> extends WitnessType<W> {

    }

    interface MonoWitness<W extends ReactorWitness.MonoWitness<W>> extends WitnessType<W> {

    }
}

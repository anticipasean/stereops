package cyclops.reactive.policy;

import cyclops.container.control.option.Option;

public enum BufferOverflowPolicy {
    DROP,
    BLOCK;

    public <T> Option<T> match(T value) {
        return this == DROP ? Option.none() : Option.some(value);
    }
}

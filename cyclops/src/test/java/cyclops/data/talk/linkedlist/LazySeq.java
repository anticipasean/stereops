package cyclops.data.talk.linkedlist;

import java.util.function.Supplier;
import lombok.AllArgsConstructor;

public interface LazySeq<E> {

    @AllArgsConstructor
    static class Cons<E> implements LazySeq<E> {

        private final Supplier<E> head;
        private final Supplier<LazySeq<E>> tail;

        public E head() {
            return head.get();
        }

    }

    static class Nil<E> implements Seq<E> {

    }

}



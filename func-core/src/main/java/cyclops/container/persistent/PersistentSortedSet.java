package cyclops.container.persistent;


import cyclops.container.control.eager.option.Option;
import cyclops.container.persistent.views.SortedSetView;
import java.util.Comparator;

public interface PersistentSortedSet<T> extends PersistentSet<T> {

    PersistentSortedSet<T> plus(T e);

    PersistentSortedSet<T> plusAll(Iterable<? extends T> list);

    PersistentSortedSet<T> removeValue(T e);

    PersistentSortedSet<T> removeAll(Iterable<? extends T> list);

    Option<T> get(int index);

    Comparator<? super T> comparator();

    default SortedSetView<T> sortedSetView() {
        return new SortedSetView.Impl<>(this);
    }
}

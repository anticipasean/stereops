package cyclops.container.persistent;


import cyclops.container.persistent.views.QueueView;

public interface PersistentQueue<T> extends PersistentIndexed<T> {


    PersistentQueue<T> minus();

    PersistentQueue<T> plus(T e);

    PersistentQueue<T> plusAll(Iterable<? extends T> list);


    public PersistentQueue<T> removeValue(T e);

    public PersistentQueue<T> removeAll(Iterable<? extends T> list);


    default QueueView<T> queueView() {
        return new QueueView.Impl<>(this);
    }

}

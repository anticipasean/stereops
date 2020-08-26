package cyclops.container.immutable.impl.base.redblacktree;

import cyclops.container.control.Option;
import cyclops.container.foldable.Deconstructable.Deconstructable5;
import cyclops.container.immutable.tuple.Tuple;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.container.immutable.tuple.Tuple3;
import cyclops.container.immutable.tuple.Tuple5;
import cyclops.reactive.ReactiveSeq;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public final class Node<K, V> implements Tree<K, V>, Deconstructable5<Boolean, Tree<K, V>, Tree<K, V>, K, V> {

    private static final long serialVersionUID = 1L;
    private final boolean isBlack;
    private final Tree<K, V> left;
    private final Tree<K, V> right;
    private final K key;
    private final V value;
    private final Comparator<K> comp;

    public Node(boolean isBlack,
                Tree<K, V> left,
                Tree<K, V> right,
                K key,
                V value,
                Comparator<K> comp) {
        this.isBlack = isBlack;
        this.left = left;
        this.right = right;
        this.key = key;
        this.value = value;
        this.comp = comp;
    }

    static <K, V> cyclops.container.immutable.impl.base.redblacktree.Node<K, V> RED(Tree<K, V> left,
                                                                                    Tree<K, V> right,
                                                                                    K key,
                                                                                    V value,
                                                                                    Comparator<? super K> comp) {
        return new cyclops.container.immutable.impl.base.redblacktree.Node(false,
                                                                           left,
                                                                           right,
                                                                           key,
                                                                           value,
                                                                           comp);
    }

    static <K, V> cyclops.container.immutable.impl.base.redblacktree.Node<K, V> BLACK(Tree<K, V> left,
                                                                                      Tree<K, V> right,
                                                                                      K key,
                                                                                      V value,
                                                                                      Comparator<? super K> comp) {
        return new cyclops.container.immutable.impl.base.redblacktree.Node(true,
                                                                           left,
                                                                           right,
                                                                           key,
                                                                           value,
                                                                           comp);
    }

    static <K, V> cyclops.container.immutable.impl.base.redblacktree.Node<K, V> LEFT_BLACK(Tree<K, V> left,
                                                                                           Tree<K, V> right,
                                                                                           K key,
                                                                                           V value,
                                                                                           Comparator<? super K> comp) {
        return new cyclops.container.immutable.impl.base.redblacktree.Node(true,
                                                                           left,
                                                                           right,
                                                                           key,
                                                                           value,
                                                                           comp);
    }

    static <K, V> cyclops.container.immutable.impl.base.redblacktree.Node<K, V> RIGHT_BLACK(Tree<K, V> left,
                                                                                            Tree<K, V> right,
                                                                                            K key,
                                                                                            V value,
                                                                                            Comparator<? super K> comp) {
        return new cyclops.container.immutable.impl.base.redblacktree.Node(true,
                                                                           left,
                                                                           right,
                                                                           key,
                                                                           value,
                                                                           comp);
    }

    public Tree<K, V> left() {
        return left;
    }


    public Tree<K, V> right() {
        return right;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isBlack() {
        return isBlack;
    }

    @Override
    public Option<V> get(K key) {
        int compRes = comp.compare(this.key,
                                   key);
        if (compRes > 0) {
            return left.get(key);
        } else if (compRes == 0) {
            return Option.of(value);
        }
        return right.get(key);
    }

    @Override
    public V getOrElse(K key,
                       V alt) {
        int compRes = comp.compare(this.key,
                                   key);
        if (compRes > 0) {
            return left.getOrElse(key,
                                  alt);
        } else if (compRes == 0) {
            return value;
        }
        return right.getOrElse(key,
                               alt);
    }

    @Override
    public V getOrElseGet(K key,
                          Supplier<? extends V> alt) {
        int compRes = comp.compare(this.key,
                                   key);
        if (compRes > 0) {
            return left.getOrElseGet(key,
                                     alt);
        } else if (compRes == 0) {
            return value;
        }
        return right.getOrElseGet(key,
                                  alt);
    }

    @Override
    public Tree<K, V> plus(K key,
                           V value) {

        int compRes = comp.compare(this.key,
                                   key);
        if (compRes > 0) {
            return balance(isBlack,
                           left.plus(key,
                                     value),
                           right,
                           this.key,
                           this.value);
        } else if (compRes == 0) {
            return new cyclops.container.immutable.impl.base.redblacktree.Node(isBlack,
                                                                               left,
                                                                               right,
                                                                               key,
                                                                               value,
                                                                               comp);
        }

        Tree<K, V> n = balance(isBlack,
                               left,
                               right.plus(key,
                                          value),
                               this.key,
                               this.value);

        return n;
    }

    @Override
    public String tree() {
        String value = (this.isBlack ? "BLACK" : "RED") + ":" + this.value;
        String left = this.left.isEmpty() ? "" : " " + this.left.tree();
        String right = this.right.isEmpty() ? "" : " " + this.right.tree();
        return "{" + value + left + right + "}";
    }


    @Override
    public Tree<K, V> minus(K key) {
        int compRes = comp.compare(this.key,
                                   key);
        if (compRes > 0) {
            return balance(isBlack,
                           left.minus(key),
                           right,
                           this.key,
                           this.value);
        } else if (compRes == 0) {
            return left.fold(leftNode -> {
                                 return right.fold(rightNode -> {
                                                       Tuple3<Tree<K, V>, K, V> t3 = rightNode.removeMin();
                                                       return balance(isBlack,
                                                                      left,
                                                                      t3._1(),
                                                                      t3._2(),
                                                                      t3._3());
                                                   },
                                                   leftLeaf -> left);

                             },
                             leftLeaf -> {
                                 return right.fold(rightNode -> right,
                                                   leftNode -> new Leaf(comp));
                             });

        }
        return balance(isBlack,
                       left,
                       right.minus(key),
                       this.key,
                       this.value);

    }

    public Tuple3<Tree<K, V>, K, V> removeMin() {
        return left.fold(node -> {
                             Tuple3<Tree<K, V>, K, V> t3 = node.removeMin();
                             return Tuple.tuple(balance(isBlack,
                                                        t3._1(),
                                                        right,
                                                        key,
                                                        value),
                                                t3._2(),
                                                t3._3());
                         },
                         leaf -> Tuple.tuple(right,
                                             key,
                                             value));
    }

    @Override
    public Tuple5<Boolean, Tree<K, V>, Tree<K, V>, K, V> unapply() {
        return Tuple.tuple(isBlack,
                           left,
                           right,
                           key,
                           value);
    }

    @Override
    public <R> R fold(Function<? super cyclops.container.immutable.impl.base.redblacktree.Node<K, V>, ? extends R> fn1,
                      Function<? super Leaf<K, V>, ? extends R> fn2) {
        return fn1.apply(this);
    }

    @Override
    public Comparator<K> comparator() {
        return comp;
    }

    public ReactiveSeq<Tuple2<K, V>> stream() {
        ReactiveSeq<Tuple2<K, V>> current = ReactiveSeq.of(Tuple.tuple(key,
                                                                       value));
        return ReactiveSeq.concat(left.stream(),
                                  current,
                                  right.stream());
    }

    @Override
    public int size() {
        return left.size() + right.size() + 1;
    }

    public cyclops.container.immutable.impl.base.redblacktree.Node<K, V> withBlack(boolean black) {
        return new cyclops.container.immutable.impl.base.redblacktree.Node<>(black,
                                                                             this.left,
                                                                             this.right,
                                                                             this.key,
                                                                             this.value,
                                                                             this.comp);
    }
}

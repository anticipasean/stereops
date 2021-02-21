package cyclops.container.immutable.impl.base.redblacktree;

import static cyclops.container.immutable.impl.base.redblacktree.Node.LEFT_BLACK;
import static cyclops.container.immutable.impl.base.redblacktree.Node.RED;
import static cyclops.container.immutable.impl.base.redblacktree.Node.RIGHT_BLACK;

import cyclops.container.control.option.Option;
import cyclops.container.foldable.Sealed2;
import cyclops.container.immutable.tuple.Tuple2;
import cyclops.reactive.ReactiveSeq;
import java.util.Comparator;
import java.util.function.Supplier;

public interface Tree<K, V> extends Sealed2<Node<K, V>, Leaf<K, V>> {

    boolean isEmpty();

    boolean isBlack();

    default boolean isRed() {
        return !isBlack();
    }

    Option<V> get(K key);

    V getOrElse(K key,
                V alt);

    V getOrElseGet(K key,
                   Supplier<? extends V> alt);

    Tree<K, V> plus(K key,
                    V value);

    Tree<K, V> minus(K key);

    Comparator<? super K> comparator();

    ReactiveSeq<Tuple2<K, V>> stream();

    int size();

    String tree();


    default Tree<K, V> balance(boolean isBlack,
                               Tree<K, V> left,
                               Tree<K, V> right,
                               K key,
                               V value) {

        if (isBlack && !isEmpty()) {
            if (left.isRed() && !left.isEmpty()) {
                Node<K, V> leftNode = left.fold(n -> n,
                                                leaf ->//unreachable
                                                    null);
                if (!leftNode.left()
                             .isBlack() && !leftNode.left()
                                                    .isEmpty()) {
                    Node<K, V> nestedLeftNode = leftNode.left()
                                                        .fold(n -> n,
                                                              leaf ->//unreachable
                                                                  null);

                    return RED(LEFT_BLACK(nestedLeftNode.left(),
                                          nestedLeftNode.right(),
                                          nestedLeftNode.key(),
                                          nestedLeftNode.value(),
                                          comparator()),
                               RIGHT_BLACK(leftNode.right(),
                                           right,
                                           key,
                                           value,
                                           comparator()),
                               leftNode.key(),
                               leftNode.value(),
                               comparator());
                }
                if (!leftNode.right()
                             .isBlack() && !leftNode.right()
                                                    .isEmpty()) {
                    Node<K, V> nestedRightNode = leftNode.right()
                                                         .fold(n -> n,
                                                               leaf ->//unreachable
                                                                   null);

                    return RED(LEFT_BLACK(leftNode.left(),
                                          nestedRightNode.left(),
                                          leftNode.key(),
                                          leftNode.value(),
                                          comparator()),
                               RIGHT_BLACK(nestedRightNode.right(),
                                           right,
                                           key,
                                           value,
                                           comparator()),
                               nestedRightNode.key(),
                               nestedRightNode.value(),
                               comparator());
                }
            }
            if (right.isRed() && !right.isEmpty()) {

                Node<K, V> rightNode = right.fold(n -> n,
                                                  leaf ->//unreachable
                                                      null);
                if (rightNode.left()
                             .isRed() && !rightNode.left()
                                                   .isEmpty()) {
                    Node<K, V> nestedLeftNode = rightNode.left()
                                                         .fold(n -> n,
                                                               leaf ->//unreachable
                                                                   null);
                    return RED(LEFT_BLACK(left,
                                          nestedLeftNode.left(),
                                          key,
                                          value,
                                          comparator()),
                               RIGHT_BLACK(nestedLeftNode.right(),
                                           rightNode.right(),
                                           rightNode.key(),
                                           rightNode.value(),
                                           comparator()),
                               nestedLeftNode.key(),
                               nestedLeftNode.value(),
                               comparator());

                }
                if (rightNode.right()
                             .isRed() && !rightNode.right()
                                                   .isEmpty()) {
                    Node<K, V> nestedRightNode = rightNode.right()
                                                          .fold(n -> n,
                                                                leaf ->//unreachable
                                                                    null);

                    Node<K, V> res = RED(LEFT_BLACK(left,
                                                    rightNode.left(),
                                                    key,
                                                    value,
                                                    comparator()),
                                         RIGHT_BLACK(nestedRightNode.left(),
                                                     nestedRightNode.right(),
                                                     nestedRightNode.key(),
                                                     nestedRightNode.value(),
                                                     comparator()),
                                         rightNode.key(),
                                         rightNode.value(),
                                         comparator());

                    return res;
                }
            }

        }

        return new Node(isBlack,
                        left,
                        right,
                        key,
                        value,
                        comparator());
    }

}

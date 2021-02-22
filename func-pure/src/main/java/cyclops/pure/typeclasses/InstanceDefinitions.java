package cyclops.pure.typeclasses;

import cyclops.pure.arrow.MonoidK;
import cyclops.container.control.lazy.maybe.Maybe;
import cyclops.container.control.eager.option.Option;
import cyclops.container.relational.Eq;
import cyclops.pure.typeclasses.comonad.Comonad;
import cyclops.pure.typeclasses.foldable.Foldable;
import cyclops.pure.typeclasses.foldable.Unfoldable;
import cyclops.pure.typeclasses.functor.ContravariantFunctor;
import cyclops.pure.typeclasses.functor.Functor;
import cyclops.pure.typeclasses.functor.ProFunctor;
import cyclops.pure.typeclasses.monad.Applicative;
import cyclops.pure.typeclasses.monad.ApplicativeError;
import cyclops.pure.typeclasses.monad.Monad;
import cyclops.pure.typeclasses.monad.MonadPlus;
import cyclops.pure.typeclasses.monad.MonadRec;
import cyclops.pure.typeclasses.monad.MonadZero;
import cyclops.pure.typeclasses.monad.Traverse;

/**
 * Created by johnmcclean on 28/06/2017.
 */
public interface InstanceDefinitions<W> {


    default Eq<W> eq() {
        return new Eq<W>() {
        };
    }

    <T, R> Functor<W> functor();

    <T> Pure<W> unit();

    <T, R> Applicative<W> applicative();

    <T, R> Monad<W> monad();

    <T, R> Option<MonadZero<W>> monadZero();

    <T> Option<MonadPlus<W>> monadPlus();

    <T> MonadRec<W> monadRec();

    <T> Foldable<W> foldable();

    <C2, T> Traverse<W> traverse();


    default <T> Option<ContravariantFunctor<W>> contravariantFunctor() {
        return Maybe.nothing();
    }

    default <T> Option<ProFunctor<W>> profunctor() {
        return Maybe.nothing();
    }

    default <T, E> Option<ApplicativeError<W, E>> applicativeError() {
        return Maybe.nothing();
    }


    <T> Option<MonadPlus<W>> monadPlus(MonoidK<W> m);


    <T> Option<Comonad<W>> comonad();

    default <T> Option<Unfoldable<W>> unfoldable() {
        return Maybe.nothing();
    }


    default Show<W> show() {
        return new Show<W>() {
        };
    }

}

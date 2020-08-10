package cyclops.container.talk;

import cyclops.async.Future;
import cyclops.container.persistent.impl.ImmutableList;
import cyclops.container.persistent.impl.Seq;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImmutabeListThreadsTest {

    int CORE_USER = 0;
    Executor IO_THREAD_POOL = Executors.newFixedThreadPool(1);

    public ImmutableList<Integer> future() {

        ImmutableList<Integer> userIdsActiveThisMonth = findUserIdsActiveThisMonth();

        Future.of(() -> {
                                      updateActiveUsersThisMonth(userIdsActiveThisMonth.plus(CORE_USER));
                                      return userIdsActiveThisMonth;
                                  },
                  IO_THREAD_POOL);

        ImmutableList<Integer> userIdsActiveThisYear = userIdsActiveThisMonth.plusAll(findUserIdsActiveThisYear());
        return userIdsActiveThisYear;

    }


    private Collection<? extends Integer> findUserIdsActiveThisYear() {
        return null;
    }


    private ImmutableList<Integer> findUserIdsActiveThisMonth() {
        ImmutableList<Integer> list = Seq.of(10,
                                             20);
        return list;
    }

    private void updateActiveUsersThisMonth(ImmutableList<Integer> list) {
    }
}

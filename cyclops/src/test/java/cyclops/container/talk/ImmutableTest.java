package cyclops.container.talk;

import cyclops.container.immutable.ImmutableList;
import cyclops.container.immutable.impl.Seq;
import org.junit.Ignore;

@Ignore
public class ImmutableTest {

    int CORE_USER = 0;
    int context = -1;

    // @Test
    public ImmutableList<Integer> unmod() {

        ImmutableList<Integer> userIds = findUserIds(context);
        ImmutableList<Integer> newList = userIds.plus(10)
                                                .plus(20);
        doSomething(newList);
        return userIds;


    }

    public void doSomething(ImmutableList<Integer> list) {
        updateActiveUsers(list.plus(CORE_USER));
    }

    private void updateActiveUsers(ImmutableList<Integer> list) {
    }

    private ImmutableList<Integer> findUserIds(int context) {
        return Seq.of(1);
    }
}

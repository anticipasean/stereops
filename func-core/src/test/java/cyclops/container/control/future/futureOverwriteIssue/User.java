package cyclops.container.control.future.futureOverwriteIssue;

import cyclops.container.immutable.impl.Seq;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

    private final Seq<Object> groups;
}

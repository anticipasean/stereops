package cyclops.container.control.future.futureOverwriteIssue;


import cyclops.container.control.Either;
import cyclops.container.immutable.impl.NonEmptyList;
import cyclops.container.immutable.impl.Vector;

public class AuthorizationService3 {

    public Either<Processor.Error, Vector<DataFileMetadata>> isAuthorized(User user,
                                                                          NonEmptyList<DataFileMetadata> nel) {
        return Either.right(nel.vector());
    }
}

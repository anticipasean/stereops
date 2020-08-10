package cyclops.control.future.futureOverwriteIssue;


import cyclops.control.Either;
import cyclops.container.persistent.impl.NonEmptyList;
import cyclops.container.persistent.impl.Vector;

public class AuthorizationService3 {

    public Either<Processor.Error, Vector<DataFileMetadata>> isAuthorized(User user,
                                                                          NonEmptyList<DataFileMetadata> nel) {
        return Either.right(nel.vector());
    }
}

package cyclops.jackson;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.type.TypeModifier;
import cyclops.container.Value;
import cyclops.stream.type.Streamable;
import cyclops.container.control.eager.either.Either;
import cyclops.container.control.lazy.eval.Eval;
import cyclops.container.control.eager.option.Option;
import cyclops.container.control.lazy.trampoline.Trampoline;
import cyclops.container.immutable.ImmutableList;
import cyclops.container.immutable.ImmutableQueue;
import cyclops.container.immutable.ImmutableSet;
import cyclops.reactive.ReactiveSeq;
import java.lang.reflect.Type;

public class CyclopsTypeModifier extends TypeModifier {

    private static final Class[] collectionLikeTypes = {ReactiveSeq.class, ImmutableList.class, ImmutableSet.class,
        ImmutableQueue.class, Streamable.class};

    @Override
    public JavaType modifyType(JavaType type,
                               Type jdkType,
                               TypeBindings bindings,
                               TypeFactory typeFactory) {

        if (type.isReferenceType() || type.isContainerType()) {
            return type;
        }
        final Class<?> raw = type.getRawClass();
        if (raw == Option.class) {
            return ReferenceType.upgradeFrom(type,
                                             type.containedTypeOrUnknown(0));
        }

        if (raw == Eval.class) {
            return ReferenceType.upgradeFrom(type,
                                             type.containedTypeOrUnknown(0));
        }
        if (raw == Trampoline.class) {
            return ReferenceType.upgradeFrom(type,
                                             type.containedTypeOrUnknown(0));
        }
        if (raw == Either.class) {
            return ReferenceType.upgradeFrom(type,
                                             type.containedTypeOrUnknown(0));
        }

        if (raw == Value.class) {
            return ReferenceType.upgradeFrom(type,
                                             type.containedTypeOrUnknown(0));
        }

        for (Class c : collectionLikeTypes) {
            if (c.isAssignableFrom(raw)) {
                //   return CollectionType.upgradeFrom(type, type.containedTypeOrUnknown(0));
                return CollectionLikeType.upgradeFrom(type,
                                                      type.containedTypeOrUnknown(0));
            }
        }

        return type;
    }
}

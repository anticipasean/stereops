package com.oath.cyclops.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import cyclops.container.control.Either;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

final class EitherDeserializer extends StdDeserializer<Either<?, ?>> {

    protected EitherDeserializer(JavaType valueType) {
        super(valueType);

    }

    @Override
    public Object deserializeWithType(JsonParser p,
                                      DeserializationContext ctxt,
                                      TypeDeserializer typeDeserializer) throws IOException {
        return super.deserializeWithType(p,
                                         ctxt,
                                         typeDeserializer);
    }

    @Override
    public Either<?, ?> deserialize(JsonParser p,
                                    DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonDeserializer ser = ctxt.findRootValueDeserializer(ctxt.getTypeFactory()
                                                                  .constructSimpleType(EitherBean.class,
                                                                                       new JavaType[0]));
        EitherBean x = (EitherBean) ser.deserialize(p,
                                                    ctxt);
        if (x.left != null) {
            return Either.left(x.left);
        }
        return Either.right(x.right);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class EitherBean {

        @Getter
        @Setter
        private Object left;
        @Getter
        @Setter
        private Object right;
    }


}

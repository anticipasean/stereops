package cyclops.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import cyclops.container.foldable.Sealed2;
import cyclops.exception.ExceptionSoftener;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class Sealed2Serializer extends JsonSerializer<Sealed2<?, ?>> {

    private static final long serialVersionUID = 1L;

    @Override
    public void serialize(Sealed2<?, ?> value,
                          JsonGenerator gen,
                          SerializerProvider serializers) throws IOException {

        value.fold(ExceptionSoftener.softenFunction(l -> {
                       JsonSerializer<Object> ser = serializers.findValueSerializer(LeftBean.class);
                       ser.serialize(new LeftBean(l),
                                     gen,
                                     serializers);
                       return null;
                   }),
                   ExceptionSoftener.softenFunction(r -> {
                       JsonSerializer<Object> ser = serializers.findValueSerializer(RightBean.class);
                       ser.serialize(new RightBean(r),
                                     gen,
                                     serializers);
                       return null;
                   }));

    }

    @AllArgsConstructor
    public static class LeftBean {

        @Getter
        @Setter
        private final Object left;
    }

    @AllArgsConstructor
    public static class RightBean {

        @Getter
        @Setter
        private final Object right;

    }
}

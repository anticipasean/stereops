package cyclops.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import cyclops.container.immutable.tuple.Tuple2;
import java.io.IOException;

public class Tuple2Serializer extends JsonSerializer<Tuple2<?, ?>> {

    private static final long serialVersionUID = 1L;


    @Override
    public void serialize(Tuple2<?, ?> value,
                          JsonGenerator gen,
                          SerializerProvider serializers) throws IOException {

        Object[] array = new Object[]{value._1(), value._2()};
        gen.writeStartArray();
        for (Object o : array) {
            JsonSerializer<Object> ser = serializers.findTypedValueSerializer(o.getClass(),
                                                                              true,
                                                                              null);
            ser.serialize(o,
                          gen,
                          serializers);
        }
        gen.writeEndArray();

    }
}

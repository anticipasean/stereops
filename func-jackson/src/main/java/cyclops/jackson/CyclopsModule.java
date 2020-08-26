package cyclops.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import cyclops.jackson.deserializers.CyclopsDeserializers;
import cyclops.jackson.serializers.CyclopsSerializers;

public class CyclopsModule extends SimpleModule {


    @Override
    public void setupModule(SetupContext context) {
        context.addDeserializers(new CyclopsDeserializers());
        context.addSerializers(new CyclopsSerializers());
        context.addTypeModifier(new CyclopsTypeModifier());

    }
}

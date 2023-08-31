package com.letifactory.gaming.eco.pricecalculator.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoWorkbench;

import java.io.IOException;

public class EcoWorkbenchDeserializer extends StdDeserializer<EcoWorkbench> {

    public EcoWorkbenchDeserializer() {
        this(null);
    }

    public EcoWorkbenchDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public EcoWorkbench deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String name = node.get("nameID").asText();

        return new EcoWorkbench(name);
    }
}

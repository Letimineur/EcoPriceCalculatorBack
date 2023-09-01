package com.letifactory.gaming.eco.pricecalculator.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItem;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItemType;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoSkill;

import java.io.IOException;

public class EcoItemDeserializer extends StdDeserializer<EcoItem> {

    public EcoItemDeserializer(){
        this(null);

    }
    public EcoItemDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public EcoItem deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String name = node.get("nameID").asText();

        return new EcoItem(name,0.0,new EcoItemType("raw",0));
    }
}

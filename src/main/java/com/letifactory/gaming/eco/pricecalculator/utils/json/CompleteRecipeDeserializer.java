package com.letifactory.gaming.eco.pricecalculator.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.letifactory.gaming.eco.pricecalculator.model.dto.CompleteRecipe;
import com.letifactory.gaming.eco.pricecalculator.model.entity.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompleteRecipeDeserializer extends StdDeserializer<CompleteRecipe> {

    public CompleteRecipeDeserializer(){
        this(null);

    }
    public CompleteRecipeDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public CompleteRecipe deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        final String recipeName = node.get("nameID").asText();
        final Integer labor = node.get("labor").asInt();

        final List<EcoRecipeItem> items = new ArrayList<>();
        final EcoRecipe recipe = new EcoRecipe(recipeName, labor, new EcoWorkbench(node.get("craftingTable").asText()), new EcoSkill(node.get("skill").asText()));
        final CompleteRecipe completeRecipe = new CompleteRecipe(recipe,items);
        final EcoItemType itemType = new EcoItemType();

        final ArrayNode inputNodes = (ArrayNode) node.get("ingredients");
        inputNodes.forEach(item->{
            final String itemName= item.get("item").asText();
            final Double qty= item.get("quantity").asDouble();
            final boolean reducible=item.get("reducible").asBoolean();
            items.add(new EcoRecipeItem(recipeName+"_"+itemName,recipe,new EcoItem(itemName,0.0,itemType,"",0,0),qty,reducible,false));
        });
        final ArrayNode outputNodes = (ArrayNode) node.get("outputs");
        outputNodes.forEach(item->{
            final String itemName= item.get("item").asText();
            final Double qty= item.get("quantity").asDouble();
            final boolean reducible=item.get("reducible").asBoolean();
            items.add(new EcoRecipeItem(recipeName+"_"+itemName,recipe,new EcoItem(itemName,0.0,itemType,"",0,0),qty,reducible,true));
        });
        return completeRecipe;
    }
}

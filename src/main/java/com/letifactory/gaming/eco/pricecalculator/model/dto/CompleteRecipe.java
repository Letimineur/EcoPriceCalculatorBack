package com.letifactory.gaming.eco.pricecalculator.model.dto;

import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipe;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipeItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CompleteRecipe {

    private EcoRecipe recipe;
    private List<EcoRecipeItem> recipeItems;

    public List<EcoRecipeItem> getAllOutput() {
        List<EcoRecipeItem> output = new ArrayList<>();
        this.recipeItems.forEach(ri -> {
            if (ri.isOutput()) {
                output.add(ri);
            }
        });
        return output;
    }

    public List<EcoRecipeItem> getAllInput() {
        List<EcoRecipeItem> input = new ArrayList<>();
        this.recipeItems.forEach(ri -> {
            if (!ri.isOutput()) {
                input.add(ri);
            }
        });
        return input;
    }

}

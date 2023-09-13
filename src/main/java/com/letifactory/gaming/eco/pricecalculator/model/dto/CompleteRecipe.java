package com.letifactory.gaming.eco.pricecalculator.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItem;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipe;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipeItem;
import com.letifactory.gaming.eco.pricecalculator.utils.AppConstantUtils;
import com.letifactory.gaming.eco.pricecalculator.utils.json.CompleteRecipeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonDeserialize(using = CompleteRecipeDeserializer.class)
public class CompleteRecipe {

    private EcoRecipe recipe;
    private List<EcoRecipeItem> recipeItems;

    public List<EcoRecipeItem> getAllOutput() {
        final List<EcoRecipeItem> output = new ArrayList<>();
        this.recipeItems.forEach(ri -> {
            if (ri.isOutput()) {
                output.add(ri);
            }
        });
        return output;
    }

    public List<EcoRecipeItem> getAllInput() {
        final List<EcoRecipeItem> input = new ArrayList<>();
        this.recipeItems.forEach(ri -> {
            if (!ri.isOutput()) {
                input.add(ri);
            }
        });
        return input;
    }

    public double getSumOfInputPrice() {
        double sum = 0.0;
        for (final EcoRecipeItem input : this.getAllInput()) {
            if (input.getEcoItem().getPrice() == 0) {
                return 0.0;
            } else {
                sum += input.getEcoItem().getPrice();
            }
        }
        return sum;
    }

    public double getSumOfPricedOutput() {
        return this.getOutputAlreadyPriced().stream().reduce(0.0, (calc, output) -> Double.sum(calc,
                        output.getEcoItem().getPrice()),
                Double::sum);
    }

    public double getNumberOfOutput() {
        //ignore Tailings as Output
        return this.getAllOutput().stream().reduce(0.0, (calc, outPut) ->
                        calc + (AppConstantUtils.isTailing(outPut.getEcoItem()) ? 0.0 : outPut.getQuantity()),
                Double::sum);
    }

    public List<EcoRecipeItem> getOutputAlreadyPriced() {
        final List<EcoRecipeItem> pricedOutput = new ArrayList<>();
        this.getAllOutput().forEach(outputs -> {
            final EcoItem ecoItem = outputs.getEcoItem();
            if (ecoItem.getPrice() > 0) {
                pricedOutput.add(outputs);
            }
        });
        return pricedOutput;
    }

    public List<EcoRecipeItem> geNotPricedOutput() {
        final List<EcoRecipeItem> notPricedOutput = new ArrayList<>();
        this.getAllOutput().forEach(outputs -> {
            final EcoItem ecoItem = outputs.getEcoItem();
            if (ecoItem.getPrice() == 0) {
                notPricedOutput.add(outputs);
            }
        });
        return notPricedOutput;
    }

}

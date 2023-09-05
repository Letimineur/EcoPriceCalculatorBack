package com.letifactory.gaming.eco.pricecalculator.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItem;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipe;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipeItem;
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

    public double getSumOfInputPrice() {
        return this.getAllInput().stream().reduce(0.0, (calc, input) -> Double.sum(calc,
                        input.getEcoItem().getPrice()),
                Double::sum);
    }
    public double getSumOfPricedOutput() {
        return this.getOutputAlreadyPriced().stream().reduce(0.0, (calc, output) -> Double.sum(calc,
                        output.getEcoItem().getPrice()),
                Double::sum);
    }

    public int getNumberOfOutput() {
        return this.getAllOutput().stream().reduce(0, (calc, outPut) -> calc + outPut.getQuantity(), Integer::sum);
    }

    public List<EcoRecipeItem> getOutputAlreadyPriced() {
        List<EcoRecipeItem> pricedOutput = new ArrayList<>();
        this.getAllOutput().forEach(outputs -> {
            final EcoItem ecoItem = outputs.getEcoItem();
            if (ecoItem.getPrice() > 0) {
                pricedOutput.add(outputs);
            }
        });
        return pricedOutput;
    }

    public List<EcoRecipeItem> geNotPricedOutput() {
        List<EcoRecipeItem> notPricedOutput = new ArrayList<>();
        this.getAllOutput().forEach(outputs -> {
            final EcoItem ecoItem = outputs.getEcoItem();
            if (ecoItem.getPrice() == 0) {
                notPricedOutput.add(outputs);
            }
        });
        return notPricedOutput;
    }

    }

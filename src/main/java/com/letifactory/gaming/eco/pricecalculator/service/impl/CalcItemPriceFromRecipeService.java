package com.letifactory.gaming.eco.pricecalculator.service.impl;

import com.letifactory.gaming.eco.pricecalculator.model.dto.CompleteRecipe;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItem;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipeItem;
import com.letifactory.gaming.eco.pricecalculator.model.service.EcoItemServiceImpl;
import com.letifactory.gaming.eco.pricecalculator.model.service.RecipeServiceImpl;
import com.letifactory.gaming.eco.pricecalculator.utils.AppConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CalcItemPriceFromRecipeService {
    private final Logger logg = LoggerFactory.getLogger(CalcItemPriceFromRecipeService.class);
    @Autowired
    RecipeServiceImpl recipeService;

    @Autowired
    EcoItemServiceImpl ecoItemService;


    public double calcItemPricefromAllRecipe(EcoItem item) throws IllegalArgumentException, NoSuchElementException {
        if (item.getType().equals(AppConstantUtils.ALL_TYPES.get(0))) {
            throw new IllegalArgumentException(
                    "It's not possible to calculate the price of a " + AppConstantUtils.ALL_TYPES.get(0) + " type");
        }
        List<CompleteRecipe> completeRecipeList = recipeService.getAllRecipesCratingItem(item.getName());
        if (completeRecipeList.isEmpty()) {
            throw new NoSuchElementException("No recipe where found for this item " + item);
        }
        //TODO calcItemsPriceFromOneRecipe()
        return 0.0;
    }

    public Map<String, Double> calcItemsPriceFromOneRecipe(CompleteRecipe completeRecipe) {
        Map<String, Double> itemsPrice = new HashMap<>();
        List<EcoRecipeItem> pricedOutputs = completeRecipe.getOutputAlreadyPriced();
        final int numberOfOutput = completeRecipe.getNumberOfOutput();
        double laborPerOutput = (double) completeRecipe.getRecipe().getLabor() / numberOfOutput;
        if (pricedOutputs.isEmpty()) {
            completeRecipe.getAllOutput().forEach(output -> {
                final double outputBasePrice = completeRecipe.getSumOfInputPrice() *
                        (numberOfOutput - output.getQuantity()) /
                        numberOfOutput;

                itemsPrice.put(output.getEcoItem().getName(), addTaxeAndCaloriesToPrice(outputBasePrice,
                        laborPerOutput, output.getEcoItem()) * output.getEcoItem().getType().getProfitsMultiplicator());
            });
        } else {
            final List<EcoRecipeItem> notPricedOutput = completeRecipe.geNotPricedOutput();
            notPricedOutput.forEach(output -> {
                final double outputBasePrice = (completeRecipe.getSumOfInputPrice()-completeRecipe.getSumOfPricedOutput()) *
                        (notPricedOutput.size() - output.getQuantity()) /
                        notPricedOutput.size();

                itemsPrice.put(output.getEcoItem().getName(), addTaxeAndCaloriesToPrice(outputBasePrice,
                        laborPerOutput, output.getEcoItem()) * output.getEcoItem().getType().getProfitsMultiplicator());
            });
        }
        return itemsPrice;
    }

    private double addTaxeAndCaloriesToPrice(double itemPrice, double laborPerOutput, EcoItem item) {
        return (itemPrice + AppConstantUtils.getLaborPrice(laborPerOutput)) * item.getType().getTaxeMultiplicator();
    }

    private CompleteRecipe addTaxeAndCaloriesToAllPrice(CompleteRecipe completeRecipe) {
        double laborPerOutput = (double) completeRecipe.getRecipe().getLabor() / completeRecipe.getNumberOfOutput();
        completeRecipe.getAllOutput().forEach(output -> output.getEcoItem()
                .setPrice(addTaxeAndCaloriesToPrice(output.getEcoItem().getPrice(),
                        laborPerOutput,
                        output.getEcoItem())));
        return completeRecipe;
    }
}

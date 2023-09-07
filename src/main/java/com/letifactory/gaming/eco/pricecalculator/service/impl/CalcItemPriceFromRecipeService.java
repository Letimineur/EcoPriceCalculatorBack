package com.letifactory.gaming.eco.pricecalculator.service.impl;

import com.letifactory.gaming.eco.pricecalculator.model.dto.CompleteRecipe;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItem;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItemType;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipeItem;
import com.letifactory.gaming.eco.pricecalculator.model.service.EcoItemServiceImpl;
import com.letifactory.gaming.eco.pricecalculator.model.service.RecipeServiceImpl;
import com.letifactory.gaming.eco.pricecalculator.utils.AppConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalcItemPriceFromRecipeService {
    private final Logger logg = LoggerFactory.getLogger(CalcItemPriceFromRecipeService.class);
    @Autowired
    RecipeServiceImpl recipeService;

    @Autowired
    EcoItemServiceImpl ecoItemService;

    public void updateItemPriceForItemType(final EcoItemType type) throws IllegalArgumentException {
        if (type.getType().equals(AppConstantUtils.ALL_TYPES.get(0))) {
            final String msg = "It's not possible to calculate the price of a " + AppConstantUtils.ALL_TYPES.get(0) + " type";
            logg.warn("updateItemPriceForItemType: " + msg);
            throw new IllegalArgumentException(
                    msg);
        }
        final List<EcoItem> itemsToUpdate = ecoItemService.getAllNotPricedItemForType(type);
        itemsToUpdate.forEach(item -> updatePriceForItem(item));
    }

    public void updatePriceForItem(final EcoItem item) {
        item.setPrice(calcItemPriceFromAllRecipe(item));
        if (item.getPrice() == 0) {
            logg.warn(item +
                    ": Item price cannot be updated. Maybe lower type item are not priced yet. Current type is " +
                    item.getType().getType() + " order:" + item.getType().getTypeOrder());
        } else {
            ecoItemService.save(item);
            logg.info("Updated price for item " + item);
        }
    }

    public double calcItemPriceFromAllRecipe(final EcoItem item) throws IllegalArgumentException, NoSuchElementException {
        if (item.getType().equals(AppConstantUtils.ALL_TYPES.get(0))) {
            final String msg = "It's not possible to calculate the price of a " + AppConstantUtils.ALL_TYPES.get(0) + " type";
            logg.warn("calcItemPriceFromAllRecipe: " + msg);
            throw new IllegalArgumentException(
                    msg);
        }
        final List<CompleteRecipe> completeRecipeList = recipeService.getAllRecipesCratingItem(item.getName());
        if (completeRecipeList.isEmpty()) {
            final String msg = "No recipe where found for this item " + item;
            logg.warn("calcItemPriceFromAllRecipe: " + msg);
            throw new NoSuchElementException(msg);
        }
        final List<Double> allPrice = new ArrayList<>();
        completeRecipeList.forEach(completeRecipe ->
                allPrice.add(calcItemsPriceFromOneRecipe(completeRecipe).get(item.getName())));
        final double result = allPrice.stream().reduce(0.0, Double::sum) / allPrice.size();
        return result;
    }

    public Map<String, Double> calcItemsPriceFromOneRecipe(final CompleteRecipe completeRecipe) {
        final Map<String, Double> itemsPrice = new HashMap<>();
        final double numberOfOutput = completeRecipe.getNumberOfOutput();
        final double laborPerOutput = (double) completeRecipe.getRecipe().getLabor() / numberOfOutput;
        final List<EcoRecipeItem> allInput = completeRecipe.getAllInput();
        final List<EcoItem> notPricedInputItem = allInput.stream().filter(input -> input.getEcoItem().getPrice() == 0).map(input -> input.getEcoItem()).collect(Collectors.toList());
        notPricedInputItem.forEach(item -> {
            System.out.println(completeRecipe.getRecipe().getName());
            logg.info("Recipe inputItem must be priced first " + item);
            this.updatePriceForItem(item);
        });

        final double sumOfInputPrice = allInput.stream().reduce(0.0, (calc, input) -> Double.sum(calc,
                        input.getEcoItem().getPrice()),
                Double::sum);
        if (isNotFeedBackRecipe(completeRecipe)) {
            completeRecipe.getAllOutput().forEach(output -> {
                final double outputBasePrice = (sumOfInputPrice *
                        output.getQuantity() / numberOfOutput) / output.getQuantity();

                itemsPrice.put(output.getEcoItem().getName(), addTaxeAndCaloriesToPrice(outputBasePrice,
                        laborPerOutput, output.getEcoItem()) * output.getEcoItem().getType().getProfitsMultiplicator());
            });
        } else {
            final List<EcoRecipeItem> notPricedOutput = completeRecipe.geNotPricedOutput();
            notPricedOutput.forEach(output -> {
                final double outputBasePrice = ((sumOfInputPrice - completeRecipe.getSumOfPricedOutput()) *
                        output.getQuantity() / numberOfOutput) / output.getQuantity();

                itemsPrice.put(output.getEcoItem().getName(), addTaxeAndCaloriesToPrice(outputBasePrice,
                        laborPerOutput, output.getEcoItem()) * output.getEcoItem().getType().getProfitsMultiplicator());
            });
        }
        return itemsPrice;
    }

    private boolean isNotFeedBackRecipe(final CompleteRecipe completeRecipe) {
        final String recipeName = completeRecipe.getRecipe().getName();
        if (AppConstantUtils.FEEDBACK_RECIPE.stream().filter(recipe -> recipe.equals(recipeName)).findFirst().isPresent()) {
            return false;
        }
        return true;
    }


    private double addTaxeAndCaloriesToPrice(final double itemPrice, final double laborPerOutput, final EcoItem item) {
        return (itemPrice + AppConstantUtils.getLaborPrice(laborPerOutput)) * item.getType().getTaxeMultiplicator();
    }

    private CompleteRecipe addTaxeAndCaloriesToAllPrice(final CompleteRecipe completeRecipe) {
        final double laborPerOutput = (double) completeRecipe.getRecipe().getLabor() / completeRecipe.getNumberOfOutput();
        completeRecipe.getAllOutput().forEach(output -> output.getEcoItem()
                .setPrice(addTaxeAndCaloriesToPrice(output.getEcoItem().getPrice(),
                        laborPerOutput,
                        output.getEcoItem())));
        return completeRecipe;
    }
}

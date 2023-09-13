package com.letifactory.gaming.eco.pricecalculator.service.impl;

import com.letifactory.gaming.eco.pricecalculator.exception.WrongValueInDataBaseException;
import com.letifactory.gaming.eco.pricecalculator.model.dto.CompleteRecipe;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItem;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItemType;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipeItem;
import com.letifactory.gaming.eco.pricecalculator.model.service.EcoItemServiceImpl;
import com.letifactory.gaming.eco.pricecalculator.model.service.EcoItemTypeServiceImpl;
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

    @Autowired
    EcoItemTypeServiceImpl ecoItemTypeService;

    private boolean isRunning = false;
    final private HashSet<EcoItem> itemToUpdate = new HashSet<>();
    final private HashMap<EcoItem, EcoItem> unCalculable = new HashMap<>();

    public void setAllPrice() {
        if (!isRunning) {
            isRunning = true;
            for (final Map.Entry<Integer, String> type : AppConstantUtils.ALL_TYPES.entrySet()) {
                if (type.getKey() > 0) {
                    try {
                        final EcoItemType itemType = ecoItemTypeService.findById(type.getValue());
                        logg.debug("Starting pricing for " + itemType);
                        setPriceForItemType(itemType);
                    } catch (final WrongValueInDataBaseException e) {
                        logg.error("Exiting price Calculation", e);
                        itemToUpdate.clear();
                        unCalculable.clear();
                        isRunning = false;
                        return;
                    }
                    for (final Map.Entry<EcoItem, EcoItem> entry : unCalculable.entrySet()) {
                        System.out.println("UnCalculable " + entry.getKey() + " due to " + entry.getValue());
                    }
                    unCalculable.clear();
                    logg.debug("Starting delayed pricing for " + type.getValue());
                    delayedItemPriceCalc();
                }

            }
        }
        isRunning = false;
    }

    private void delayedItemPriceCalc() {
        while (!itemToUpdate.isEmpty()) {
            final List<EcoItem> inCalc = new ArrayList<>(itemToUpdate);
            itemToUpdate.clear();
            for (final EcoItem item : inCalc) {
                try {
                    if (item.getName().equals("MortaredStone")) {
                        System.out.println("toto");
                    }
                    setItemPrice(item);
                } catch (final NoSuchElementException e) {
                    logg.error("Cannot set price for Item during delayed process", e);
                }
            }
        }
    }

    private void setPriceForItemType(final EcoItemType type) throws WrongValueInDataBaseException {
        final List<EcoItem> itemsToUpdate = ecoItemService.getAllItemForType(type);
        itemsToUpdate.forEach(item -> {
            try {
                setItemPrice(item);
            } catch (final NoSuchElementException e) {
                logg.error("Cannot set price for Item", e);
            }
        });
    }

    private EcoItem setPriceForTagItem(final EcoItem item) throws NoSuchElementException {
        final List<String> itemList = AppConstantUtils.TAG_ITEM_LINK.get(item.getName());
        if (itemList != null && !itemList.isEmpty()) {
            final List<Double> allPrice = new ArrayList<>();
            final List<EcoItem> allItemFromTag = ecoItemService.getAllItemFromList(itemList);
            allItemFromTag.forEach(itemFromTag -> allPrice.add(itemFromTag.getPrice()));
            final double price = getLowestPrice(allPrice);
            item.setPrice(price);
            return saveItem(item);
        } else {
            throw new NoSuchElementException("No Link item exist for this Tag " + item.getName());
        }
    }

    private static double getLowestPrice(final List<Double> allPrice) {
        return allPrice.stream().filter(d -> d > 0).mapToDouble(d -> d).min().orElse(0.0);
    }

    private static double getAveragePrice(final List<Double> allPrice) {
        return allPrice.stream().reduce(Double::sum).get() / allPrice.size();
    }

    public EcoItem setItemPrice(final EcoItem item) throws WrongValueInDataBaseException, NoSuchElementException {
        if(AppConstantUtils.isTailing(item)){
            logg.info("Tailing don't need to be priced");
            return item;
        }
        final List<CompleteRecipe> completeRecipeList = recipeService.getAllRecipesCratingItem(item.getName());
        if (completeRecipeList.isEmpty()) {
            if (item.isTag()) {
                return setPriceForTagItem(item);
            } else {
                throw new NoSuchElementException("No recipe where found for this item " + item);
            }
        }
        double result = 0.0;
        // special case of BarrelItem that only need base recipe to be calculated
        if (item.getName().equals("BarrelItem")) {
            logg.debug("Special calc for Barrel item price");
            for (final CompleteRecipe cptRecipe : completeRecipeList) {
                if (cptRecipe.getRecipe().getName().equals("Barrel")) {
                    result = calcPriceForItemFromRecipe(cptRecipe, item);
                    logg.debug("Barrel will be priced at " + result);
                    break;
                }
            }
        } else {
            final List<Double> allPrice = new ArrayList<>();
            completeRecipeList
                    .forEach(completeRecipe -> allPrice.add(calcPriceForItemFromRecipe(completeRecipe, item)));
            result = getLowestPrice(allPrice);
        }

        final double orgPrice = item.getPrice();
        item.setPrice(0.0);
        item.setPrice(result);
        if (item.getPrice() == 0) {
            logg.warn(item.getName() +
                    ": Item price cannot be set because calcul send 0 as result");
        } else {
            return saveItem(item);
        }
        item.setPrice(orgPrice);
        logg.warn(item + " price didn't get modified");
        return item;
    }


    private EcoItem saveItem(final EcoItem item) {
        item.setPrice(Math.round(item.getPrice() * 100.0) / 100.0);
        final EcoItem retItem = ecoItemService.save(item);
        logg.info("Price set for " + (retItem.isTag() ? "tagItem " : "item ") + retItem);
        return retItem;
    }

    private double calcPriceForItemFromRecipe(final CompleteRecipe completeRecipe, final EcoItem itemToCalc)
            throws WrongValueInDataBaseException {
        if (itemToCalc.getName().equals("BasicCircuitItem")) {
            System.out.println("toto");
        }
        if (testForUnpricedInput(completeRecipe, itemToCalc)) {
            return 0.0;
        }
        final double numberOfOutput = completeRecipe.getNumberOfOutput();
        final double laborPerOutput = (double) completeRecipe.getRecipe().getLabor() / numberOfOutput;
        final List<EcoRecipeItem> allInput = completeRecipe.getAllInput();
        final double sumOfInputPrice = allInput.stream().reduce(0.0,
                (calc, input) -> Double.sum(calc, input.getEcoItem().getPrice() * input.getQuantity()),
                Double::sum);

        if (isNotFeedBackRecipe(completeRecipe)) {
            for (final EcoRecipeItem output : completeRecipe.getAllOutput()) {
                if (output.getEcoItem().getName().equals(itemToCalc.getName())) {
                    final double outputBasePrice = (sumOfInputPrice / numberOfOutput);
                    return addTaxeAndCaloriesToPrice(outputBasePrice, laborPerOutput, output.getEcoItem());
                }
            }
        } else {
            final List<EcoRecipeItem> notPricedOutput = completeRecipe.geNotPricedOutput();
            for (final EcoRecipeItem output : notPricedOutput) {
                if (output.getEcoItem().getName().equals(itemToCalc.getName())) {
                    final double outputBasePrice = (sumOfInputPrice - completeRecipe.getSumOfPricedOutput())
                            / numberOfOutput;

                    return addTaxeAndCaloriesToPrice(outputBasePrice, laborPerOutput, output.getEcoItem());
                }
            }
        }
        // Should not go here
        logg.error("No item found in output. Should not happen. Recipe: " + completeRecipe.getRecipe().getName() +
                " for " + itemToCalc);
        return 0.0;
    }

    private boolean testForUnpricedInput(final CompleteRecipe completeRecipe, final EcoItem itemToCalc)
            throws WrongValueInDataBaseException {
        final List<EcoRecipeItem> allInput = completeRecipe.getAllInput();
        final List<EcoItem> notPricedInputItem = allInput.stream().map(EcoRecipeItem::getEcoItem)
                .filter(ecoItem -> ecoItem.getPrice() == 0).toList();
        if (!notPricedInputItem.isEmpty()) {
            for (final EcoItem item : notPricedInputItem) {
                System.out.println(completeRecipe.getRecipe().getName());
                logg.info("Recipe inputItem must be priced first " + item);
                if (item.getType().getTypeOrder() <= 0) {
                    throw new WrongValueInDataBaseException("A raw itemType doesn't have a price. Impossible to " +
                            "calItemPrice because of " + item);
                } else if (item.getType().getTypeOrder() > itemToCalc.getType().getTypeOrder() &&
                        isNotFeedBackRecipe(completeRecipe)) {
                    logg.warn("Bad type order of input " + item + ",it should be =< " + itemToCalc +
                            ". Impossible to continue pricing");
                    unCalculable.put(itemToCalc, item);
                    return true;
                } else {
                    logg.info("An input must be priced first" + item);
                    itemToUpdate.add(item);
                }
            }
            return true;
        }
        return false;
    }

    private boolean isNotFeedBackRecipe(final CompleteRecipe completeRecipe) {
        final String recipeName = completeRecipe.getRecipe().getName();
        return AppConstantUtils.FEEDBACK_RECIPE.stream().noneMatch(recipe -> recipe.equals(recipeName));
    }

    private double addTaxeAndCaloriesToPrice(final double itemPrice, final double laborPerOutput, final EcoItem item) {
        return (itemPrice + AppConstantUtils.getLaborPrice(laborPerOutput)) *
                item.getType().getTaxeMultiplicator() *
                item.getType().getProfitsMultiplicator();
    }

    @Deprecated
    public void showRecipeConflict() {
        recipeService.findAll().forEach(recipe -> {
            final CompleteRecipe completeRecipe = recipeService.getCompleteRecipe(recipe.getName());
            completeRecipe.getAllOutput().forEach(output -> {
                final List<EcoItem> inputs = new ArrayList<>();
                completeRecipe.getAllInput().forEach(input -> {
                    if (input.getEcoItem().getType().getTypeOrder() > output.getEcoItem().getType().getTypeOrder()) {
                        inputs.add(input.getEcoItem());

                    }
                });
                if (inputs.size() > 0) {
                    logg.debug("Recipe " + completeRecipe.getRecipe().getName());
                    logg.debug(output.getEcoItem() + " has higher input :");
                    inputs.forEach(input -> logg.debug("Input: " + input));
                }
            });
        });
    }

}

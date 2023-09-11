package com.letifactory.gaming.eco.pricecalculator.service.impl;

import com.letifactory.gaming.eco.pricecalculator.exception.WrongValueInDataBaseException;
import com.letifactory.gaming.eco.pricecalculator.model.dto.CompleteRecipe;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItem;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItemType;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipe;
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
    private HashSet<EcoItem> itemToUpdate = new HashSet<>();
    private HashMap<EcoItem, EcoItem> unCalculable = new HashMap<>();

    public void setAllPrice() {
        if (!isRunning) {
            isRunning = true;
            for (Map.Entry<Integer, String> type : AppConstantUtils.ALL_TYPES.entrySet()) {
                if (type.getKey() > 0) {
                    try {
                        final EcoItemType itemType = ecoItemTypeService.findById(type.getValue());
                        logg.info("Starting pricing for " + itemType);
                        setPriceForItemType(itemType);
                    } catch (NoSuchElementException e) {
                        logg.warn("No type found for " + type.getValue() + " can not continue calcul.", e);
                        isRunning = false;
                        itemToUpdate.clear();
                        return;
                    } catch (WrongValueInDataBaseException e) {
                        logg.warn(e.getMessage());
                        isRunning = false;
                        itemToUpdate.clear();
                        return;
                    }
                    for (Map.Entry<EcoItem, EcoItem> entry : unCalculable.entrySet()) {
                        System.out.println("UnCalculable " + entry.getKey() + " dur to " + entry.getValue());
                    }
                    logg.info("Starting delayed pricing for " + type.getValue());
                    delayedItemPriceCalc();
                }

            }
        }
        isRunning = false;
    }

    private void delayedItemPriceCalc() {
        while (!itemToUpdate.isEmpty()) {
            List<EcoItem> inCalc = new ArrayList<>(itemToUpdate);
            itemToUpdate.clear();
            for (EcoItem item : inCalc) {
                setItemPrice(item);
            }
        }
    }

    private void setPriceForItemType(EcoItemType type) throws WrongValueInDataBaseException {
        final List<EcoItem> itemsToUpdate = ecoItemService.getAllNotPricedItemForType(type);
        itemsToUpdate.forEach(item -> {
            setItemPrice(item);
        });
    }

    public EcoItem setItemPrice(final EcoItem item) throws WrongValueInDataBaseException, NoSuchElementException {
        final List<CompleteRecipe> completeRecipeList = recipeService.getAllRecipesCratingItem(item.getName());
        if (completeRecipeList.isEmpty()) {
            final String msg = "No recipe where found for this item " + item;
            logg.warn("calcItemPriceFromAllRecipe: " + msg);
            throw new NoSuchElementException(msg);
        }
        final List<Double> allPrice = new ArrayList<>();
        completeRecipeList.forEach(completeRecipe ->
                allPrice.add(calcPriceForItemFromRecipe(completeRecipe, item)));
        final double result = allPrice.stream().reduce(0.0, Double::sum) / allPrice.size();

        double orgPrice = item.getPrice();
        item.setPrice(0.0);
        item.setPrice(result);
        if (item.getPrice() == 0) {
            logg.warn(item +
                    ": Item price cannot be set. Maybe lower type item are not priced yet. Current type is " +
                    item.getType().getType() + " order:" + item.getType().getTypeOrder());
        } else {
            logg.info("Price set for item " + item);
            return ecoItemService.save(item);
        }

        item.setPrice(orgPrice);
        return item;
    }

    private double calcPriceForItemFromRecipe(CompleteRecipe completeRecipe, EcoItem itemToCalc)
            throws WrongValueInDataBaseException {
        if (testForUnpricedInput(completeRecipe, itemToCalc)) {
            return 0.0;
        }
        final double numberOfOutput = completeRecipe.getNumberOfOutput();
        final double laborPerOutput = (double) completeRecipe.getRecipe().getLabor() / numberOfOutput;
        final List<EcoRecipeItem> allInput = completeRecipe.getAllInput();
        final double sumOfInputPrice = allInput.stream().reduce(0.0, (calc, input) -> Double.sum(calc,
                        input.getEcoItem().getPrice()),
                Double::sum);

        if (isNotFeedBackRecipe(completeRecipe)) {
            for (EcoRecipeItem output : completeRecipe.getAllOutput()) {
                if (output.getEcoItem().getName().equals(itemToCalc.getName())) {
                    final double outputBasePrice = (sumOfInputPrice *
                            output.getQuantity() / numberOfOutput) / output.getQuantity();
                    return addTaxeAndCaloriesToPrice(outputBasePrice,
                            laborPerOutput, output.getEcoItem()) *
                            output.getEcoItem().getType().getProfitsMultiplicator();
                }
            }
        } else {
            final List<EcoRecipeItem> notPricedOutput = completeRecipe.geNotPricedOutput();
            for (EcoRecipeItem output : notPricedOutput) {
                if (output.getEcoItem().getName().equals(itemToCalc.getName())) {
                    final double outputBasePrice = ((sumOfInputPrice - completeRecipe.getSumOfPricedOutput()) *
                            output.getQuantity() / numberOfOutput) / output.getQuantity();

                    return addTaxeAndCaloriesToPrice(outputBasePrice,
                            laborPerOutput, output.getEcoItem()) *
                            output.getEcoItem().getType().getProfitsMultiplicator();
                }
            }
        }
        //Should not go here
        logg.warn("No item found in output. Should not happen. Recipe: " + completeRecipe.getRecipe().getName() +
                " for " + itemToCalc);
        return 0.0;
    }

    private boolean testForUnpricedInput(final CompleteRecipe completeRecipe, final EcoItem itemToCalc)
            throws WrongValueInDataBaseException {
        final List<EcoRecipeItem> allInput = completeRecipe.getAllInput();
        final List<EcoItem> notPricedInputItem =
                allInput.stream().map(EcoRecipeItem::getEcoItem).filter(ecoItem -> ecoItem.getPrice() == 0).toList();
        if (!notPricedInputItem.isEmpty()) {
            for (EcoItem item : notPricedInputItem) {
                System.out.println(completeRecipe.getRecipe().getName());
                logg.info("Recipe inputItem must be priced first " + item);
                if (item.getType().getTypeOrder() == 0) {
                    throw new WrongValueInDataBaseException("A raw itemType doesn't have a price. Impossible to " +
                            "calItemPrice because of " + item);
                } else if (item.getType().getTypeOrder() > itemToCalc.getType().getTypeOrder()) {
                    logg.error("Bad type order of input " + item + ",it should be =< " + itemToCalc);
                    unCalculable.put(itemToCalc, item);
                } else {
                    logg.info("Add input to delayed price");
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
        return (itemPrice + AppConstantUtils.getLaborPrice(laborPerOutput)) * item.getType().getTaxeMultiplicator();
    }

    public void showRecipeConflict() {
        recipeService.findAll().forEach(recipe -> {
            final CompleteRecipe completeRecipe = recipeService.getCompleteRecipe(recipe.getName());
            completeRecipe.getAllOutput().forEach(output -> {
                List<EcoItem> inputs = new ArrayList<>();
                completeRecipe.getAllInput().forEach(input -> {
                    if (input.getEcoItem().getType().getTypeOrder() > output.getEcoItem().getType().getTypeOrder()) {
                        inputs.add(input.getEcoItem());

                    }
                });
                if (inputs.size() > 0) {
                    System.out.println("Recipe " + completeRecipe.getRecipe().getName());
                    System.out.println(output.getEcoItem() + " has higher input :");
                    inputs.forEach(input -> System.out.println("Input: " + input));
                }
            });
        });
    }

}

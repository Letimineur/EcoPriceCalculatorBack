package com.letifactory.gaming.eco.pricecalculator.model.service;

import com.letifactory.gaming.eco.pricecalculator.model.dto.CompleteRecipe;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipe;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipeItem;
import com.letifactory.gaming.eco.pricecalculator.model.repository.RecipeItemRepository;
import com.letifactory.gaming.eco.pricecalculator.model.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RecipeServiceImpl {

    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeItemRepository recipeItemRepository;

    @Transactional
    public CompleteRecipe save(final CompleteRecipe completeRecipe) {
        completeRecipe.setRecipe(recipeRepository.save(completeRecipe.getRecipe()));
        completeRecipe.setRecipeItems(this.saveRecipeItems(completeRecipe.getRecipeItems()));
        return completeRecipe;
    }

    private List<EcoRecipeItem> saveRecipeItems(final List<EcoRecipeItem> items) {
        final List<EcoRecipeItem> retItems = new ArrayList<>();
        items.forEach(i -> retItems.add(recipeItemRepository.save(i)));
        return retItems;
    }

    public CompleteRecipe getCompleteRecipe(final String recipeName) throws NoSuchElementException {
        final Optional<EcoRecipe> recipeOpt = recipeRepository.findById(recipeName);
        final List<EcoRecipeItem> allRecipeItem = recipeItemRepository.findAllByRecipe(recipeName);
        if (recipeOpt.isPresent()) {
            return new CompleteRecipe(recipeOpt.get(), allRecipeItem);
        } else {
            throw new NoSuchElementException("No recipe found for " + recipeName);
        }
    }

    public List<CompleteRecipe> getAllRecipesCratingItem(final String itemName) {
        final List<CompleteRecipe> craftingRecipe = new ArrayList<>();
        final List<EcoRecipe> recipeNames = this.recipeItemRepository.getAllRecipesCratingItem(itemName);
        recipeNames.forEach(recipe ->
                craftingRecipe.add(this.getCompleteRecipe(recipe.getName()))
        );
        return craftingRecipe;
    }

    public List<EcoRecipe> findAll() {
        return recipeRepository.findAll();
    }
}

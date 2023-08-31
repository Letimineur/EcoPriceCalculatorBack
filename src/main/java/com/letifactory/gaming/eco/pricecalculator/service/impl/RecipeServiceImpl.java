package com.letifactory.gaming.eco.pricecalculator.service.impl;

import jakarta.transaction.Transactional;
import com.letifactory.gaming.eco.pricecalculator.model.dto.CompleteRecipe;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipe;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipeItem;
import com.letifactory.gaming.eco.pricecalculator.model.repository.RecipeItemRepository;
import com.letifactory.gaming.eco.pricecalculator.model.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl {

    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeItemRepository recipeItemRepository;

    @Transactional
    public CompleteRecipe save(CompleteRecipe completeRecipe){
        completeRecipe.setRecipe(recipeRepository.save(completeRecipe.getRecipe()));
        completeRecipe.setRecipeItems(this.saveRecipeItems(completeRecipe.getRecipeItems()));
        return completeRecipe;
    }

    private List<EcoRecipeItem> saveRecipeItems(List<EcoRecipeItem> items){
        List<EcoRecipeItem> retItems = new ArrayList<>();
        items.forEach(i-> retItems.add(recipeItemRepository.save(i)));
        return retItems;
    }

    public CompleteRecipe getCompleteRecipe(String recipeName){
        final Optional<EcoRecipe> optRecipe = recipeRepository.findById(recipeName);
        return null;
    }

}

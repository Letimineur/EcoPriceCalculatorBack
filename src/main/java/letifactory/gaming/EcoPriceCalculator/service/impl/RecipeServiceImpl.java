package letifactory.gaming.EcoPriceCalculator.service.impl;

import jakarta.transaction.Transactional;
import letifactory.gaming.EcoPriceCalculator.model.dto.CompleteRecipe;
import letifactory.gaming.EcoPriceCalculator.model.entity.EcoRecipe;
import letifactory.gaming.EcoPriceCalculator.model.entity.EcoRecipeItem;
import letifactory.gaming.EcoPriceCalculator.model.repository.RecipeItemRepository;
import letifactory.gaming.EcoPriceCalculator.model.repository.RecipeRepository;
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

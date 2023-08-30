package letifactory.gaming.EcoPriceCalculator.model.dto;

import letifactory.gaming.EcoPriceCalculator.model.entity.EcoRecipe;
import letifactory.gaming.EcoPriceCalculator.model.entity.EcoRecipeItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CompleteRecipe {

    private EcoRecipe recipe;
    private List<EcoRecipeItem> recipeItems;

}

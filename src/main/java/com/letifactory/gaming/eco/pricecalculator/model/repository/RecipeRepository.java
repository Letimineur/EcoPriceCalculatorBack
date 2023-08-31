package com.letifactory.gaming.eco.pricecalculator.model.repository;

import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<EcoRecipe, String> {
//    @Query("Select new com.letifactory.gaming.EcoPriceCalculator.model.dto.CompleteRecipe(r, ri) from EcoRecipe r, " +
//            "EcoRecipeItem ri" +
//            " where r.name = :recipe AND r.name = ri.ecoRecipe GROUP BY r")
//    CompleteRecipe getCompleteRecipeByName(@Param("recipe") String recipe);
}

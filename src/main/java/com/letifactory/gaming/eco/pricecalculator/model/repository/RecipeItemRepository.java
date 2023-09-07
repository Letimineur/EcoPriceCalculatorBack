package com.letifactory.gaming.eco.pricecalculator.model.repository;

import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipe;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeItemRepository extends JpaRepository<EcoRecipeItem, String> {

    @Query(value = "Select ri.ecoRecipe from EcoRecipeItem ri where ri.ecoItem.name = :itemName and ri.isOutput = true")
    List<EcoRecipe> getAllRecipesCratingItem(@Param("itemName")String itemName);

    @Query(value = "Select ri from EcoRecipeItem ri where ri.ecoRecipe.name = :recipe")
    List<EcoRecipeItem> findAllByRecipe(@Param("recipe")String recipe);
}

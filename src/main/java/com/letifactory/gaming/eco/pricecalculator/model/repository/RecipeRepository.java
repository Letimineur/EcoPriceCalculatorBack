package com.letifactory.gaming.eco.pricecalculator.model.repository;

import com.letifactory.gaming.eco.pricecalculator.model.dto.CompleteRecipe;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<EcoRecipe, String> {

    Optional<CompleteRecipe> getCompleteRecipeByName(@Param("recipe") String recipe);
}

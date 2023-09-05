package com.letifactory.gaming.eco.pricecalculator.model.repository;

import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoRecipeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeItemRepository extends JpaRepository<EcoRecipeItem, String> {

    @Query(value = "Select ri.recipe from EcoRecipeItem ri where ri.item = :itemName and ri.isOutput = 1")
    public List<String> getAllRecipesCratingItem(@Param("itemName")String itemName);
}

package com.letifactory.gaming.eco.pricecalculator.model.service;

import com.letifactory.gaming.eco.pricecalculator.model.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EcoRecipeItemServiceImpl {

    @Autowired
    private RecipeRepository recipeRepository;

}

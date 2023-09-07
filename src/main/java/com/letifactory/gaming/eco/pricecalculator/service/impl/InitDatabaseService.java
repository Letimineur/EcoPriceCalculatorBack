package com.letifactory.gaming.eco.pricecalculator.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letifactory.gaming.eco.pricecalculator.exception.FailedDatabaseInitException;
import com.letifactory.gaming.eco.pricecalculator.model.dto.CompleteRecipe;
import com.letifactory.gaming.eco.pricecalculator.model.entity.*;
import com.letifactory.gaming.eco.pricecalculator.model.repository.*;
import com.letifactory.gaming.eco.pricecalculator.utils.AppConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class InitDatabaseService {

    private final Logger logg = LoggerFactory.getLogger(InitDatabaseService.class);

    private static String ADDED_LOG = "Added";
    private static final String ADDING_LOG = "Adding %s";
    private static final String SUCCESS_ADD_LOG = "Successfully added to database all %s";
    private static final String FAILED_ADD_LOG = "Failed to load %s from JSON file.";

    @Autowired
    private ConfigRepository configRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemTypeRepository itemTypeRepository;
    @Autowired
    private RecipeItemRepository recipeItemRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private SkillRepository skillRepository;
    //    @Autowired
//    private UserItemRepository userItemRepository;
//    @Autowired
//    private UserItemTypeRepository userItemTypeRepository;
//    @Autowired
//    private UserRepository userRepository;
    @Autowired
    private WorkbenchRepository workbenchRepository;

    @Autowired
    private CalcItemPriceFromRecipeService calcItemPriceFromRecipeService;


    public void initDatabase() throws FailedDatabaseInitException {

        this.intEcoConfig();
        this.initEcoSkill();
        this.initEcoWorkbench();
        this.initEcoItemType();
        this.initEcoItem();
        this.initRecipes();

        calcItemPriceFromRecipeService.updateItemPriceForItemType(new EcoItemType("basic", 5.0, 10.0, 1));

    }

    private void intEcoConfig() {
        List<EcoConfig> configs = configRepository.findAll();
        if (configs.isEmpty()) {
            final String ecoConfig = "EcoConfigs";
            try {
                configs = new ObjectMapper().readValue(
                        new File(AppConstantUtils.getJsonFilePath(ecoConfig)),
                        new TypeReference<>() {
                        });
                configs.forEach(cfg -> {
                    logg.info(String.format(ADDING_LOG, cfg.getName()));
                    configRepository.save(cfg);
                    logg.info(ADDED_LOG);
                });
                logg.info(String.format(SUCCESS_ADD_LOG, ecoConfig));
            } catch (final IOException e) {
                throw new FailedDatabaseInitException(String.format(FAILED_ADD_LOG, ecoConfig), e);
            }
        }
        AppConstantUtils.addAllConfigToConstant(configs);
    }

    private void initEcoSkill() {
        List<EcoSkill> skills = skillRepository.findAll();
        if (skills.isEmpty()) {
            final String ecoSkill = "EcoSkills";
            try {
                skills = new ObjectMapper().readValue(new File(AppConstantUtils.getJsonFilePath(ecoSkill)),
                        new TypeReference<>() {
                        });
                skills.forEach(skill -> {
                            logg.info(String.format(ADDING_LOG, skill.getName()));
                            skillRepository.save(skill);
                            logg.info(ADDED_LOG);
                        }
                );
                logg.info(String.format(SUCCESS_ADD_LOG, ecoSkill));
            } catch (final IOException e) {
                throw new FailedDatabaseInitException(String.format(FAILED_ADD_LOG, ecoSkill), e);
            }
        }
    }

    private void initEcoWorkbench() {
        List<EcoWorkbench> workbenches = workbenchRepository.findAll();
        if (workbenches.isEmpty()) {
            final String ecoWorkbench = "EcoWorkbenches";
            try {
                workbenches = new ObjectMapper().readValue(
                        new File(AppConstantUtils.getJsonFilePath(ecoWorkbench)),
                        new TypeReference<>() {
                        });
                workbenches.forEach(wb -> {
                    logg.info(String.format(ADDING_LOG, wb.getName()));
                    workbenchRepository.save(wb);
                    logg.info(ADDED_LOG);
                });
                logg.info(String.format(SUCCESS_ADD_LOG, ecoWorkbench));
            } catch (final IOException e) {
                throw new FailedDatabaseInitException(String.format(FAILED_ADD_LOG, ecoWorkbench), e);
            }
        }
    }

    private void initEcoItemType() {
        List<EcoItemType> itemTypes = itemTypeRepository.findAll();
        if (itemTypes.isEmpty()) {
            final String ecoItemType = "EcoItemTypes";
            try {
                itemTypes = new ObjectMapper().readValue(
                        new File(AppConstantUtils.getJsonFilePath(ecoItemType)),
                        new TypeReference<>() {
                        });
                itemTypes.forEach(itt -> {
                    logg.info(String.format(ADDING_LOG, itt.getType()));
                    itemTypeRepository.save(itt);
                    logg.info(ADDED_LOG);
                });
                logg.info(String.format(SUCCESS_ADD_LOG, ecoItemType));
            } catch (final IOException e) {
                throw new FailedDatabaseInitException(String.format(FAILED_ADD_LOG, ecoItemType), e);
            }
        }
        //Init allType constant for later use
        AppConstantUtils.addAllTypeToConstant(itemTypes);
    }

    private void initEcoItem() {
        List<EcoItem> items = itemRepository.findAll();
        if (items.isEmpty()) {
            final String ecoItem = "EcoItems";
            try {
                items = new ObjectMapper().readValue(
                        new File(AppConstantUtils.getJsonFilePath(ecoItem)),
                        new TypeReference<>() {
                        });
                items.forEach(item -> {
                    logg.info(String.format(ADDING_LOG, item.getName()));
                    itemRepository.save(item);
                    logg.info(ADDED_LOG);
                });
                logg.info(String.format(SUCCESS_ADD_LOG, ecoItem));
            } catch (final IOException e) {
                throw new FailedDatabaseInitException(String.format(FAILED_ADD_LOG, ecoItem), e);
            }
        }
    }

    private void initRecipes() {
        final List<EcoRecipe> recipes = recipeRepository.findAll();
        if (recipes.isEmpty()) {
            final String ecoRecipes = "EcoRecipes";
            try {
                final List<CompleteRecipe> cptRecipes = new ObjectMapper().readValue(
                        new File(AppConstantUtils.getJsonFilePath(ecoRecipes)),
                        new TypeReference<>() {
                        });
                cptRecipes.forEach(completeRecipe -> {
                    logg.info(String.format(ADDING_LOG, completeRecipe.getRecipe().getName()));
                    recipeRepository.save(completeRecipe.getRecipe());
                    completeRecipe.getRecipeItems().forEach(itemRecipe -> {
                        logg.info(String.format(ADDING_LOG, itemRecipe.getNameId()));
                        recipeItemRepository.save(itemRecipe);
                    });
                    logg.info(ADDED_LOG);
                });
                logg.info(String.format(SUCCESS_ADD_LOG, ecoRecipes));
            } catch (final IOException e) {
                throw new FailedDatabaseInitException(String.format(FAILED_ADD_LOG, ecoRecipes), e);
            }
        }
    }
}

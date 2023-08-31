package com.letifactory.gaming.eco.pricecalculator.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letifactory.gaming.eco.pricecalculator.exception.FailedDatabaseInitException;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItemType;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoSkill;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoWorkbench;
import com.letifactory.gaming.eco.pricecalculator.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class InitDatabaseService {

    private final static String JSONPATH = "src/main/resources/static/data/%s.json";
    private static String ADDED_LOG = "Added";
    private static final String ADDING_LOG = "Adding %s";
    private static final String SUCCESS_ADD_LOG = "Successfully added to database all %s";
    private static final String FAILED_ADD_LOG = "Failed to load %s from JSON file.";

    //    @Autowired
//    private ConfigRepository configRepository;
//    @Autowired
//    private ItemRepository itemRepository;
    @Autowired
    private ItemTypeRepository itemTypeRepository;
    //    @Autowired
//    private RecipeItemRepository recipeItemRepository;
//    @Autowired
//    private RecipeRepository recipeRepository;
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

    public static String getJsonFilePath(String fileName) {
        return String.format(JSONPATH,fileName);
    }

    public void initDatabase() throws FailedDatabaseInitException {

        this.initEcoSkill();
        this.initEcoWorkbench();
        this.initEcoItemType();
    }

    private void initEcoSkill() {
        List<EcoSkill> skills = skillRepository.findAll();
        if (skills.isEmpty()) {
            final String ecoSkill = "EcoSkills";
            try {
                skills = new ObjectMapper().readValue(new File(InitDatabaseService.getJsonFilePath(ecoSkill)),
                        new TypeReference<>() {
                        });
                skills.forEach(skill -> {
                            System.out.println(String.format(ADDING_LOG, skill.getName()));
                            skillRepository.save(skill);
                            System.out.println(ADDED_LOG);
                        }
                );
                System.out.println(String.format(SUCCESS_ADD_LOG, ecoSkill));
            } catch (IOException e) {
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
                        new File(InitDatabaseService.getJsonFilePath(ecoWorkbench)),
                        new TypeReference<>() {
                        });
                workbenches.forEach(wb -> {
                    System.out.println(String.format(ADDING_LOG, wb.getName()));
                    workbenchRepository.save(wb);
                    System.out.println(ADDED_LOG);
                });
                System.out.println(String.format(SUCCESS_ADD_LOG, ecoWorkbench));
            } catch (IOException e) {
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
                        new File(InitDatabaseService.getJsonFilePath(ecoItemType)),
                        new TypeReference<>() {
                        });
                itemTypes.forEach(itt -> {
                    System.out.println(String.format(ADDING_LOG, itt.getType()));
                    itemTypeRepository.save(itt);
                    System.out.println(ADDED_LOG);
                });
                System.out.println(String.format(SUCCESS_ADD_LOG, ecoItemType));
            } catch (IOException e) {
                throw new FailedDatabaseInitException(String.format(FAILED_ADD_LOG, ecoItemType), e);
            }
        }
    }

}

package com.letifactory.gaming.eco.pricecalculator.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letifactory.gaming.eco.pricecalculator.exception.FailedDatabaseInitException;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoSkill;
import com.letifactory.gaming.eco.pricecalculator.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class InitDatabaseService {

    private final static StringBuilder JSONPATH = new StringBuilder("src/resources/static/data/");
//    @Autowired
//    private ConfigRepository configRepository;
//    @Autowired
//    private ItemRepository itemRepository;
//    @Autowired
//    private ItemTypeRepository itemTypeRepository;
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
//    @Autowired
//    private WorkbenchRepository workbenchRepository;

    public static String getJsonFilePath(String fileName) {
        return JSONPATH.append(fileName).append(".json").toString();
    }

    public void initDatabase() throws FailedDatabaseInitException {
        //List<EcoConfig> configs = configRepository.findAll();
        List<EcoSkill> skills = skillRepository.findAll();
        if (skills.isEmpty()) {
            try {
                skills = new ObjectMapper().readValue(new File(InitDatabaseService.getJsonFilePath("EcoSkill")),
                        new TypeReference<>() {
                        });
                skills.forEach(skill -> skillRepository.save(skill));
            } catch (IOException e) {
                throw new FailedDatabaseInitException("Failed to load Skills from JSON file.", e);
            }
        }

    }
}

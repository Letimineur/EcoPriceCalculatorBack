package com.letifactory.gaming.eco.pricecalculator.model.service;

import jakarta.transaction.Transactional;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoConfig;
import com.letifactory.gaming.eco.pricecalculator.model.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl {

    @Autowired
    ConfigRepository configRepository;

    @Transactional
    public EcoConfig save(EcoConfig ecoConfig){
       return configRepository.save(ecoConfig);
    }
}

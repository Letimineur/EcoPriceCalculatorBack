package com.letifactory.gaming.eco.pricecalculator.model.service;

import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoConfig;
import com.letifactory.gaming.eco.pricecalculator.model.repository.ConfigRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl {

    @Autowired
    ConfigRepository configRepository;

    @Transactional
    public EcoConfig save(final EcoConfig ecoConfig){
       return configRepository.save(ecoConfig);
    }
}

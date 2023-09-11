package com.letifactory.gaming.eco.pricecalculator.model.service;

import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItemType;
import com.letifactory.gaming.eco.pricecalculator.model.repository.ItemTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EcoItemTypeServiceImpl {

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    public EcoItemType findById(String type) throws NoSuchElementException {
        final Optional<EcoItemType> byId = itemTypeRepository.findById(type);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new EntityNotFoundException("No type found for id: " + type);
    }
}

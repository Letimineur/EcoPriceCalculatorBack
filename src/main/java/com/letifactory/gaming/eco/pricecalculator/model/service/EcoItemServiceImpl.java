package com.letifactory.gaming.eco.pricecalculator.model.service;

import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItem;
import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItemType;
import com.letifactory.gaming.eco.pricecalculator.model.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcoItemServiceImpl {

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public EcoItem save(final EcoItem item) {
        return this.itemRepository.save(item);
    }

    public List<EcoItem> getAllNotPricedItemForType(final EcoItemType type){
        return  this.itemRepository.getAllNotPricedItemForType(type.getType());
    }

    public List<EcoItem> getAllItemForType(final EcoItemType type){
        return  this.itemRepository.getAllItemForType(type.getType());
    }
}

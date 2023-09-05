package com.letifactory.gaming.eco.pricecalculator.model.service;

import com.letifactory.gaming.eco.pricecalculator.model.entity.EcoItem;
import com.letifactory.gaming.eco.pricecalculator.model.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EcoItemServiceImpl {

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public EcoItem save(EcoItem item){
        return this.itemRepository.save(item);
    }
}
